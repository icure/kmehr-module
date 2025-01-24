package org.taktik.icure.asynclogic.samv2.impl

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.taktik.couchdb.Client
import org.taktik.couchdb.create
import org.taktik.couchdb.exception.CouchDbConflictException
import org.taktik.couchdb.update
import org.taktik.icure.asyncdao.CouchDbDispatcher
import org.taktik.icure.asyncdao.InternalDAO
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
import org.taktik.icure.entities.base.StoredDocument
import org.taktik.icure.entities.samv2.updates.SamUpdate
import org.taktik.icure.entities.samv2.updates.SignatureUpdate
import org.taktik.icure.entities.samv2.updates.UpdateType
import org.taktik.icure.utils.GzipDeflateInputStream
import org.taktik.icure.utils.log
import org.taktik.icure.utils.toFlow
import java.util.ArrayDeque

@Component
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

		private suspend fun getCurrentSamUpdate(): SamUpdate? = samUpdateDAO.getLastAppliedUpdate()

		suspend fun update(jwt: String) {
			try {
				_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Started, System.currentTimeMillis(), "Update job started"))
				val datastoreInfo = datastoreInstanceProvider.getInstanceAndGroup()
				val client = drugsCouchDbDispatcher.getClient(datastoreInfo)
				checkOnlyLocalNodeExists(client)
				val currentDrugsVersion = getCurrentSamUpdate()
				if (currentDrugsVersion == null) {
					loadSnapshotUpdate(jwt)
					loadDiffUpdate(
						jwt,
						checkNotNull(getCurrentSamUpdate()) { "After loading the snapshot, the current version cannot be null" }
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
			_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying snapshot update ${latestSnapshot.version}"))
			latestSnapshot.updates.forEach { (type, resourceName) ->
				when (type) {
					SamUpdate.BundleType.Amps -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Amp from snapshot update ${latestSnapshot.version}"))
						val currentAmpIds = loadEntities(ampDAO, latestSnapshot.id, UpdateType.Snapshot, resourceName)
						deleteUnused(ampDAO, currentAmpIds)
					}
					SamUpdate.BundleType.NonMedicinals -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Nmp from snapshot update ${latestSnapshot.version}"))
						val currentNmpIds = loadEntities(nmpDAO, latestSnapshot.id, UpdateType.Snapshot, resourceName)
						deleteUnused(nmpDAO, currentNmpIds)
					}
					SamUpdate.BundleType.Paragraphs -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Paragraphs from snapshot update ${latestSnapshot.version}"))
						val currentParagraphIds = loadEntities(paragraphDAO, latestSnapshot.id, UpdateType.Snapshot, resourceName)
						deleteUnused(paragraphDAO, currentParagraphIds)
					}
					SamUpdate.BundleType.PharmaceuticalForms -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying PharmaceuticalForms from snapshot update ${latestSnapshot.version}"))
						val currentParagraphIds = loadEntities(pharmaceuticalFormDAO, latestSnapshot.id, UpdateType.Snapshot, resourceName)
						deleteUnused(pharmaceuticalFormDAO, currentParagraphIds)
					}
					SamUpdate.BundleType.Substances -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Substances from snapshot update ${latestSnapshot.version}"))
						val currentParagraphIds = loadEntities(substanceDAO, latestSnapshot.id, UpdateType.Snapshot, resourceName)
						deleteUnused(substanceDAO, currentParagraphIds)
					}
					SamUpdate.BundleType.Verses -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Verses from snapshot update ${latestSnapshot.version}"))
						val currentVersesIds = loadEntities(verseDAO, latestSnapshot.id, UpdateType.Snapshot, resourceName)
						deleteUnused(verseDAO, currentVersesIds)
					}
					SamUpdate.BundleType.Vmps -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Vmps from snapshot update ${latestSnapshot.version}"))
						val currentVmpIds = loadEntities(vmpDAO, latestSnapshot.id, UpdateType.Snapshot, resourceName)
						deleteUnused(vmpDAO, currentVmpIds)
					}
					SamUpdate.BundleType.VmpGroups -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Vmp Groups from snapshot update ${latestSnapshot.version}"))
						val currentVmpIds = loadEntities(vmpGroupDAO, latestSnapshot.id, UpdateType.Snapshot, resourceName)
						deleteUnused(vmpGroupDAO, currentVmpIds)
					}
					SamUpdate.BundleType.Signatures -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Signatures from snapshot update ${latestSnapshot.version}"))
						loadSignatures(latestSnapshot.id, UpdateType.Snapshot, resourceName)
					}
					else -> {}
				}
			}
			_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Saving version reference"))
			samUpdateDAO.save(latestSnapshot.copy(rev = null))
			_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Successfully applied snapshot update ${latestSnapshot.version}"))
		}

		private suspend fun loadDiffUpdate(jwt: String, currentUpdateVersion: SamUpdate) {
			updatesBridge.getFollowingUpdates(jwt, currentUpdateVersion).forEach { samUpdate ->
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
				}.takeIf { it.isNotEmpty() }?.let {
					dao.save(it)
				} ?: emptyFlow()
			}.toList()
			return updatedAmpIds
		}

		private suspend fun <T : StoredDocument> deleteEntities(
			dao: InternalDAO<T>,
			updateVersion: String,
			type: UpdateType,
			resourceName: String,
			hardDelete: Boolean
		): List<String> {
			val entitiesToDelete = updatesBridge.getEntityDeleteContent(updateVersion, type, resourceName).let {
				dao.getEntities(it.toList())
			}
			return if (hardDelete) {
				dao.purge(entitiesToDelete).map { it.id }.toList()
			} else {
				dao.remove(entitiesToDelete).map { it.id }.toList()
			}
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