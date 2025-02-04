package org.taktik.icure.exceptions

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import org.taktik.icure.asynclogic.bridge.CodeLogicBridge

class BridgeExceptionTest: StringSpec({

	"Should write the unimplemented method in the message" {
		val codeLogic = CodeLogicBridge(mockk(relaxed = true), mockk(relaxed = true), mockk(relaxed = true))
		shouldThrow<BridgeException> {
			codeLogic.modify(emptyList()).toList()
		}.also {
			it.message shouldBe "Bridge method not implemented: org.taktik.icure.asynclogic.bridge.CodeLogicBridge.modify"
		}
	}

})