package org.taktik.icure.asyncdao.samv2

import org.taktik.icure.asyncdao.InternalDAO
import org.taktik.icure.entities.samv2.updates.SamUpdate

interface SamUpdateDAO : InternalDAO<SamUpdate> {

	suspend fun getLastAppliedUpdate(): SamUpdate?

}