package org.taktik.icure.test

import com.icure.test.setup.ICureTestSetup
import io.kotest.common.runBlocking
import io.ktor.client.HttpClient
import io.ktor.client.plugins.expectSuccess
import io.ktor.client.request.basicAuth
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.taktik.icure.entities.UserType
import java.io.File

class EnvironmentBootstrapper : ApplicationContextInitializer<ConfigurableApplicationContext> {

	private val composeDir = "src/test/resources/docker"
	private val krakenCompose = System.getenv("KRAKEN_COMPOSE") ?: "file://$composeDir/docker-compose-cloud.yaml"

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