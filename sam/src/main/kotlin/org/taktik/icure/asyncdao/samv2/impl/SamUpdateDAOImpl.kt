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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.future.await
import org.taktik.couchdb.ViewRowWithDoc
import org.taktik.couchdb.annotation.View
import org.taktik.couchdb.get
import org.taktik.couchdb.queryViewIncludeDocs
import java.time.Duration

@Repository("samUpdateDAO")
@Profile("sam")
@View(name = "all", map = "function(doc) { if (doc.java_type == 'org.taktik.icure.entities.samv2.updates.SamUpdate') emit( null, doc._id )}", reduce = "function(keys, values, rereduce) { return values.reduce((maxValue, currentValue) => currentValue > maxValue ? currentValue : maxValue); }")
class SamUpdateDAOImpl(
	@Qualifier("drugCouchDbDispatcher") couchDbDispatcher: CouchDbDispatcher,
	idGenerator: IDGenerator,
	datastoreInstanceProvider: DatastoreInstanceProvider,
	designDocumentProvider: DesignDocumentProvider
) : InternalDAOImpl<SamUpdate>(SamUpdate::class.java, couchDbDispatcher, idGenerator, datastoreInstanceProvider, designDocumentProvider), SamUpdateDAO {

	override fun getEntities(): Flow<SamUpdate> = flow {
		val client = couchDbDispatcher.getClient(datastoreInstanceProvider.getInstanceAndGroup())
		emitAll(
			client.queryViewIncludeDocs<String?, String, SamUpdate>(
				createQuery("all")
					.reduce(false)
					.includeDocs(true)
					.descending(true)
			).map { it.doc }
		)
	}

	override suspend fun getLastAppliedUpdate(): SamUpdate? {
		val client = couchDbDispatcher.getClient(datastoreInstanceProvider.getInstanceAndGroup())
		val lastUpdateId = client.queryView<String?, String>(
			createQuery("all")
				.reduce(true)
				.groupLevel(1)
				.includeDocs(false)
		).firstOrNull()?.value
		return lastUpdateId?.let { get(it) }
	}
}