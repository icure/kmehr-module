package org.taktik.icure.asyncdao.samv2.impl

import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.firstOrNull
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository
import org.taktik.couchdb.ViewRowWithDoc
import org.taktik.couchdb.annotation.View
import org.taktik.couchdb.dao.DesignDocumentProvider
import org.taktik.couchdb.id.IDGenerator
import org.taktik.icure.asyncdao.CouchDbDispatcher
import org.taktik.icure.asyncdao.impl.InternalDAOImpl
import org.taktik.icure.asyncdao.samv2.SamUpdateDAO
import org.taktik.icure.asynclogic.datastore.DatastoreInstanceProvider
import org.taktik.icure.asynclogic.datastore.IDatastoreInformation
import org.taktik.icure.entities.samv2.updates.SamUpdate
import org.taktik.icure.utils.queryView

@Repository("samUpdateDAO")
@Profile("sam")
@View(name = "all", map = "function(doc) { if (doc.java_type == 'org.taktik.icure.entities.samv2.updates.SamUpdate') emit( null, doc._id )}")
class SamUpdateDAOImpl(
	@Qualifier("drugCouchDbDispatcher") couchDbDispatcher: CouchDbDispatcher,
	idGenerator: IDGenerator,
	datastoreInstanceProvider: DatastoreInstanceProvider,
	designDocumentProvider: DesignDocumentProvider
) : InternalDAOImpl<SamUpdate>(SamUpdate::class.java, couchDbDispatcher, idGenerator, datastoreInstanceProvider, designDocumentProvider),
	SamUpdateDAO {

	@View(name = "by_date", map = "function(doc) { if (doc.java_type == 'org.taktik.icure.entities.samv2.updates.SamUpdate' && !doc.deleted) emit( doc.date, [doc._id, doc.type] )}")
	override suspend fun getLastAppliedUpdate(datastoreInformation: IDatastoreInformation): SamUpdate? {
		val client = couchDbDispatcher.getClient(datastoreInformation)
		return client.queryView<Int, Array<String>, SamUpdate>(
				createQuery("by_date")
					.includeDocs(true)
					.descending(true)
					.limit(1),
			).filterIsInstance<ViewRowWithDoc<Int, Array<String>, SamUpdate>>().firstOrNull()?.doc
	}
}