package org.taktik.icure.asyncdao.samv2.impl

import kotlinx.coroutines.flow.filter
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

@Repository("samUpdateDAO")
@Profile("sam")
class SamUpdateDAOImpl(
	@Qualifier("drugCouchDbDispatcher") couchDbDispatcher: CouchDbDispatcher,
	idGenerator: IDGenerator,
	datastoreInstanceProvider: DatastoreInstanceProvider,
	designDocumentProvider: DesignDocumentProvider
) : InternalDAOImpl<SamUpdate>(SamUpdate::class.java, couchDbDispatcher, idGenerator, datastoreInstanceProvider, designDocumentProvider),
	SamUpdateDAO {

	private val samVersionRegex = Regex("E\\.[0-9]{8}_[0-9]{4,}")

	override suspend fun getLastAppliedUpdate(): SamUpdate? {
		val client = couchDbDispatcher.getClient(datastoreInstanceProvider.getInstanceAndGroup())
		var lastUpdateId: String? = null
		client.queryView<String, Any>(
			ViewQuery().allDocs().includeDocs(false)
		).filter {
			samVersionRegex.matches(it.id)
		}.collect{
			if (lastUpdateId == null || it.id > lastUpdateId!!) {
				lastUpdateId = it.id
			}
		}
		return lastUpdateId?.let { get(it) }
	}
}