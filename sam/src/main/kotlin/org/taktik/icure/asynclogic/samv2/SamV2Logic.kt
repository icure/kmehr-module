/*
 * Copyright (c) 2020. Taktik SA, All rights reserved.
 */

package org.taktik.icure.asynclogic.samv2

import kotlinx.coroutines.flow.Flow
import org.taktik.couchdb.ViewQueryResultEvent
import org.taktik.icure.db.PaginationOffset
import org.taktik.icure.entities.samv2.Amp
import org.taktik.icure.entities.samv2.Nmp
import org.taktik.icure.entities.samv2.Paragraph
import org.taktik.icure.entities.samv2.PharmaceuticalForm
import org.taktik.icure.entities.samv2.ProductId
import org.taktik.icure.entities.samv2.SamVersion
import org.taktik.icure.entities.samv2.Substance
import org.taktik.icure.entities.samv2.Verse
import org.taktik.icure.entities.samv2.Vmp
import org.taktik.icure.entities.samv2.VmpGroup

interface SamV2Logic {
    fun findAmpsByLabel(language: String?, label: String, paginationOffset: PaginationOffset<List<String>>): Flow<Amp>
    fun findNmpsByLabel(language: String?, label: String?, paginationOffset: PaginationOffset<List<String>>): Flow<ViewQueryResultEvent>
    fun findVmpsByLabel(language: String?, label: String?, paginationOffset: PaginationOffset<List<String>>): Flow<ViewQueryResultEvent>
    fun findVmpGroupsByLabel(language: String?, label: String?, paginationOffset: PaginationOffset<List<String>>): Flow<ViewQueryResultEvent>
    fun findVmpGroupsByVmpGroupCode(vmpgCode: String, paginationOffset: PaginationOffset<String>): Flow<ViewQueryResultEvent>
    fun findVmpGroups(paginationOffset: PaginationOffset<String>): Flow<ViewQueryResultEvent>
    fun findVmpsByVmpCode(vmpCode: String, paginationOffset: PaginationOffset<String>): Flow<ViewQueryResultEvent>
    fun findVmpsByGroupCode(vmpgCode: String, paginationOffset: PaginationOffset<String>): Flow<ViewQueryResultEvent>
    fun findVmpsByGroupId(vmpgId: String, paginationOffset: PaginationOffset<String>): Flow<ViewQueryResultEvent>
    fun findAmpsByVmpGroupCode(vmpgCode: String, paginationOffset: PaginationOffset<String>): Flow<ViewQueryResultEvent>
    fun findAmpsByVmpGroupId(vmpgId: String, paginationOffset: PaginationOffset<String>): Flow<ViewQueryResultEvent>
    fun findAmpsByVmpCode(vmpCode: String, paginationOffset: PaginationOffset<String>): Flow<ViewQueryResultEvent>
    fun findAmpsByVmpId(vmpId: String, paginationOffset: PaginationOffset<String>): Flow<ViewQueryResultEvent>
    fun findAmpsByAtcCode(atcCode: String, paginationOffset: PaginationOffset<String>): Flow<ViewQueryResultEvent>
    fun findAmpsByDmppCode(dmppCode: String): Flow<ViewQueryResultEvent>
    fun findAmpsByAmpCode(ampCode: String): Flow<ViewQueryResultEvent>

    fun listAmpIdsByLabel(language: String?, label: String?): Flow<String>
    fun listNmpIdsByLabel(language: String?, label: String?): Flow<String>
    fun listVmpIdsByLabel(language: String?, label: String?): Flow<String>
    fun listVmpGroupIdsByLabel(language: String?, label: String?): Flow<String>
    fun listVmpIdsByGroupCode(vmpgCode: String): Flow<String>
    fun listVmpIdsByGroupId(vmpgId: String): Flow<String>
    fun listAmpIdsByVmpGroupCode(vmpgCode: String): Flow<String>
    fun listAmpIdsByVmpGroupId(vmpgId: String): Flow<String>
    fun listAmpIdsByVmpCode(vmpCode: String): Flow<String>
    fun listAmpIdsByVmpId(vmpId: String): Flow<String>

    suspend fun getVersion(): SamVersion?
    suspend fun listAmpProductIds(ids: Collection<String>): List<ProductId?>
    suspend fun listVmpgProductIds(ids: Collection<String>): List<ProductId?>
    suspend fun listNmpProductIds(ids: Collection<String>): List<ProductId?>
    fun listSubstances(): Flow<Substance>
    fun listPharmaceuticalForms(): Flow<PharmaceuticalForm>

    fun listVmpsByVmpCodes(vmpCodes: List<String>): Flow<Vmp>
    fun listVmpsByGroupIds(vmpgIds: List<String>): Flow<Vmp>
    fun listAmpsByGroupCodes(vmpgCodes: List<String>): Flow<Amp>
    fun listAmpsByDmppCodes(dmppCodes: List<String>): Flow<Amp>
    fun listAmpsByGroupIds(groupIds: List<String>): Flow<Amp>
    fun listAmpsByVmpCodes(vmpgCodes: List<String>): Flow<Amp>
    fun listAmpsByVmpIds(vmpIds: List<String>): Flow<Amp>
    fun listVmpGroupsByVmpGroupCodes(vmpgCodes: List<String>): Flow<VmpGroup>
    fun listNmpsByCnks(cnks: List<String>): Flow<Nmp>

    fun findParagraphs(searchString: String, language: String): Flow<Paragraph>
    fun findParagraphsWithCnk(cnk: Long, language: String): Flow<Paragraph>

    fun listVerses(chapterName: String, paragraphName: String): Flow<Verse>

    suspend fun getParagraphInfos(chapterName: String, paragraphName: String): Paragraph
    suspend fun getVersesHierarchy(chapterName: String, paragraphName: String): Verse?

    fun getAmpsForParagraph(chapterName: String, paragraphName: String): Flow<Amp>
    fun getVtmNamesForParagraph(chapterName: String, paragraphName: String, language: String): Flow<String>
}
