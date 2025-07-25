package org.taktik.icure.test.fake.components

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.taktik.couchdb.ViewRowWithDoc
import org.taktik.couchdb.dao.DesignDocumentProvider
import org.taktik.couchdb.id.IDGenerator
import org.taktik.couchdb.queryViewIncludeDocs
import org.taktik.icure.asyncdao.CouchDbDispatcher
import org.taktik.icure.asyncdao.impl.InternalDAOImpl
import org.taktik.icure.asyncdao.samv2.AmpDAO
import org.taktik.icure.datastore.DatastoreInstanceProvider
import org.taktik.icure.entities.samv2.Amp
import org.taktik.icure.utils.createQuery


class TestAmpDAO(
	private val ampDAO: AmpDAO,
	couchDbDispatcher: CouchDbDispatcher,
	idGenerator: IDGenerator,
	datastoreInstanceProvider: DatastoreInstanceProvider,
	designDocumentProvider: DesignDocumentProvider
) : InternalDAOImpl<Amp>(Amp::class.java, couchDbDispatcher, idGenerator, datastoreInstanceProvider, designDocumentProvider) {

	fun getAllAmps(): Flow<Amp> = flow {
		val client = couchDbDispatcher.getClient(datastoreInstanceProvider.getInstanceAndGroup())

		val viewQuery = designDocumentProvider.createQuery(
			client,
			ampDAO, "all",
			entityClass
		).includeDocs(true)

		emitAll(client
			.queryViewIncludeDocs<Any?, String, Amp>(viewQuery)
			.filterIsInstance<ViewRowWithDoc<Any?, String, Amp>>()
			.map { it.doc }
		)
	}

}