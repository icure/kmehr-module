package org.taktik.icure.entities.samv2.updates

import org.taktik.icure.entities.samv2.SamVersion

data class SignatureUpdate(
	val version: SamVersion,
	val idsForAttachment: Map<String, String>? = null,
	val type: SignatureType
) {

	enum class SignatureType { SamV2, Amp, Nmp, Vmp }

}