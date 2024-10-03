/*
 * Copyright (c) 2020. Taktik SA, All rights reserved.
 */

package org.taktik.icure.utils

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import org.taktik.icure.entities.samv2.embed.SamText

class JacksonSamTextLenientDeserializer : JsonDeserializer<SamText>() {
	override fun deserialize(p: JsonParser, ctxt: DeserializationContext): SamText? {
		return if (p.currentToken()?.isScalarValue == true) {
			p.valueAsString?.let { SamText(it, it, it, it) }
		} else {
			p.readValueAs(SamText::class.java)
		}
	}
}
