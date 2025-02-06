package org.taktik.icure.asynclogic.samv2.impl

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.launch
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import org.taktik.couchdb.Client
import org.taktik.couchdb.ViewRowNoDoc
import org.taktik.couchdb.create
import org.taktik.couchdb.update
import org.taktik.icure.asyncdao.CouchDbDispatcher
import org.taktik.icure.asyncdao.InternalDAO
import org.taktik.icure.asyncdao.results.BulkSaveResult
import org.taktik.icure.asyncdao.samv2.AmpDAO
import org.taktik.icure.asyncdao.samv2.NmpDAO
import org.taktik.icure.asyncdao.samv2.ParagraphDAO
import org.taktik.icure.asyncdao.samv2.PharmaceuticalFormDAO
import org.taktik.icure.asyncdao.samv2.SamUpdateDAO
import org.taktik.icure.asyncdao.samv2.SubstanceDAO
import org.taktik.icure.asyncdao.samv2.VerseDAO
import org.taktik.icure.asyncdao.samv2.VmpDAO
import org.taktik.icure.asyncdao.samv2.VmpGroupDAO
import org.taktik.icure.asynclogic.datastore.DatastoreInstanceProvider
import org.taktik.icure.asynclogic.samv2.UpdatesBridge
import org.taktik.icure.db.PaginationOffset
import org.taktik.icure.entities.base.StoredDocument
import org.taktik.icure.entities.samv2.updates.SamUpdate
import org.taktik.icure.entities.samv2.updates.SignatureUpdate
import org.taktik.icure.entities.samv2.updates.UpdateType
import org.taktik.icure.utils.GzipDeflateInputStream
import org.taktik.icure.utils.suspendRetry
import org.taktik.icure.utils.toFlow
import reactor.netty.http.client.PrematureCloseException
import java.util.ArrayDeque

