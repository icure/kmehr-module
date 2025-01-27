package org.taktik.icure.asynclogic.samv2

import kotlinx.coroutines.flow.Flow
import org.taktik.icure.entities.base.StoredDocument
import org.taktik.icure.entities.samv2.updates.SamUpdate
import org.taktik.icure.entities.samv2.updates.SignatureUpdate
import org.taktik.icure.entities.samv2.updates.UpdateType

interface UpdatesBridge {

	suspend fun getFollowingUpdates(jwt: String, currentPatch: SamUpdate?): List<SamUpdate>
	fun <T : StoredDocument> getEntityUpdateContent(klass: Class<T>, patchId: String, type: UpdateType, resourceName: String): Flow<T>

	fun getSignaturesUpdateContent(patchId: String, type: UpdateType, resourceName: String): Flow<SignatureUpdate>
	fun getEntityDeleteContent(patchId: String, type: UpdateType, resourceName: String): Flow<String>
}