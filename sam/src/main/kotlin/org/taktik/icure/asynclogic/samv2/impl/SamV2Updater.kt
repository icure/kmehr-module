package org.taktik.icure.asynclogic.samv2.impl

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.taktik.couchdb.Client
import org.taktik.couchdb.create
import org.taktik.couchdb.update
import org.taktik.icure.asyncdao.CouchDbDispatcher
import org.taktik.icure.asyncdao.InternalDAO
import org.taktik.icure.asyncdao.samv2.AmpDAO
import org.taktik.icure.asyncdao.samv2.NmpDAO
import org.taktik.icure.asyncdao.samv2.ParagraphDAO
import org.taktik.icure.asyncdao.samv2.SamUpdateDAO
import org.taktik.icure.asyncdao.samv2.VerseDAO
import org.taktik.icure.asyncdao.samv2.VmpDAO
import org.taktik.icure.asynclogic.datastore.DatastoreInstanceProvider
import org.taktik.icure.asynclogic.datastore.IDatastoreInformation
import org.taktik.icure.asynclogic.samv2.UpdatesBridge
import org.taktik.icure.entities.base.StoredDocument
import org.taktik.icure.entities.samv2.updates.SamUpdate
import org.taktik.icure.entities.samv2.updates.SignatureUpdate
import org.taktik.icure.entities.samv2.updates.UpdateType
import org.taktik.icure.utils.GzipDeflateInputStream
import org.taktik.icure.utils.toFlow
import java.util.ArrayDeque