@Component
@ConditionalOnProperty(name = ["icure.sam.updaterUrl"], matchIfMissing = false)
class SamV2Updater(
	@Qualifier("drugCouchDbDispatcher") private val drugsCouchDbDispatcher: CouchDbDispatcher,
	private val ampDAO: AmpDAO,
	private val vmpDAO: VmpDAO,
	private val vmpGroupDAO: VmpGroupDAO,
	private val nmpDAO: NmpDAO,
	private val paragraphDAO: ParagraphDAO,
	private val pharmaceuticalFormDAO: PharmaceuticalFormDAO,
	private val substanceDAO: SubstanceDAO,
	private val verseDAO: VerseDAO,
	private val samUpdateDAO: SamUpdateDAO,
	private val updatesBridge: UpdatesBridge,
	private val datastoreInstanceProvider: DatastoreInstanceProvider
) {

	private val coroutineScope = CoroutineScope(Dispatchers.Default)
	private var currentJob: SamV2UpdateTask? = null

	fun startUpdateJob(jwt: String) {
		if (currentJob == null || currentJob?.isCompleted == true) {
			val task = SamV2UpdateTask()
			task.job = coroutineScope.launch {
				task.update(jwt)
			}
			currentJob = task
		} else {
			throw IllegalArgumentException("Cannot start update job: a job is already running")
		}
	}

	fun stopUpdateJob() {
		if (currentJob != null && currentJob?.isCompleted == false) {
			currentJob?.job?.cancel()
		}
	}

	fun getCurrentJobStatus() = currentJob?.processStatus()
		?: listOf(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Missing, System.currentTimeMillis(), "No update job running or completed"))

	fun getAppliedUpdates() = samUpdateDAO.getEntities()

	private inner class SamV2UpdateTask {
		var job: Job? = null
		private val _processStatus = mutableListOf<SamV2UpdateTaskLog>()

		val isCompleted: Boolean
			get() = job?.isCompleted ?: false

		fun processStatus(): List<SamV2UpdateTaskLog> = _processStatus

		private suspend fun checkOnlyLocalNodeExists(client: Client) {
			val membership = client.membership()
			if (membership.allNodes.size != 1) {
				throw IllegalStateException("Multiple nodes found on local installation")
			}
		}

		private suspend fun getCurrentSamUpdate(): SamUpdate? = samUpdateDAO.getLastAppliedUpdate()

		suspend fun update(jwt: String) {
			try {
				_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Started, System.currentTimeMillis(), "Update job started"))
				val datastoreInfo = datastoreInstanceProvider.getInstanceAndGroup()
				val client = drugsCouchDbDispatcher.getClient(datastoreInfo)
				checkOnlyLocalNodeExists(client)
				val currentUpdate = getCurrentSamUpdate()
				_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Retrieving updates to apply"))
				val updates = updatesBridge.getFollowingUpdates(jwt, currentUpdate)
				if (updates.isNotEmpty()) {
					_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Updates to apply: ${updates.joinToString(", ") { "${it.type} - ${it.id}" }}"))
				} else {
					_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Drugs and ChapIV are up to date"))
				}
				updates.forEach { update ->
					when(update.type) {
						UpdateType.Diff -> loadDiffUpdate(update)
						UpdateType.Snapshot -> loadSnapshotUpdate(update)
					}
				}
				_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Completed, System.currentTimeMillis(), "Update completed successfully"))
			} catch(e: Exception) {
				if (e is UpdatesBridgeImpl.UpdaterBridgeException) {
					_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Error, System.currentTimeMillis(), e.message ?: "Error received from updater backend"))
				} else {
					_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Error, System.currentTimeMillis(), e.stackTraceToString()))
				}
			}

		}

		private suspend fun loadSnapshotUpdate(snapshot: SamUpdate) {
			_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying snapshot update ${snapshot.version}"))
			snapshot.updates.forEach { (type, resourceName) ->
				when (type) {
					SamUpdate.BundleType.Amps -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Amp from snapshot update ${snapshot.version}"))
						val currentAmpIds = loadEntities(ampDAO, snapshot.id, UpdateType.Snapshot, resourceName)
						deleteUnused(ampDAO, currentAmpIds)
					}
					SamUpdate.BundleType.NonMedicinals -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Nmp from snapshot update ${snapshot.version}"))
						val currentNmpIds = loadEntities(nmpDAO, snapshot.id, UpdateType.Snapshot, resourceName)
						deleteUnused(nmpDAO, currentNmpIds)
					}
					SamUpdate.BundleType.Paragraphs -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Paragraphs from snapshot update ${snapshot.version}"))
						val currentParagraphIds = loadEntities(paragraphDAO, snapshot.id, UpdateType.Snapshot, resourceName)
						deleteUnused(paragraphDAO, currentParagraphIds)
					}
					SamUpdate.BundleType.PharmaceuticalForms -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying PharmaceuticalForms from snapshot update ${snapshot.version}"))
						val currentParagraphIds = loadEntities(pharmaceuticalFormDAO, snapshot.id, UpdateType.Snapshot, resourceName)
						deleteUnused(pharmaceuticalFormDAO, currentParagraphIds)
					}
					SamUpdate.BundleType.Substances -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Substances from snapshot update ${snapshot.version}"))
						val currentParagraphIds = loadEntities(substanceDAO, snapshot.id, UpdateType.Snapshot, resourceName)
						deleteUnused(substanceDAO, currentParagraphIds)
					}
					SamUpdate.BundleType.Verses -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Verses from snapshot update ${snapshot.version}"))
						val currentVersesIds = loadEntities(verseDAO, snapshot.id, UpdateType.Snapshot, resourceName)
						deleteUnused(verseDAO, currentVersesIds)
					}
					SamUpdate.BundleType.Vmps -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Vmps from snapshot update ${snapshot.version}"))
						val currentVmpIds = loadEntities(vmpDAO, snapshot.id, UpdateType.Snapshot, resourceName)
						deleteUnused(vmpDAO, currentVmpIds)
					}
					SamUpdate.BundleType.VmpGroups -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Vmp Groups from snapshot update ${snapshot.version}"))
						val currentVmpIds = loadEntities(vmpGroupDAO, snapshot.id, UpdateType.Snapshot, resourceName)
						deleteUnused(vmpGroupDAO, currentVmpIds)
					}
					SamUpdate.BundleType.Signatures -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Signatures from snapshot update ${snapshot.version}"))
						loadSignatures(snapshot.id, UpdateType.Snapshot, resourceName)
					}
				}
			}
			_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Saving version reference"))
			samUpdateDAO.save(snapshot.copy(rev = null))
			_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Successfully applied snapshot update ${snapshot.version}"))
		}

		private suspend fun loadDiffUpdate(samUpdate: SamUpdate) {
			_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying diff update ${samUpdate.version}"))
			samUpdate.updates.forEach { (type, resourceName) ->
				when (type) {
					SamUpdate.BundleType.Amps -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Amps from diff update ${samUpdate.version}"))
						loadEntities(ampDAO, samUpdate.id, UpdateType.Diff, resourceName)
					}
					SamUpdate.BundleType.NonMedicinals -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Nmps from diff update ${samUpdate.version}"))
						loadEntities(nmpDAO, samUpdate.id, UpdateType.Diff, resourceName)
					}
					SamUpdate.BundleType.Paragraphs -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Paragraphs updates from diff update ${samUpdate.version}"))
						loadEntities(paragraphDAO, samUpdate.id, UpdateType.Diff, resourceName)
					}
					SamUpdate.BundleType.PharmaceuticalForms -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying PharmaceuticalForms updates from diff update ${samUpdate.version}"))
						loadEntities(pharmaceuticalFormDAO, samUpdate.id, UpdateType.Diff, resourceName)
					}
					SamUpdate.BundleType.Substances -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Substances updates from diff update ${samUpdate.version}"))
						loadEntities(substanceDAO, samUpdate.id, UpdateType.Diff, resourceName)
					}
					SamUpdate.BundleType.Verses -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Verses updates from diff update ${samUpdate.version}"))
						loadEntities(verseDAO, samUpdate.id, UpdateType.Diff, resourceName)
					}
					SamUpdate.BundleType.Vmps -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Vmps updates from diff update ${samUpdate.version}"))
						loadEntities(vmpDAO, samUpdate.id, UpdateType.Diff, resourceName)
					}
					SamUpdate.BundleType.VmpGroups -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Vmp updates Groups from diff update ${samUpdate.version}"))
						loadEntities(vmpGroupDAO, samUpdate.id, UpdateType.Diff, resourceName)
					}
					SamUpdate.BundleType.Signatures -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Signatures updates from diff update ${samUpdate.version}"))
					}
				}
			}
			samUpdate.deletions.forEach { (type, resourceName) ->
				when (type) {
					SamUpdate.BundleType.Amps -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Deleting Amps from diff update ${samUpdate.version}"))
						deleteEntities(ampDAO, samUpdate.id, UpdateType.Diff, resourceName, hardDelete = false)
					}
					SamUpdate.BundleType.NonMedicinals -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Deleting Nmps from diff update ${samUpdate.version}"))
						deleteEntities(nmpDAO, samUpdate.id, UpdateType.Diff, resourceName, hardDelete = false)
					}
					SamUpdate.BundleType.Paragraphs -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Deleting Paragraphs from diff update ${samUpdate.version}"))
						deleteEntities(paragraphDAO, samUpdate.id, UpdateType.Diff, resourceName, hardDelete = true)
					}
					SamUpdate.BundleType.PharmaceuticalForms -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Deleting PharmaceuticalForms from diff update ${samUpdate.version}"))
						deleteEntities(pharmaceuticalFormDAO, samUpdate.id, UpdateType.Diff, resourceName, hardDelete = false)
					}
					SamUpdate.BundleType.Substances -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Deleting Substances from diff update ${samUpdate.version}"))
						deleteEntities(substanceDAO, samUpdate.id, UpdateType.Diff, resourceName, hardDelete = false)
					}
					SamUpdate.BundleType.Verses -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Deleting Verses from diff update ${samUpdate.version}"))
						deleteEntities(vmpDAO, samUpdate.id, UpdateType.Diff, resourceName, hardDelete = true)
					}
					SamUpdate.BundleType.Vmps -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Deleting Vmps from diff update ${samUpdate.version}"))
						deleteEntities(vmpDAO, samUpdate.id, UpdateType.Diff, resourceName, hardDelete = false)
					}
					SamUpdate.BundleType.VmpGroups -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Deleting Vmp Groups from diff update ${samUpdate.version}"))
						deleteEntities(vmpGroupDAO, samUpdate.id, UpdateType.Diff, resourceName, hardDelete = false)
					}
					else -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Ignoring deletion bundle $type"))
					}
				}
			}
			_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Saving version reference"))
			samUpdateDAO.save(samUpdate.copy(rev = null))
			_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Successfully applied diff update ${samUpdate.version}"))
		}

		private suspend fun loadSignatures(
			updateVersion: String,
			type: UpdateType,
			resourceName: String
		) {
			val datastoreInfo = datastoreInstanceProvider.getInstanceAndGroup()
			val client = drugsCouchDbDispatcher.getClient(datastoreInfo)
			updatesBridge.getSignaturesUpdateContent(updateVersion, type, resourceName).collect { signatureUpdate ->
				if (signatureUpdate.type == SignatureUpdate.SignatureType.SamV2) {
					ampDAO.getVersion(datastoreInfo)?.let { samVersion ->
						client.update(samVersion.copy(version = signatureUpdate.version.version, date = signatureUpdate.version.date))
					} ?: client.create(signatureUpdate.version.copy(rev = null))
				} else {
					val version = ampDAO.getSignature(datastoreInfo, signatureUpdate.type.name.lowercase())?.let {
						client.update(it.copy(version = signatureUpdate.version.version, date = signatureUpdate.version.date))
					} ?: client.create(signatureUpdate.version.copy(rev = null, attachments = emptyMap()))
					signatureUpdate.idsForAttachment?.also { productIds ->
						client.createAttachment(version.id, "signatures", version.rev!!, "application/gzip", makeSignatures(productIds))
					}
				}
			}
		}

		private fun makeSignatures(productIds: Map<String, String>) =
			GzipDeflateInputStream(
				productIds.entries.sortedBy { it.key }
					.joinToString("\n") { "${it.key}|${it.value}" }
					.byteInputStream(Charsets.UTF_8)
			).toFlow()

		private suspend inline fun <reified T : StoredDocument> loadEntities(
			dao: InternalDAO<T>,
			updateVersion: String,
			type: UpdateType,
			resourceName: String
		): Set<String> = loadEntitiesRecursive(dao, T::class.java, updateVersion, type, resourceName)

		private suspend fun <T : StoredDocument> loadEntitiesRecursive(
			dao: InternalDAO<T>,
			klass: Class<T>,
			updateVersion: String,
			type: UpdateType,
			resourceName: String,
			updatedEntitiesId: LinkedHashSet<String> = LinkedHashSet(),
		): Set<String> = try {
			val lastApplied = updatedEntitiesId.lastOrNull()
			updatesBridge.getEntityUpdateContent(klass, updateVersion, type, resourceName).dropWhile {
				lastApplied != null && it.id != lastApplied
			}.buffered(20) { loadedEntities ->
				val currentEntities = dao.getEntities(loadedEntities.map { it.id }).toList().associateBy { it.id }
				loadedEntities.mapNotNull { entity ->
					@Suppress("UNCHECKED_CAST")
					when {
						!currentEntities.containsKey(entity.id) -> entity as T
						entity.withIdRev(rev = "0") != currentEntities.getValue(entity.id).withIdRev(rev = "0") -> entity.withIdRev(
							rev = checkNotNull(currentEntities.getValue(entity.id).rev) { "Entity ${entity.id} has no revision in the database" }
						) as T
						else -> null
					}
				}.takeIf { it.isNotEmpty() }?.let { entitiesToSave ->
					dao.save(entitiesToSave).onEach {
						if (it is BulkSaveResult.Failure) {
							throw IllegalStateException("Cannot save entity ${it.entityId}: ${it.code} - ${it.message}")
						}
					}.toList().asFlow()
				}.also {
					updatedEntitiesId.addAll(loadedEntities.map { it.id })
				} ?: emptyFlow()
			}.toList()
			updatedEntitiesId
		} catch (e: Exception) {
			if (e is PrematureCloseException) {
				_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Connection closed prematurely, retrying ${klass.name} from ${updatedEntitiesId.lastOrNull() ?: "the beginning"}."))
				loadEntitiesRecursive(dao, klass, updateVersion, type, resourceName, updatedEntitiesId)
			} else {
				throw e
			}
		}

		private suspend fun <T : StoredDocument> deleteEntities(
			dao: InternalDAO<T>,
			updateVersion: String,
			type: UpdateType,
			resourceName: String,
			hardDelete: Boolean
		): List<String> =
			updatesBridge.getEntityDeleteContent(updateVersion, type, resourceName)
				.toList()
				.chunked(100)
				.flatMap { idsChunk ->
					val entitiesToDelete = dao.getEntities(idsChunk)
					if (hardDelete) {
						dao.purge(entitiesToDelete).map { it.id }.toList()
					} else {
						dao.remove(entitiesToDelete).map { it.id }.toList()
					}
				}


		private suspend fun <T : StoredDocument> deleteUnused(dao: InternalDAO<T>, used: Set<String>): List<String> =
			getEntityIdsExhaustingPagination(dao).filter {
				it !in used
			}.toSet().let { idsToRemove ->
				idsToRemove.chunked(100).flatMap { idsChunk ->
					dao.purge(dao.getEntities(idsChunk)).map {
						it.id
					}.toList()
				}
			}

		private fun <T: StoredDocument> getEntityIdsExhaustingPagination(dao: InternalDAO<T>): Flow<String> = flow {
			val pageSize = 1001
			var paginationOffset = PaginationOffset<Nothing>(pageSize)
			do {
				val result = suspendRetry(10) {
					dao.getEntityIdsPaginated(paginationOffset).filterIsInstance<ViewRowNoDoc<*, *>>().map { it.id }.toList()
				}
				emitAll(result.take(pageSize - 1).asFlow())
				if (result.size >= pageSize) {
					paginationOffset = PaginationOffset(pageSize, result.last())
				}
			} while (result.size >= pageSize)
		}

		private fun <T : Any, R: Any> Flow<T>.buffered(bufferSize: Int, block: suspend (ArrayDeque<T>) -> Flow<R>): Flow<R> = flow {
			val queue = ArrayDeque<T>(bufferSize)
			this@buffered.collect {
				queue.add(it)
				if (queue.size == bufferSize) {
					emitAll(block(queue))
					queue.clear()
				}
			}
			emitAll(block(queue))
		}
	}

}