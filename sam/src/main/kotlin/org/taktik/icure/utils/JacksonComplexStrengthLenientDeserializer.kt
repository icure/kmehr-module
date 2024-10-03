package org.taktik.icure.utils

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import org.taktik.icure.entities.samv2.embed.ComplexStrength
import org.taktik.icure.entities.samv2.embed.Quantity

class JacksonComplexStrengthLenientDeserializer : JsonDeserializer<ComplexStrength>() {
	override fun deserialize(p: JsonParser, ctxt: DeserializationContext): ComplexStrength {
		val currentNode = p.readValueAsTree<JsonNode>()
		return if (currentNode.get("quantity") == null) {
			ComplexStrength(quantity = p.codec.treeToValue(currentNode, Quantity::class.java))
		} else {
			p.codec.treeToValue(currentNode, ComplexStrength::class.java)
		}
	}
}