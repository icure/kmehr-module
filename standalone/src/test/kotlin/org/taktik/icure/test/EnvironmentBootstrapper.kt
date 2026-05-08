package org.taktik.icure.test

import com.icure.cardinal.sdk.api.raw.RawApiConfig
import com.icure.cardinal.sdk.api.raw.impl.RawUserApiImpl
import com.icure.cardinal.sdk.model.ListOfIds
import com.icure.cardinal.sdk.model.User
import com.icure.cardinal.sdk.options.RequestRetryConfiguration
import com.icure.cardinal.sdk.utils.Serialization
import com.icure.test.setup.ICureTestSetup
import com.icure.utils.InternalIcureApi
import io.kotest.common.runBlocking
import io.ktor.client.HttpClient
import io.ktor.client.plugins.expectSuccess
import io.ktor.client.request.basicAuth
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.taktik.icure.entities.UserType
import java.io.File
import kotlin.time.ExperimentalTime

class EnvironmentBootstrapper : ApplicationContextInitializer<ConfigurableApplicationContext> {

	private val composeDir = "src/test/resources/docker"
	private val krakenCompose = System.getenv("KRAKEN_COMPOSE") ?: "file://$composeDir/docker-compose-cloud.yaml"
	private val masterUserId = "f422c984-4af8-11f1-8ec8-f78509c56b89"

	@OptIn(InternalIcureApi::class, ExperimentalTime::class)
	override fun initialize(applicationContext: ConfigurableApplicationContext) {
		runBlocking {
			ICureTestSetup.startKrakenEnvironment(krakenCompose, emptyList(), composeDir)
			ICureTestSetup.bootstrapCloud(
				groupId = "xx",
				groupPassword = "xx",
				groupUserId = uuid(),
				groupUserLogin = "john",
				couchDbUser = "icure",
				couchDbPassword = "icure",
				rootUserRoles = defaultRoles
			)
			loadRolesInConfig()
			createMasterUser()
		}
	}

	private suspend fun createMasterUser() {
		val isUserMissing = testHttpClient.get("http://localhost:15984/icure-xx-base/$masterUserId") {
			basicAuth("icure", "icure")
			contentType(ContentType.Application.Json)
		}.status.value == 404
		if (isUserMissing) {
			val user = """
			{
			  "_id": "$masterUserId",
			  "login": "jack",
			  "passwordHash": "1796980233375ccd113c972d946b2c4a7892e4f69c60684cfa730150047f9c0b",
			  "isUse2fa": true,
			  "type": "database",
			  "status": "ACTIVE",
			  "java_type": "org.taktik.icure.entities.User"
			}
		""".trimIndent()
			testHttpClient.post("http://localhost:15984/icure-xx-base") {
				basicAuth("icure", "icure")
				contentType(ContentType.Application.Json)
				setBody(user)
				expectSuccess = true
			}
			val userOnBase = """
			{
			  "_id": "xx:$masterUserId",
			  "identifier": [],
			  "loginIdentifiers": [],
			  "hashedLoginIdentifiers": [],
			  "status": "ACTIVE",
			  "passwordHash": "1796980233375ccd113c972d946b2c4a7892e4f69c60684cfa730150047f9c0b",
			  "userType": "USER",
			  "login": "jack",
			  "loginHash": "MWERWefm/3hD6kYndF6JIl/IZmIc/P29QIca9EE3R8w=",
			  "groupId": "xx",
			  "authenticationTokens": {},
			  "permissions": [],
			  "roles": ["xx:KMEHR_ROLE"],
			  "inheritsPermissionsToSubgroups": true,
			  "java_type": "org.taktik.icure.entities.User"
			}
		""".trimIndent()
			testHttpClient.post("http://localhost:15984/icure-__-base") {
				basicAuth("icure", "icure")
				contentType(ContentType.Application.Json)
				setBody(userOnBase)
				expectSuccess = true
			}
		}
	}

	private suspend fun loadRolesInConfig() {
		val payload = File("src/test/resources/roles.json").readText()
		testHttpClient.post("http://localhost:15984/icure-__-config/_bulk_docs") {
			basicAuth("icure", "icure")
			contentType(ContentType.Application.Json)
			setBody(payload)
			expectSuccess = true
		}
	}

	private val defaultRoles = mapOf(
		UserType.PATIENT.name to listOf("BASIC_USER", "BASIC_DATA_OWNER"),
		UserType.HCP.name to listOf("BASIC_USER", "BASIC_DATA_OWNER", "PATIENT_USER_MANAGER", "LEGACY_HCP"),
		UserType.DEVICE.name to listOf("BASIC_USER", "BASIC_DATA_OWNER"),
		UserType.USER.name to listOf("BASIC_USER")
	)

}