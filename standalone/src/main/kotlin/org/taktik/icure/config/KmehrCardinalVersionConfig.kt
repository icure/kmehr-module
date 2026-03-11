package org.taktik.icure.config

import org.springframework.stereotype.Service
import org.taktik.icure.entities.utils.SemanticVersion

@Service
class KmehrCardinalVersionConfig : CardinalVersionConfig {
	override suspend fun getUserCardinalVersion(): SemanticVersion? = null

	override suspend fun shouldUseCardinalModel(): Boolean = false

}