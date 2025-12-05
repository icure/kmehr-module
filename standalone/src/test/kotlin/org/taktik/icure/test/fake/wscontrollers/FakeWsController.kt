package org.taktik.icure.test.fake.wscontrollers

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.reactor.mono
import org.springframework.stereotype.Component
import org.taktik.icure.asynclogic.bridge.HealthcarePartyLogicBridge
import org.taktik.icure.asynclogic.impl.BridgeAsyncSessionLogic
import org.taktik.icure.security.jwt.JwtDecoder
import org.taktik.icure.services.external.http.WsController
import org.taktik.icure.services.external.http.websocket.annotation.WSOperation
import org.taktik.icure.services.external.http.websocket.annotation.WSRequestMapping

@Component
@WSRequestMapping("/ws/fake")
class FakeWsController(
	private val hcpBridge: HealthcarePartyLogicBridge,
	private val sessionLogic: BridgeAsyncSessionLogic,
) : WsController {

	private fun isJwtExpired(jwt: String): Boolean {
		val expirationSeconds = JwtDecoder.decodeExpirationSeconds(jwt)
		return expirationSeconds < (System.currentTimeMillis() / 1000) - 30
	}

	@WSRequestMapping("/echo")
	@WSOperation(PlainTextOperation::class)
	fun echo(operation: PlainTextOperation) = mono {
		val response = sessionLogic.getCurrentDataOwnerId()
		operation.textResponse(response)
	}

	@WSRequestMapping("/slowOp")
	@WSOperation(PlainTextOperation::class)
	fun slowOp(operation: PlainTextOperation) = mono {
		val jwt = sessionLogic.getCurrentJWT()
		assert(jwt?.let { !isJwtExpired(jwt) } ?: false)
		delay(10_000)
		assert(jwt?.let { !isJwtExpired(jwt) } ?: false)
		val hcpId = sessionLogic.getCurrentDataOwnerId()
		val currentHcp = hcpBridge.getHealthcareParties(listOf(hcpId)).first()
		operation.textResponse(currentHcp.id)
	}

}
