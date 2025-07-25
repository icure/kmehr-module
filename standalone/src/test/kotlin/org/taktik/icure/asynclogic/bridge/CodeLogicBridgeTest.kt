package org.taktik.icure.asynclogic.bridge

import com.icure.cardinal.sdk.CardinalBaseApis
import com.icure.cardinal.sdk.api.raw.RawCodeApi
import com.icure.utils.InternalIcureApi
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.TestInstance
import org.taktik.icure.asynclogic.bridge.mappers.CodeMapper
import org.taktik.icure.config.BridgeConfig
import org.taktik.icure.entities.base.Code
import org.taktik.icure.test.BaseKmehrTest
import org.taktik.icure.test.KmehrTestApplication
import org.taktik.icure.test.UserCredentials
import org.taktik.icure.test.createHealthcarePartyUser
import org.taktik.icure.test.uuid
import org.taktik.icure.test.withAuthenticatedReactorContext

@OptIn(InternalIcureApi::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CodeLogicBridgeTest(
	val sdk: CardinalBaseApis,
	val rawCodeApi: RawCodeApi,
	val bridgeConfig: BridgeConfig,
	val codeMapper: CodeMapper,
) : BaseKmehrTest() {

	init {
		runBlocking {
			val hcp = createHealthcarePartyUser(
				bridgeConfig.iCureUrl,
				KmehrTestApplication.masterHcp.login,
				KmehrTestApplication.masterHcp.password,
			)

			val codeBridge = CodeLogicBridge(
				sdk = sdk,
				rawCodeApi = rawCodeApi,
				codeMapper = codeMapper
			)

			codeLogicBridgeTest(codeBridge, hcp)
		}
	}
}

private fun StringSpec.codeLogicBridgeTest(
	codeBridge: CodeLogicBridge,
	credentials: UserCredentials
) {

	"Should be able of creating a code" {
		withAuthenticatedReactorContext(credentials) {
			val type = uuid().substring(0, 6)
			val code = uuid().substring(0, 6)
			val version = uuid().substring(0, 6)
			codeBridge.create(
				Code(
					id = "$type|$code|$version",
					type = type,
					code = code,
					version = version
				)
			).let {
				it shouldNotBe null
				it!!.type shouldBe type
				it.code shouldBe code
				it.version shouldBe version
			}
		}
	}

	"Should be able to check if a code is valid" {
		withAuthenticatedReactorContext(credentials) {
			val type = uuid().substring(0, 6)
			val code = uuid().substring(0, 6)
			val version = uuid().substring(0, 6)
			val newCode = codeBridge.create(
				Code(
					id = "$type|$code|$version",
					type = type,
					code = code,
					version = version
				)
			)
			newCode shouldNotBe null
			codeBridge.isValid(newCode!!.type, newCode.code, newCode.version) shouldBe true
		}
	}

	"Should be able to check if a code is not valid" {
		withAuthenticatedReactorContext(credentials) {
			val type = uuid().substring(0, 6)
			val code = uuid().substring(0, 6)
			val version = uuid().substring(0, 6)
			codeBridge.isValid(type, code, version) shouldBe false
		}
	}

	"isValid returns false if the code passed as parameter has the wrong version" {
		withAuthenticatedReactorContext(credentials) {
			val type = uuid().substring(0, 6)
			val code = uuid().substring(0, 6)
			val version = uuid().substring(0, 6)
			val newCode = codeBridge.create(
				Code(
					id = "$type|$code|$version",
					type = type,
					code = code,
					version = version
				)
			)
			newCode shouldNotBe null
			codeBridge.isValid(newCode!!.type, newCode.code, uuid().substring(0, 6)) shouldBe false
		}
	}

	"isValid returns true if the code passed exists and no version is passed" {
		withAuthenticatedReactorContext(credentials) {
			val type = uuid().substring(0, 6)
			val code = uuid().substring(0, 6)
			val version = uuid().substring(0, 6)
			val newCode = codeBridge.create(
				Code(
					id = "$type|$code|$version",
					type = type,
					code = code,
					version = version
				)
			)
			newCode shouldNotBe null
			codeBridge.isValid(newCode!!.type, newCode.code, null) shouldBe true
		}
	}

	"Should be able of retrieving a code by region, language, type and label" {
		withAuthenticatedReactorContext(credentials) {
			val type = uuid().substring(0, 6)
			val code = uuid().substring(0, 6)
			val version = uuid().substring(0, 6)
			val newCode = codeBridge.create(
				Code(
					id = "$type|$code|$version",
					type = type,
					code = code,
					version = version,
					regions = setOf(uuid().substring(0, 6)),
					label = mapOf(uuid().substring(0, 6) to uuid().substring(0, 6))
				)
			)
			newCode shouldNotBe null
			codeBridge.getCodeByLabel(
				newCode!!.regions.first(),
				newCode.label!!.values.first(),
				newCode.type!!,
				newCode.label!!.keys.toList()
			).let {
				it shouldNotBe null
				it?.id shouldBe newCode.id
			}
		}
	}

	"If no languages are passed, fr and nl are used by default" {
		withAuthenticatedReactorContext(credentials) {
			val type = uuid().substring(0, 6)
			val code = uuid().substring(0, 6)
			val version = uuid().substring(0, 6)
			val newCode = codeBridge.create(
				Code(
					id = "$type|$code|$version",
					type = type,
					code = code,
					version = version,
					regions = setOf(uuid().substring(0, 6)),
					label = mapOf("fr" to uuid().substring(0, 6))
				)
			)
			newCode shouldNotBe null
			codeBridge.getCodeByLabel(
				newCode!!.regions.first(),
				newCode.label!!.values.first(),
				newCode.type!!
			).let {
				it shouldNotBe null
				it?.id shouldBe newCode.id
			}
		}
	}

	"If the code is not found, null is returned" {
		withAuthenticatedReactorContext(credentials) {
			codeBridge.getCodeByLabel(
				uuid().substring(0, 6),
				uuid().substring(0, 6),
				uuid().substring(0, 6)
			) shouldBe null
		}
	}

	"Can get codes by type" {
		withAuthenticatedReactorContext(credentials) {
			val types = List(3) { uuid().substring(0, 6) }
			val codes = types.flatMap { type ->
				listOf(
					Code(id = "$type|CODE-A|1", type = type, code = "CODE-A", version = "1", regions = setOf("fr", "be")),
					Code(id = "$type|CODE-A|2", type = type, code = "CODE-A", version = "2", regions = setOf("fr", "be")),
					Code(id = "$type|CODE-B|1", type = type, code = "CODE-B", version = "1", regions = setOf("fr", "be")),
					Code(id = "$type|CODE-B|2", type = type, code = "CODE-B", version = "2", regions = setOf("fr", "be")),
					Code(id = "$type|CODE-C|1", type = type, code = "CODE-C", version = "1", regions = setOf("fr", "be")),
					Code(id = "$type|CODE-C|2", type = type, code = "CODE-C", version = "2", regions = setOf("fr", "be")),
				)
			}.map { codeBridge.create(it) }
			val typeToRetrieve = types.random()
			val retrievedCodes = codeBridge.findCodesBy(typeToRetrieve, null, null).toList()
			retrievedCodes.shouldNotBeEmpty()
			retrievedCodes shouldContainExactlyInAnyOrder codes.filter { it?.type == typeToRetrieve }
		}
	}
}
