package org.taktik.icure.asynclogic.samv2.impl

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.TimeoutCancellationException
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
import org.taktik.icure.datastore.DatastoreInstanceProvider
import org.taktik.icure.asynclogic.samv2.UpdatesBridge
import org.taktik.icure.db.PaginationOffset
import org.taktik.icure.entities.base.StoredDocument
import org.taktik.icure.entities.samv2.Amp
import org.taktik.icure.entities.samv2.Nmp
import org.taktik.icure.entities.samv2.Paragraph
import org.taktik.icure.entities.samv2.PharmaceuticalForm
import org.taktik.icure.entities.samv2.Substance
import org.taktik.icure.entities.samv2.Verse
import org.taktik.icure.entities.samv2.Vmp
import org.taktik.icure.entities.samv2.VmpGroup
import org.taktik.icure.entities.samv2.updates.SamUpdate
import org.taktik.icure.entities.samv2.updates.SignatureUpdate
import org.taktik.icure.entities.samv2.updates.UpdateType
import org.taktik.icure.utils.GzipDeflateInputStream
import org.taktik.icure.utils.suspendRetry
import org.taktik.icure.utils.toFlow
import reactor.netty.http.client.PrematureCloseException
import java.util.ArrayDeque

class SamV2Updater(
	private val drugsCouchDbDispatcher: CouchDbDispatcher,
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

	fun startUpdateJob(jwt: String, forceSnapshot: Boolean, purgeEntities: Boolean, useUpdates: List<SamUpdate>?) {
		if (currentJob == null || currentJob?.isCompleted == true) {
			val task = SamV2UpdateTask()
			task.job = coroutineScope.launch {
				task.update(jwt, forceSnapshot, purgeEntities, useUpdates)
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

		val bundleTypeToHandler = SamUpdate.BundleType.entries.filter {
			it != SamUpdate.BundleType.Signatures
		}.associateWith {
			when(it) {
				SamUpdate.BundleType.Amps -> BundleHandler(ampDAO, Amp::class.java)
				SamUpdate.BundleType.NonMedicinals -> BundleHandler(nmpDAO, Nmp::class.java)
				SamUpdate.BundleType.Paragraphs -> BundleHandler(paragraphDAO, Paragraph::class.java)
				SamUpdate.BundleType.PharmaceuticalForms -> BundleHandler(pharmaceuticalFormDAO, PharmaceuticalForm::class.java)
				SamUpdate.BundleType.Substances -> BundleHandler(substanceDAO, Substance::class.java)
				SamUpdate.BundleType.Verses -> BundleHandler(verseDAO, Verse::class.java)
				SamUpdate.BundleType.Vmps -> BundleHandler(vmpDAO, Vmp::class.java)
				SamUpdate.BundleType.VmpGroups -> BundleHandler(vmpGroupDAO, VmpGroup::class.java)
				SamUpdate.BundleType.Signatures ->
					throw IllegalStateException("Cannot instantiate a BundleHandler for signatures")
			}
		}

		fun processStatus(): List<SamV2UpdateTaskLog> = _processStatus

		private suspend fun checkOnlyLocalNodeExists(client: Client) {
			val membership = client.membership()
			if (membership.allNodes.size != 1) {
				throw IllegalStateException("Multiple nodes found on local installation")
			}
		}

		private suspend fun getCurrentSamUpdate(): SamUpdate? = samUpdateDAO.getLastAppliedUpdate()

		suspend fun update(jwt: String, forceSnapshot: Boolean, purgeEntities: Boolean, useUpdates: List<SamUpdate>?) {
			try {
				_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Started, System.currentTimeMillis(), "Update job started"))
				val datastoreInfo = datastoreInstanceProvider.getInstanceAndGroup()
				val client = drugsCouchDbDispatcher.getClient(datastoreInfo)
				checkOnlyLocalNodeExists(client)
				val currentUpdate = getCurrentSamUpdate()
				_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Retrieving updates to apply"))
				val updates = useUpdates ?: updatesBridge.getFollowingUpdates(jwt, currentUpdate, forceSnapshot)
				if (updates.isNotEmpty()) {
					_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Updates to apply: ${updates.joinToString(", ") { "${it.type} - ${it.id}" }}"))
				} else {
					_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Drugs and ChapIV are up to date"))
				}
				updates.forEach { update ->
					when(update.type) {
						UpdateType.Diff -> loadDiffUpdate(update, purgeEntities)
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
				if (type == SamUpdate.BundleType.Signatures) {
					_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Signatures from snapshot update ${snapshot.version}"))
					loadSignatures(snapshot.id, UpdateType.Snapshot, resourceName)
				} else {
					val handler = bundleTypeToHandler.getValue(type)
					handler.loadSnapshotUpdate(samUpdate = snapshot, resourceName = resourceName, bundleType = type)
				}
			}
			saveOrUpdateVersionReference(snapshot)
			_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Successfully applied snapshot update ${snapshot.version}"))
		}

		private suspend fun loadDiffUpdate(samUpdate: SamUpdate, purgeEntities: Boolean) {
			_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying diff update ${samUpdate.version}"))
			samUpdate.updates.forEach { (type, resourceName) ->
				if (type == SamUpdate.BundleType.Signatures) {
					_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Applying Signatures updates from diff update ${samUpdate.version}"))
					loadSignatures(samUpdate.id, UpdateType.Diff, resourceName)
				} else {
					val handler = bundleTypeToHandler.getValue(type)
					handler.loadDiffUpdate(samUpdate = samUpdate, resourceName = resourceName, bundleType = type)
				}
			}
			samUpdate.deletions.forEach { (type, resourceName) ->
				val handler = bundleTypeToHandler[type]
				if (handler != null) {
					handler.applyDiffDeletions(
						samUpdate = samUpdate,
						resourceName = resourceName,
						bundleType = type,
						hardDelete = purgeEntities,
					)
				} else {
					_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Ignoring deletion bundle $type"))
				}
			}
			saveOrUpdateVersionReference(samUpdate)
			_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Successfully applied diff update ${samUpdate.version}"))
		}

		private suspend fun saveOrUpdateVersionReference(samUpdate: SamUpdate) {
			val update = samUpdateDAO.get(samUpdate.id)
			if (update == null) {
				_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Saving version reference"))
				samUpdateDAO.save(samUpdate.copy(rev = null))
			} else {
				_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Overwriting existing version reference"))
				samUpdateDAO.save(samUpdate.copy(rev = update.rev))
			}
		}

		private tailrec suspend fun loadSignatures(
			updateVersion: String,
			type: UpdateType,
			resourceName: String
		) {
			val datastoreInfo = datastoreInstanceProvider.getInstanceAndGroup()
			val client = drugsCouchDbDispatcher.getClient(datastoreInfo)
			// The compiler does not support tailrec with try/catch
			val result = runCatching {
				updatesBridge.getSignaturesUpdateContent(updateVersion, type, resourceName).collect { signatureUpdate ->
					_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Loaded Signature document of type ${signatureUpdate.type}"))
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
					_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Saved Signature document of type ${signatureUpdate.type}"))
				}
			}
			if (result.isFailure) {
				when (val exception = result.exceptionOrNull()) {
					is TimeoutCancellationException -> {
						_processStatus.addFirst(SamV2UpdateTaskLog(SamV2UpdateTaskLog.Status.Running, System.currentTimeMillis(), "Network timeout while loading Signatures, retrying"))
						loadSignatures(updateVersion, type, resourceName)
					}
					null -> throw IllegalStateException("Process failed without exception")
					else -> throw exception
				}
			}
		}

		private fun makeSignatures(productIds: Map<String, String>) =
			GzipDeflateInputStream(
				productIds.entries.sortedBy { it.key }
					.joinToString("\n") { "${it.key}|${it.value}" }
					.byteInputStream(Charsets.UTF_8)
			).toFlow()

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

		private inner class BundleHandler<T : StoredDocument>(
			private val dao: InternalDAO<T>,
			private val klass: Class<T>,
		) {

			suspend fun loadSnapshotUpdate(
				samUpdate: SamUpdate,
				resourceName: String,
				bundleType: SamUpdate.BundleType
			) {
				_processStatus.addFirst(
					SamV2UpdateTaskLog(
						status = SamV2UpdateTaskLog.Status.Running,
						time = System.currentTimeMillis(),
						msg = "Applying ${bundleType.name} from snapshot update ${samUpdate.version}"
					)
				)
				val currentIds = loadEntitiesRecursive(
					dao = dao,
					klass = klass,
					updateVersion = samUpdate.id,
					type = UpdateType.Snapshot,
					resourceName = resourceName
				)
				deleteUnused(dao = dao, used = currentIds)
			}

			suspend fun loadDiffUpdate(
				samUpdate: SamUpdate,
				resourceName: String,
				bundleType: SamUpdate.BundleType
			) {
				_processStatus.addFirst(
					SamV2UpdateTaskLog(
						status = SamV2UpdateTaskLog.Status.Running,
						time = System.currentTimeMillis(),
						msg = "Applying ${bundleType.name} from diff update ${samUpdate.version}"
					)
				)
				loadEntitiesRecursive(
					dao = dao,
					klass = klass,
					updateVersion = samUpdate.id,
					type = UpdateType.Diff,
					resourceName = resourceName
				)
			}

			suspend fun applyDiffDeletions(
				samUpdate: SamUpdate,
				resourceName: String,
				bundleType: SamUpdate.BundleType,
				hardDelete: Boolean
			) {
				_processStatus.addFirst(
					SamV2UpdateTaskLog(
						status = SamV2UpdateTaskLog.Status.Running,
						time = System.currentTimeMillis(),
						msg = "Deleting ${bundleType.name} from diff update ${samUpdate.version}"
					)
				)
				deleteEntities(
					dao = dao,
					updateVersion = samUpdate.id,
					type = UpdateType.Diff,
					resourceName = resourceName,
					hardDelete = hardDelete
				)
			}

		}
	}

}
