package org.taktik.icure.asynclogic.bridge

import com.icure.cardinal.sdk.CardinalBaseApis
import com.icure.cardinal.sdk.api.raw.RawDocumentApi
import com.icure.utils.InternalIcureApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DefaultDataBufferFactory
import org.springframework.stereotype.Service
import org.taktik.icure.asynclogic.DocumentLogic
import org.taktik.icure.asynclogic.bridge.mappers.DocumentMapper
import org.taktik.icure.asynclogic.objectstorage.DataAttachmentChange
import org.taktik.icure.domain.BatchUpdateDocumentInfo
import org.taktik.icure.entities.Document
import org.taktik.icure.entities.requests.BulkShareOrUpdateMetadataParams
import org.taktik.icure.entities.requests.EntityBulkShareResult
import org.taktik.icure.exceptions.BridgeException
import org.taktik.icure.exceptions.NotFoundRequestException
import org.taktik.icure.utils.toByteArray
import java.nio.ByteBuffer

@OptIn(InternalIcureApi::class)
@Service
class DocumentLogicBridge(
	private val sdk: CardinalBaseApis,
	private val rawDocumentApi: RawDocumentApi,
	private val documentMapper: DocumentMapper
) : GenericLogicBridge<Document>(), DocumentLogic {

	override fun bulkShareOrUpdateMetadata(requests: BulkShareOrUpdateMetadataParams): Flow<EntityBulkShareResult<Document>> {
		throw BridgeException()
	}

	override suspend fun createDocument(document: Document, strict: Boolean): Document? =
		rawDocumentApi.createDocument(documentMapper.map(document))
			.successBody()
			.let(documentMapper::map)

	override fun createOrModifyDocuments(documents: List<BatchUpdateDocumentInfo>, strict: Boolean): Flow<Document> {
		throw BridgeException()
	}

	override fun getAttachment(documentId: String, attachmentId: String): Flow<ByteBuffer> {
		throw BridgeException()
	}

	override suspend fun getDocument(documentId: String): Document? =
		sdk.document.getDocument(documentId)?.let(documentMapper::map)

	override fun getDocuments(documentIds: Collection<String>): Flow<Document> {
		throw BridgeException()
	}

	override suspend fun getDocumentsByExternalUuid(documentId: String): List<Document> {
		throw BridgeException()
	}

	override suspend fun getMainAttachment(documentId: String): Flow<DataBuffer> =
		sdk.document.let { api ->
			val document = getDocument(documentId)
			if(document?.attachmentId != null || document?.objectStoreReference != null) {
				val attachment = api.getRawMainAttachment(documentId)
				val bufferFactory = DefaultDataBufferFactory()
				val buffer = bufferFactory.allocateBuffer(attachment.size)
				buffer.write(attachment)
				flowOf(buffer)
			} else null
		} ?: emptyFlow()

	override suspend fun getMainAttachment(document: Document): Flow<DataBuffer> {
		throw BridgeException()
	}

	override fun listDocumentsByDocumentTypeHCPartySecretMessageKeys(
		documentTypeCode: String,
		hcPartyId: String,
		secretForeignKeys: List<String>
	): Flow<Document> {
		throw BridgeException()
	}

	@Deprecated("This method cannot include results with secure delegations, use listDocumentIdsByDataOwnerPatientCreated instead")
	override fun listDocumentsByHCPartySecretMessageKeys(
		hcPartyId: String,
		secretForeignKeys: List<String>
	): Flow<Document> {
		throw BridgeException()
	}

	override fun listDocumentIdsByDataOwnerPatientCreated(
		dataOwnerId: String,
		secretForeignKeys: Set<String>,
		startDate: Long?,
		endDate: Long?,
		descending: Boolean
	): Flow<String> {
		throw BridgeException()
	}

	override fun listDocumentsWithoutDelegation(limit: Int): Flow<Document> {
		throw BridgeException()
	}

	override suspend fun modifyDocument(updatedDocument: Document, strict: Boolean): Document? {
		throw BridgeException()
	}

	override suspend fun updateAttachments(
		documentId: String,
		documentRev: String?,
		mainAttachmentChange: DataAttachmentChange?,
		secondaryAttachmentsChanges: Map<String, DataAttachmentChange>
	): Document? = sdk.document.let { api ->
		if (documentRev == null) throw IllegalStateException("Cannot update attachments of a document with null rev")
		when(mainAttachmentChange) {
			is DataAttachmentChange.CreateOrUpdate -> api.setRawMainAttachment(
				documentId = documentId,
				rev = checkNotNull(documentRev) { "Rev cannot be null" },
				utis = mainAttachmentChange.utis,
				attachment = mainAttachmentChange.data.toByteArray(true),
				encrypted = false
			)
			is DataAttachmentChange.Delete -> api.deleteMainAttachment(
				entityId = documentId,
				rev = checkNotNull(documentRev) { "Rev cannot be null" }
			)
			else -> api.getDocument(documentId) ?: throw NotFoundRequestException("Document $documentId not found")
		}.let { initialDocument ->
			secondaryAttachmentsChanges.entries.fold(initialDocument) { doc, (key, update) ->
				when(update) {
					is DataAttachmentChange.CreateOrUpdate -> api.setRawSecondaryAttachment(
						documentId = doc.id,
						key = key,
						rev = checkNotNull(documentRev) { "Rev cannot be null" },
						utis = update.utis,
						attachment = update.data.toByteArray(true),
						encrypted = false
					)
					is DataAttachmentChange.Delete -> api.deleteSecondaryAttachment(
						documentId = doc.id,
						key = key,
						rev = checkNotNull(documentRev) { "Rev cannot be null" }
					)
				}
			}
		}.let(documentMapper::map)
	}
}
