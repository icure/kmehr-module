package org.taktik.icure.asyncdao.samv2.impl

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.future.future
import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository
import org.taktik.couchdb.dao.DesignDocumentProvider
import org.taktik.couchdb.entity.ViewQuery
import org.taktik.couchdb.id.IDGenerator
import org.taktik.couchdb.queryView
import org.taktik.icure.asyncdao.CouchDbDispatcher
import org.taktik.icure.asyncdao.impl.InternalDAOImpl
import org.taktik.icure.asyncdao.samv2.SamUpdateDAO
import org.taktik.icure.asynclogic.datastore.DatastoreInstanceProvider
import org.taktik.icure.entities.samv2.updates.SamUpdate
import com.github.benmanes.caffeine.cache.Caffeine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.future.await
import org.taktik.couchdb.get
import java.time.Duration

@Repository("samUpdateDAO")
@Profile("sam")
class SamUpdateDAOImpl(
	@Qualifier("drugCouchDbDispatcher") couchDbDispatcher: CouchDbDispatcher,
	idGenerator: IDGenerator,
	datastoreInstanceProvider: DatastoreInstanceProvider,
	designDocumentProvider: DesignDocumentProvider
) : InternalDAOImpl<SamUpdate>(SamUpdate::class.java, couchDbDispatcher, idGenerator, datastoreInstanceProvider, designDocumentProvider),
	SamUpdateDAO, DisposableBean {

	private val samUpdateKey = "sam-update"
	private val samVersionRegex = Regex("E\\.[0-9]{8}_[0-9]{4,}")
	private val samUpdateScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
	private val samUpdatesCache = Caffeine.newBuilder()
		.expireAfterWrite(Duration.ofDays(1))
		.buildAsync<String, List<SamUpdate>>()

	override fun destroy() {
		samUpdateScope.cancel()
	}

	override suspend fun getAppliedUpdates(): List<SamUpdate> =
		samUpdateScope.async {
			val client = couchDbDispatcher.getClient(datastoreInstanceProvider.getInstanceAndGroup())
			samUpdatesCache.get(samUpdateKey) { _, _ ->
				future {
					val ids = client.queryView<String, Any>(
						ViewQuery().allDocs().includeDocs(false)
					).filter {
						samVersionRegex.matches(it.id)
					}.map { it.id }.toList().sorted()
					client.get<SamUpdate>(ids).toList()
				}
			}.await()
		}.await()

	override suspend fun save(entity: SamUpdate): SamUpdate? {
		samUpdatesCache.synchronous().invalidate(samUpdateKey)
		return super.save(entity)
	}

	override suspend fun getLastAppliedUpdate(): SamUpdate? {
		val client = couchDbDispatcher.getClient(datastoreInstanceProvider.getInstanceAndGroup())
		var lastUpdateId: String? = null
		client.queryView<String, Any>(
			ViewQuery().allDocs().includeDocs(false)
		).filter {
			samVersionRegex.matches(it.id)
		}.collect {
			if (lastUpdateId == null || it.id > lastUpdateId!!) {
				lastUpdateId = it.id
			}
		}
		return lastUpdateId?.let { get(it) }
	}
}