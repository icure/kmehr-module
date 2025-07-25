/*
 * Copyright (c) 2020. Taktik SA, All rights reserved.
 */

package org.taktik.icure.asyncdao.samv2

import kotlinx.coroutines.flow.Flow
import org.taktik.icure.asyncdao.InternalDAO
import org.taktik.icure.datastore.IDatastoreInformation
import org.taktik.icure.entities.samv2.Verse

interface VerseDAO : InternalDAO<Verse> {
	fun listVerses(datastoreInformation: IDatastoreInformation, chapterName: String, paragraphName: String): Flow<Verse>
}