@Component
class SamV2Updater(
	@Qualifier("drugCouchDbDispatcher") private val drugsCouchDbDispatcher: CouchDbDispatcher,
	private val ampDAO: AmpDAO,
	private val vmpDAO: VmpDAO,
	private val nmpDAO: NmpDAO,
	private val paragraphDAO: ParagraphDAO,
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

	fun getCurrentJobStatus() = currentJob?.processStatus()
		?: listOf(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Missing, System.currentTimeMillis(), "No update job running or completed"))

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

		private suspend fun getCurrentSamUpdate(datastoreInformation: IDatastoreInformation): SamUpdate? =
			samUpdateDAO.getLastAppliedUpdate(datastoreInformation)

		suspend fun update(jwt: String) {
			try {
				_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Started, System.currentTimeMillis(), "Update job started"))
				val datastoreInfo = datastoreInstanceProvider.getInstanceAndGroup()
				val client = drugsCouchDbDispatcher.getClient(datastoreInfo)
				checkOnlyLocalNodeExists(client)
				val currentDrugsVersion = getCurrentSamUpdate(datastoreInfo)
				if (currentDrugsVersion == null) {
					loadSnapshotUpdate(jwt)
					loadDiffUpdate(
						jwt,
						checkNotNull(getCurrentSamUpdate(datastoreInfo)) { "After loading the snapshot, the current version cannot be null" }
					)
				} else {
					loadDiffUpdate(jwt, currentDrugsVersion)
				}
				_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Completed, System.currentTimeMillis(), "Update completed successfully"))
			} catch(e: Exception) {
				_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Error, System.currentTimeMillis(), e.stackTraceToString()))
			}

		}

		private suspend fun loadSnapshotUpdate(jwt: String) {
			val latestSnapshot = updatesBridge.getMostRecentSnapshot(jwt)
			_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying snapshot update ${latestSnapshot.version} - ${latestSnapshot.date}"))
			latestSnapshot.updates.forEach { (type, resourceName) ->
				when (type) {
					SamUpdate.BundleType.Amps -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Amp from snapshot update ${latestSnapshot.version} - ${latestSnapshot.date}"))
						val currentAmpIds = loadEntities(ampDAO, latestSnapshot.id, UpdateType.Snapshot, resourceName)
						deleteUnused(ampDAO, currentAmpIds)
					}
					SamUpdate.BundleType.NonMedicinals -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Nmp from snapshot update ${latestSnapshot.version} - ${latestSnapshot.date}"))
						val currentNmpIds = loadEntities(nmpDAO, latestSnapshot.id, UpdateType.Snapshot, resourceName)
						deleteUnused(nmpDAO, currentNmpIds)
					}
					SamUpdate.BundleType.Paragraphs -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Paragraphs from snapshot update ${latestSnapshot.version} - ${latestSnapshot.date}"))
						val currentParagraphIds = loadEntities(paragraphDAO, latestSnapshot.id, UpdateType.Snapshot, resourceName)
						deleteUnused(paragraphDAO, currentParagraphIds)
					}
					SamUpdate.BundleType.Verses -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Verses from snapshot update ${latestSnapshot.version} - ${latestSnapshot.date}"))
						val currentVersesIds = loadEntities(verseDAO, latestSnapshot.id, UpdateType.Snapshot, resourceName)
						deleteUnused(verseDAO, currentVersesIds)
					}
					SamUpdate.BundleType.Vmps -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Vmps from snapshot update ${latestSnapshot.version} - ${latestSnapshot.date}"))
						val currentVmpIds = loadEntities(vmpDAO, latestSnapshot.id, UpdateType.Snapshot, resourceName)
						deleteUnused(vmpDAO, currentVmpIds)
					}
					SamUpdate.BundleType.Signatures -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Signatures from snapshot update ${latestSnapshot.version} - ${latestSnapshot.date}"))
						loadSignatures(latestSnapshot.id, UpdateType.Snapshot, resourceName)
					}
				}
			}
			samUpdateDAO.save(latestSnapshot.copy(rev = null))
			_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Successfully applied snapshot update ${latestSnapshot.version} - ${latestSnapshot.date}"))
		}

		private suspend fun loadDiffUpdate(jwt: String, currentUpdateVersion: SamUpdate) {
			updatesBridge.getFollowingUpdates(jwt, currentUpdateVersion).forEach { samUpdate ->
				_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying diff update ${samUpdate.version} - ${samUpdate.date}"))
				samUpdate.updates.forEach { (type, resourceName) ->
					when (type) {
						SamUpdate.BundleType.Amps -> {
							_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Amps from diff update ${samUpdate.version} - ${samUpdate.date}"))
							loadEntities(ampDAO, samUpdate.id, UpdateType.Diff, resourceName)
							deleteEntities(ampDAO, samUpdate.id, UpdateType.Diff, resourceName)
						}
						SamUpdate.BundleType.NonMedicinals -> {
							_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Nmps from diff update ${samUpdate.version} - ${samUpdate.date}"))
							loadEntities(nmpDAO, samUpdate.id, UpdateType.Diff, resourceName)
							deleteEntities(nmpDAO, samUpdate.id, UpdateType.Diff, resourceName)
						}
						SamUpdate.BundleType.Paragraphs -> {
							_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Paragraphs from diff update ${samUpdate.version} - ${samUpdate.date}"))
							loadEntities(paragraphDAO, samUpdate.id, UpdateType.Diff, resourceName)
							deleteEntities(paragraphDAO, samUpdate.id, UpdateType.Diff, resourceName)
						}
						SamUpdate.BundleType.Verses -> {
							_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Verses from diff update ${samUpdate.version} - ${samUpdate.date}"))
							loadEntities(verseDAO, samUpdate.id, UpdateType.Diff, resourceName)
							deleteEntities(vmpDAO, samUpdate.id, UpdateType.Diff, resourceName)
						}
						SamUpdate.BundleType.Vmps -> {
							_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Vmps from diff update ${samUpdate.version} - ${samUpdate.date}"))
							loadEntities(vmpDAO, samUpdate.id, UpdateType.Diff, resourceName)
							deleteEntities(vmpDAO, samUpdate.id, UpdateType.Diff, resourceName)
						}
						SamUpdate.BundleType.Signatures -> {
							_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Signatures from diff update ${samUpdate.version} - ${samUpdate.date}"))
							loadSignatures(samUpdate.id, UpdateType.Diff, resourceName)
						}
					}
				}
				samUpdateDAO.save(samUpdate.copy(rev = null))
				_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Successfully applied diff update ${samUpdate.version} - ${samUpdate.date}"))
			}
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
						client.update(signatureUpdate.version.copy(rev = samVersion.rev))
					} ?: client.create(signatureUpdate.version.copy(rev = null))
				} else {
					val version = ampDAO.getSignature(datastoreInfo, signatureUpdate.type.name.lowercase())?.let {
						client.update(signatureUpdate.version.copy(rev = it.rev))
					} ?: client.create(signatureUpdate.version.copy(rev = null))
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
		): Set<String> {
			val updatedAmpIds = mutableSetOf<String>()
			updatesBridge.getEntityUpdateContent(T::class.java, updateVersion, type, resourceName).buffered(20) { loadedEntities ->
				val loadedAmpsIds = loadedEntities.map { it.id }
				updatedAmpIds.addAll(loadedAmpsIds)
				val currentAmps = dao.getEntities(loadedEntities.map { it.id }).toList().associateBy { it.id }
				loadedEntities.mapNotNull { entity ->
					when {
						!currentAmps.containsKey(entity.id) -> entity as T
						entity.withIdRev(rev = "0") != currentAmps.getValue(entity.id).withIdRev(rev = "0") -> entity.withIdRev(
							rev = checkNotNull(currentAmps.getValue(entity.id).rev) { "Entity ${entity.id} has no revision in the database" }
						) as T
						else -> null
					}
				}.let {
					dao.save(it)
				}
			}.toList()
			return updatedAmpIds
		}

		private suspend fun <T : StoredDocument> deleteEntities(
			dao: InternalDAO<T>,
			updateVersion: String,
			type: UpdateType,
			resourceName: String
		): List<String> {
			val entitiesToDelete = updatesBridge.getEntityDeleteContent(updateVersion, type, resourceName).let {
				dao.getEntities(it.toList())
			}
			return dao.purge(entitiesToDelete).map { it.id }.toList()
		}

		private suspend fun <T : StoredDocument> deleteUnused(dao: InternalDAO<T>, used: Set<String>): List<String> =
			dao.getEntities().filter {
				it.id !in used
			}.toList().let { entitiesToRemove ->
				dao.purge(entitiesToRemove.asFlow())
				entitiesToRemove.map { it.id }
			}

		private fun <T : Any, R: Any> Flow<T>.buffered(bufferSize: Int, block: suspend (ArrayDeque<T>) -> Flow<R>): Flow<R> = flow {
			val queue = ArrayDeque<T>(bufferSize)
			this@buffered.collect {
				queue.add(it)
				if (queue.size == 20) {
					emitAll(block(queue))
					queue.clear()
				}
			}
			emitAll(block(queue))
		}
	}

}