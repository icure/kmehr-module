@file:Suppress("ktlint:standard:no-wildcard-imports")

package org.taktik.icure.asynclogic.samv2.impl

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.taktik.couchdb.ViewQueryResultEvent
import org.taktik.couchdb.ViewRowWithDoc
import org.taktik.couchdb.entity.ComplexKey
import org.taktik.icure.asyncdao.samv2.*
import org.taktik.icure.asynclogic.samv2.SamV2Logic
import org.taktik.icure.datastore.DatastoreInstanceProvider
import org.taktik.icure.db.PaginationOffset
import org.taktik.icure.db.sanitize
import org.taktik.icure.entities.samv2.*
import org.taktik.icure.entities.samv2.embed.SamText
import org.taktik.icure.pagination.PaginationElement
import org.taktik.icure.pagination.limitIncludingKey
import org.taktik.icure.pagination.toPaginatedFlow
import org.taktik.icure.utils.aggregateResultsAsFlow
import org.taktik.icure.utils.bufferedChunks
import org.taktik.icure.utils.distinct
import kotlin.math.min

@Service
@Profile("sam")
class SamV2LogicImpl(
    private val ampDAO: AmpDAO,
    private val vmpDAO: VmpDAO,
    private val vmpGroupDAO: VmpGroupDAO,
    private val nmpDAO: NmpDAO,
    private val substanceDAO: SubstanceDAO,
    private val pharmaceuticalFormDAO: PharmaceuticalFormDAO,
    private val paragraphDAO: ParagraphDAO,
    private val verseDAO: VerseDAO,
    private val datastoreInstanceProvider: DatastoreInstanceProvider,
) : SamV2Logic {
    private val mutex = Mutex()

    private var ampProductIds: Map<String, String>? = null
    private var nmpProductIds: Map<String, String>? = null
    private var vmpProductIds: Map<String, String>? = null

    override fun findVmpsByGroupId(
        vmpgId: String,
        paginationOffset: PaginationOffset<String>,
    ): Flow<PaginationElement> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(
                vmpDAO
                    .findVmpsByGroupId(datastore, vmpgId, paginationOffset.limitIncludingKey())
                    .toPaginatedFlow<Vmp>(paginationOffset.limit),
            )
        }

    override fun findAmpsByVmpGroupCode(
        vmpgCode: String,
        paginationOffset: PaginationOffset<String>,
    ): Flow<ViewQueryResultEvent> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(ampDAO.findAmpsByVmpGroupCode(datastore, vmpgCode, paginationOffset))
        }

    override fun findAmpsByVmpGroupId(
        vmpgId: String,
        paginationOffset: PaginationOffset<String>,
    ): Flow<ViewQueryResultEvent> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(ampDAO.findAmpsByVmpGroupId(datastore, vmpgId, paginationOffset))
        }

    override fun findAmpsByVmpCode(
        vmpCode: String,
        paginationOffset: PaginationOffset<String>,
    ): Flow<ViewQueryResultEvent> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(ampDAO.findAmpsByVmpCode(datastore, vmpCode, paginationOffset))
        }

    override fun findAmpsByVmpId(
        vmpId: String,
        paginationOffset: PaginationOffset<String>,
    ): Flow<ViewQueryResultEvent> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(ampDAO.findAmpsByVmpId(datastore, vmpId, paginationOffset.limitIncludingKey()))
        }

    override fun findAmpsByDmppCode(dmppCode: String): Flow<ViewQueryResultEvent> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(ampDAO.findAmpsByDmppCode(datastore, dmppCode))
        }

    override fun findAmpsByAmpCode(ampCode: String): Flow<ViewQueryResultEvent> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(ampDAO.findAmpsByAmpCode(datastore, ampCode))
        }

    override fun listVmpIdsByGroupCode(vmpgCode: String): Flow<String> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(vmpDAO.listVmpIdsByGroupCode(datastore, vmpgCode))
        }

    override fun listVmpIdsByGroupId(vmpgId: String): Flow<String> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(vmpDAO.listVmpIdsByGroupId(datastore, vmpgId))
        }

    override fun listAmpIdsByVmpGroupCode(vmpgCode: String): Flow<String> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(ampDAO.listAmpIdsByVmpGroupCode(datastore, vmpgCode))
        }

    override fun listAmpIdsByVmpGroupId(vmpgId: String): Flow<String> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(ampDAO.listAmpIdsByVmpGroupId(datastore, vmpgId))
        }

    override fun listAmpIdsByVmpCode(vmpCode: String): Flow<String> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(ampDAO.listAmpIdsByVmpCode(datastore, vmpCode))
        }

    override fun listAmpIdsByVmpId(vmpId: String): Flow<String> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(ampDAO.listAmpIdsByVmpId(datastore, vmpId))
        }

    override suspend fun getVersion(): SamVersion? {
        val datastore = datastoreInstanceProvider.getInstanceAndGroup()
        return ampDAO.getVersion(datastore)
    }

    override suspend fun listAmpProductIds(ids: Collection<String>): List<ProductId?> {
        mutex.withLock {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            if (this.ampProductIds == null) {
                this.ampProductIds = ampDAO.getProductIdsFromSignature(datastore, "amp")
                return ids.map { id -> this.ampProductIds?.get(id)?.let { ProductId(id = id, productId = it) } }
            }
        }
        return ids.map { id -> this.ampProductIds?.get(id)?.let { ProductId(id = id, productId = it) } }
    }

    override suspend fun listVmpgProductIds(ids: Collection<String>): List<ProductId?> {
        mutex.withLock {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            if (this.vmpProductIds == null) {
                this.vmpProductIds = ampDAO.getProductIdsFromSignature(datastore, "vmp")
                return ids.map { id -> this.vmpProductIds?.get(id)?.let { ProductId(id = id, productId = it) } }
            }
        }
        return ids.map { id -> this.vmpProductIds?.get(id)?.let { ProductId(id = id, productId = it) } }
    }

    override suspend fun listNmpProductIds(ids: Collection<String>): List<ProductId?> {
        mutex.withLock {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            if (this.nmpProductIds == null) {
                this.nmpProductIds = ampDAO.getProductIdsFromSignature(datastore, "nmp")
                return ids.map { id -> this.nmpProductIds?.get(id)?.let { ProductId(id = id, productId = it) } }
            }
        }
        return ids.map { id -> this.nmpProductIds?.get(id)?.let { ProductId(id = id, productId = it) } }
    }

    override fun findVmpsByGroupCode(
        vmpgCode: String,
        paginationOffset: PaginationOffset<String>,
    ): Flow<PaginationElement> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(
                vmpDAO
                    .findVmpsByGroupCode(datastore, vmpgCode, paginationOffset.limitIncludingKey())
                    .toPaginatedFlow<Vmp>(paginationOffset.limit),
            )
        }


    /**
     * Finds all AMP entities with their associated AMPP CTI Extended identifiers matching the given label.
     *
     * This function searches for Actual Medicinal Products (AMPs) and their Packages (AMPPs)
     * by performing a text-based label search across multiple name fields. The search is case-insensitive
     * and supports multi-word queries, with all words required to match (AND logic).
     *
     * @param language Optional language code for localized name matching. Supported values are:
     *                 - "fr" for French
     *                 - "en" for English
     *                 - "de" for German
     *                 - "nl" for Dutch
     * @param label The search label string. Multiple words can be separated by spaces.
     *              Each word will be sanitized and must appear in at least one of the searchable fields.
     * @param onlyValidAmpps If true, returns only AMPs that have valid AMPPs with active commercializations.
     *                        If false, includes AMPs even if they have no valid commercializations.
     * @param paginationOffset Pagination offset configuration for limiting and cursor-based pagination.
     *                         The startDocumentId (if provided) should be in the format "ampId|ctiExtended".
     *
     * @return A Flow emitting pairs of (AMPP CTI Extended identifier, AMP entity).
     *         Each pair represents an AMP with one of its matching AMPPs.
     *         The flow respects the pagination limit and filtering criteria.
     *
     * The function searches across the following AMP name fields:
     * - Official name (always searched, not language-dependent)
     * - Abbreviated name (localized if language is specified)
     * - Prescription name (localized if language is specified)
     * - Generic name (localized if language is specified)
     * - AMPP-specific prescription name (localized if language is specified)
     *
     * When [onlyValidAmpps] is true, invalid AMPPs are removed from the returned AMP entities.
     */
    override fun findAmppsByLabel(
        language: String,
        label: String,
        onlyValidAmpps: Boolean,
        paginationOffset: PaginationOffset<Nothing>,
    ): Flow<Pair<String,Amp>> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            val labelComponents = label.split(" ").mapNotNull { it.sanitize() }
            val ampIds = labelComponents.maxByOrNull { it.length }?.let { ampDAO.listAmpAmppIdsByLabel(datastore, language, it).toSet().toList() }
                ?: throw IllegalArgumentException("Label must contain at least one valid component")

            emitAll(
                aggregateResultsAsFlow(
                    ids = ampIds,
                    limit = paginationOffset.limitIncludingKey().limit,
                    supplier = { ids -> ampDAO.getEntities(ids.map { it.first }).toList().mapIndexed { idx, it -> ampIds[idx].second to it }.asFlow() },
                    filter = { (ctiExtended, amp) ->
                        labelComponents.all { labelComponent ->
                            listOfNotNull(
                                amp.officialName,
                                amp.abbreviatedName?.localized(language)?.sanitize(),
                                amp.prescriptionName?.localized(language)?.sanitize(),
                                amp.name?.localized(language)?.sanitize(),
                                amp.ampps.find { it.ctiExtended === ctiExtended }?.prescriptionName?.localized(language)?.sanitize(),
                            ).any { it.contains(other = labelComponent, ignoreCase = true) }
                        } && (!onlyValidAmpps || amp.hasValidAmpps(includeWithoutCommercializations = false))
                    },
                    startDocumentId = paginationOffset.startDocumentId?.split(":")?.let { (a,b) -> a to b } ,
                    heuristic = if (labelComponents.size == 1) 1.1 else min(3.0, labelComponents.size.toDouble()),
                ).map {
                    if (onlyValidAmpps) {
                        it.first to it.second.removeInvalidAmpps(includeWithoutCommercializations = false)
                    } else {
                        it
                    }
                },
            )
        }

    override fun findAmpsByLabel(
        language: String,
        label: String,
        onlyValidAmpps: Boolean,
        paginationOffset: PaginationOffset<Nothing>,
    ): Flow<Amp> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            val labelComponents = label.split(" ").mapNotNull { it.sanitize() }
            val ampIds = labelComponents.maxByOrNull { it.length }?.let { ampDAO.listAmpIdsByLabel(datastore, language, it).toSet() }
                ?: throw IllegalArgumentException("Label must contain at least one valid component")
            emitAll(
                aggregateResultsAsFlow(
                    ids = ampIds,
                    limit = paginationOffset.limitIncludingKey().limit,
                    supplier = { ids -> ampDAO.getEntities(ids) },
                    filter = { amp ->
                        labelComponents.all { labelComponent ->
                            listOfNotNull(
                                amp.officialName,
                                amp.abbreviatedName?.localized(language)?.sanitize(),
                                amp.prescriptionName?.localized(language)?.sanitize(),
                                amp.name?.localized(language)?.sanitize(),
                            ).any { it.contains(other = labelComponent, ignoreCase = true) }
                        } && (!onlyValidAmpps || amp.hasValidAmpps(includeWithoutCommercializations = false))
                    },
                    startDocumentId = paginationOffset.startDocumentId,
                    heuristic = if (labelComponents.size == 1) 1.1 else min(3.0, labelComponents.size.toDouble()),
                ).map {
                    if (onlyValidAmpps) {
                        it.removeInvalidAmpps(includeWithoutCommercializations = false)
                    } else {
                        it
                    }
                },
            )
        }

    private fun SamText.localized(language: String?) =
        when (language) {
            "fr" -> this.fr
            "en" -> this.en
            "de" -> this.de
            "nl" -> this.nl
            else -> null
        }

    override fun listAmpIdsByLabel(
        language: String,
        label: String,
    ): Flow<String> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(ampDAO.listAmpIdsByLabel(datastore, language, label))
        }

    override fun findVmpsByLabel(
        language: String?,
        label: String?,
        paginationOffset: PaginationOffset<List<String>>,
    ): Flow<PaginationElement> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(
                vmpDAO
                    .findVmpsByLabel(datastore, language, label, paginationOffset.limitIncludingKey())
                    .toPaginatedFlow<Vmp>(paginationOffset.limit),
            )
        }

    override fun listVmpIdsByLabel(
        language: String?,
        label: String?,
    ): Flow<String> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(vmpDAO.listVmpIdsByLabel(datastore, language, label))
        }

    override fun findVmpGroupsByLabel(
        language: String?,
        label: String?,
        paginationOffset: PaginationOffset<List<String>>,
    ): Flow<ViewQueryResultEvent> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(vmpGroupDAO.findVmpGroupsByLabel(datastore, language, label, paginationOffset.limitIncludingKey()))
        }

    override fun findVmpGroupsByVmpGroupCode(
        vmpgCode: String,
        paginationOffset: PaginationOffset<String>,
    ): Flow<ViewQueryResultEvent> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(vmpGroupDAO.findVmpGroupsByVmpGroupCode(datastore, vmpgCode, paginationOffset.limitIncludingKey()))
        }

    override fun findVmpGroups(paginationOffset: PaginationOffset<String>): Flow<ViewQueryResultEvent> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(vmpGroupDAO.findVmpGroups(datastore, paginationOffset))
        }

    override fun findVmpsByVmpCode(
        vmpCode: String,
        paginationOffset: PaginationOffset<String>,
    ): Flow<PaginationElement> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(
                vmpDAO
                    .findVmpsByVmpCode(datastore, vmpCode, paginationOffset.limitIncludingKey())
                    .toPaginatedFlow<Vmp>(paginationOffset.limit),
            )
        }

    override fun listVmpGroupIdsByLabel(
        language: String?,
        label: String?,
    ): Flow<String> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(vmpGroupDAO.listVmpGroupIdsByLabel(datastore, language, label))
        }

    override fun findNmpsByLabel(
        language: String?,
        label: String?,
        paginationOffset: PaginationOffset<List<String>>,
    ): Flow<ViewQueryResultEvent> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(nmpDAO.findNmpsByLabel(datastore, language, label, paginationOffset.limitIncludingKey()))
        }

    override fun listNmpIdsByLabel(
        language: String?,
        label: String?,
    ): Flow<String> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(nmpDAO.listNmpIdsByLabel(datastore, language, label))
        }

    override fun listSubstances(): Flow<Substance> = substanceDAO.getEntities()

    override fun listPharmaceuticalForms(): Flow<PharmaceuticalForm> = pharmaceuticalFormDAO.getEntities()

    override fun listVmpsByVmpCodes(vmpCodes: List<String>): Flow<Vmp> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(vmpDAO.listVmpsByVmpCodes(datastore, vmpCodes))
        }

    override fun findAmpsByAtcCode(
        atcCode: String,
        paginationOffset: PaginationOffset<String>,
    ): Flow<ViewQueryResultEvent> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(ampDAO.findAmpsByAtc(datastore, atcCode, paginationOffset))
        }

    override fun listVmpsByGroupIds(vmpgIds: List<String>): Flow<Vmp> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(vmpDAO.listVmpsByGroupIds(datastore, vmpgIds))
        }

    override fun listAmpsByGroupCodes(vmpgCodes: List<String>): Flow<Amp> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(ampDAO.listAmpsByVmpGroupCodes(datastore, vmpgCodes))
        }

    override fun listAmpsByDmppCodes(dmppCodes: List<String>): Flow<Amp> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(ampDAO.listAmpsByDmppCodes(datastore, dmppCodes))
        }

    override fun listAmpNamesByDmppCode(dmppCode: String): Flow<SamText> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(
                ampDAO
                    .listAmpsByDmppCodes(
                        datastoreInformation = datastore,
                        dmppCodes = listOf(dmppCode),
                    ).filter {
                        it.hasValidAmpps(includeWithoutCommercializations = false)
                    }.mapNotNull {
                        it.name
                    },
            )
        }

    override fun listAmpsByGroupIds(groupIds: List<String>): Flow<Amp> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(ampDAO.listAmpsByVmpGroupIds(datastore, groupIds))
        }

    override fun listAmpsByVmpCodes(vmpgCodes: List<String>): Flow<Amp> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(ampDAO.listAmpsByVmpCodes(datastore, vmpgCodes))
        }

    override fun listAmpsByVmpIds(vmpIds: List<String>): Flow<Amp> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(ampDAO.listAmpsByVmpIds(datastore, vmpIds))
        }

    override fun listVmpGroupsByVmpGroupCodes(vmpgCodes: List<String>): Flow<VmpGroup> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(vmpGroupDAO.listVmpGroupsByVmpGroupCodes(datastore, vmpgCodes))
        }

    override fun listNmpsByCnks(cnks: List<String>): Flow<Nmp> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(nmpDAO.listNmpsByCnks(datastore, cnks))
        }

    override fun findParagraphs(
        searchString: String,
        language: String,
    ): Flow<Paragraph> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(
                paragraphDAO
                    .findParagraphs(datastore, searchString, language, PaginationOffset(1000))
                    .filterIsInstance<ViewRowWithDoc<ComplexKey, Int, Paragraph>>()
                    .map { it.doc },
            )
        }

    override fun findParagraphsWithCnk(
        cnk: Long,
        language: String,
    ): Flow<Paragraph> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(paragraphDAO.findParagraphsWithCnk(datastore, cnk, language))
        }

    override suspend fun getParagraphInfos(
        chapterName: String,
        paragraphName: String,
    ): Paragraph {
        val datastore = datastoreInstanceProvider.getInstanceAndGroup()
        return paragraphDAO.getParagraph(datastore, chapterName, paragraphName)
    }

    override suspend fun getVersesHierarchy(
        chapterName: String,
        paragraphName: String,
    ): Verse? {
        val allVerses: List<Verse> = listVerses(chapterName, paragraphName).toList()

        fun fillChildren(v: Verse): Verse = v.copy(children = allVerses.filter { it.verseSeqParent == v.verseSeq }.map { fillChildren(it) })

        return allVerses.takeIf { it.isNotEmpty() }?.let { fillChildren(it.first()) }
    }

    override fun listVerses(
        chapterName: String,
        paragraphName: String,
    ) = flow {
        val datastore = datastoreInstanceProvider.getInstanceAndGroup()
        emitAll(verseDAO.listVerses(datastore, chapterName, paragraphName))
    }

    override fun getAmpsForParagraph(
        chapterName: String,
        paragraphName: String,
    ): Flow<Amp> =
        flow {
            val datastore = datastoreInstanceProvider.getInstanceAndGroup()
            emitAll(
                ampDAO
                    .findAmpsByChapterParagraph(datastore, chapterName, paragraphName, PaginationOffset(1000))
                    .filterIsInstance<ViewRowWithDoc<ComplexKey, Int, Amp>>()
                    .map { it.doc },
            )
        }

    @ExperimentalCoroutinesApi
    @FlowPreview
    override fun getVtmNamesForParagraph(
        chapterName: String,
        paragraphName: String,
        language: String,
    ): Flow<String> =
        getAmpsForParagraph(chapterName, paragraphName)
            .bufferedChunks(100, 200)
            .flatMapConcat { amp ->
                vmpDAO.getEntities(amp.mapNotNull { it.vmp?.id }).mapNotNull {
                    it.vtm?.name?.let { t ->
                        when (language) {
                            "fr" -> t.fr
                            "en" -> t.en
                            "de" -> t.de
                            "nl" -> t.nl
                            else -> null
                        }
                    }
                }
            }.distinct()
}
