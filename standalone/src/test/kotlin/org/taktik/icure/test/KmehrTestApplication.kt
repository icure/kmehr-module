package org.taktik.icure.test

import com.fasterxml.jackson.databind.ObjectMapper
import com.icure.cardinal.sdk.api.raw.RawApiConfig
import com.icure.cardinal.sdk.api.raw.impl.RawGroupApiImpl
import com.icure.cardinal.sdk.api.raw.impl.RawHealthcarePartyApiImpl
import com.icure.cardinal.sdk.api.raw.impl.RawPermissionApiImpl
import com.icure.cardinal.sdk.api.raw.impl.RawUserApiImpl
import com.icure.cardinal.sdk.model.DatabaseInitialisation
import com.icure.cardinal.sdk.model.HealthcareParty
import com.icure.cardinal.sdk.model.User
import com.icure.cardinal.sdk.model.security.AlwaysPermissionItem
import com.icure.cardinal.sdk.model.security.Permission
import com.icure.cardinal.sdk.model.security.PermissionType
import com.icure.cardinal.sdk.options.RequestRetryConfiguration
import com.icure.utils.InternalIcureApi
import com.icure.cardinal.sdk.utils.Serialization
import io.ktor.client.plugins.expectSuccess
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.PropertySource
import org.springframework.test.context.ContextConfiguration
import org.taktik.icure.asyncdao.InternalDAO
import org.taktik.icure.asynclogic.impl.BridgeAsyncSessionLogic
import org.taktik.icure.config.BridgeConfig
import org.taktik.icure.security.jwt.JwtKeyUtils
import org.taktik.icure.test.fake.components.FakeBridgeCredentialsManager
import java.security.interfaces.RSAPublicKey

@SpringBootApplication(
	scanBasePackages = [
		"org.springframework.boot.autoconfigure.aop",
		"org.springframework.boot.autoconfigure.context",
		"org.springframework.boot.autoconfigure.validation",
		"org.springframework.boot.autoconfigure.websocket",
		"org.taktik.icure.config",
		"org.taktik.icure.asynclogic",
		"org.taktik.icure.security",
		"org.taktik.icure.config",
		"org.taktik.icure.asynclogic",
		"org.taktik.icure.asyncdao",
		"org.taktik.icure.be.ehealth.logic",
		"org.taktik.icure.be.ehealth.logic.impl",
		"org.taktik.icure.be.format.logic",
		"org.taktik.icure.db",
		"org.taktik.icure.errors",
		"org.taktik.icure.services.external.rest",
		"org.taktik.icure.services.external.http",
		"org.taktik.icure.services.external.rest.v1.controllers",
		"org.taktik.icure.services.external.rest.v1.mapper",
		"org.taktik.icure.services.external.rest.v2.mapper",
		"org.taktik.icure.test",
		"org.taktik.icure.test.fake.controllers",
		"org.taktik.icure.test.fake.wscontrollers"
	]
)
@ContextConfiguration(initializers = [EnvironmentBootstrapper::class])
@PropertySource("classpath:kmehr-test.properties")
@TestConfiguration
class KmehrTestApplication {

	companion object {
		lateinit var groupId: String
		lateinit var masterHcp: UserCredentials
		lateinit var fakeSessionLogic: BridgeAsyncSessionLogic
		lateinit var jwtAuthPublicKey: RSAPublicKey
	}

	@Value("\${icure.backend.url}")
	val baseICurePath = ""

	@OptIn(InternalIcureApi::class)
	@Bean
	fun performStartupTasks(
		bridgeConfig: BridgeConfig,
		objectMapper: ObjectMapper,
		internalDaos: List<InternalDAO<*>>
	) = ApplicationRunner {
		runBlocking {
			val authProvider = getAuthProvider(baseICurePath, "john", "LetMeIn")
			val groupApi = RawGroupApiImpl(
				apiUrl = baseICurePath,
				authProvider = authProvider,
				rawApiConfig = RawApiConfig(
					httpClient = testHttpClient,
					json = Serialization.json,
					additionalHeaders = emptyMap(),
					requestTimeout = null,
					retryConfiguration = RequestRetryConfiguration(),
				)
			)
			val userApi = RawUserApiImpl(
				apiUrl = baseICurePath,
				authProvider = authProvider,
				rawApiConfig = RawApiConfig(
					httpClient = testHttpClient,
					json = Serialization.json,
					additionalHeaders = emptyMap(),
					requestTimeout = null,
					retryConfiguration = RequestRetryConfiguration(),
				)
			)
			val hcpApi = RawHealthcarePartyApiImpl(
				apiUrl = baseICurePath,
				authProvider = authProvider,
				rawApiConfig = RawApiConfig(
					httpClient = testHttpClient,
					json = Serialization.json,
					additionalHeaders = emptyMap(),
					requestTimeout = null,
					retryConfiguration = RequestRetryConfiguration(),
				)
			)

			val jwtAuthPublicKeyAsString = testHttpClient.get("$baseICurePath/rest/v2/auth/publicKey/authJwt") {
				expectSuccess = true
			}.bodyAsText()
			jwtAuthPublicKey = JwtKeyUtils.decodePublicKeyFromString(jwtAuthPublicKeyAsString)

			val testGroupId = groupApi.listGroups().successBody().firstOrNull{ it.id.startsWith("e2e-test") }?.id
				?: "e2e-test-${uuid()}".also {
					groupApi.createGroup(
						id = it,
						name = "test",
						type = null,
						password = uuid(),
						server = null,
						q = null,
						superGroup = null,
						applicationId = null,
						initialisationData = DatabaseInitialisation(null, null, null, null),
					).successBody()
				}

			val createdHcp = hcpApi.createHealthcarePartyInGroup(
				testGroupId,
				HealthcareParty(
					uuid(),
					name = "Mr. Darcy"
				)
			).successBody()

			val userLogin = generateEmail()
			val userPwd = uuid()
			val createdUser = userApi.createUserInGroup(
				testGroupId,
				User(
					uuid(),
					login = userLogin,
					email = userLogin,
					passwordHash = userPwd,
					healthcarePartyId = createdHcp.id
				)
			).successBody()

			assignAdminPermissionToUser(testGroupId, createdUser.id)
			checkIfUserIsAvailable(userLogin, userPwd)

			internalDaos.forEach {
				it.forceInitStandardDesignDocument(true)
			}

			groupId = testGroupId
			masterHcp = UserCredentials(createdUser.id, userLogin, userPwd, createdHcp.id)
			fakeSessionLogic = BridgeAsyncSessionLogic(
				bridgeConfig = bridgeConfig,
				credentialsManager = FakeBridgeCredentialsManager(bridgeConfig, masterHcp.login, masterHcp.password),
				objectMapper = objectMapper,
				httpClient = testHttpClient,
			)
		}
	}

	@OptIn(InternalIcureApi::class)
	private suspend fun checkIfUserIsAvailable(username: String, password: String) = flow<Unit> {
		val authProvider = getAuthProvider(baseICurePath, username, password)
		RawUserApiImpl(
			apiUrl = baseICurePath,
			authProvider = authProvider,
			rawApiConfig = RawApiConfig(
				httpClient = testHttpClient,
				json = Serialization.json,
				additionalHeaders = emptyMap(),
				requestTimeout = null,
				retryConfiguration = RequestRetryConfiguration(),
			)
		).getCurrentUser().successBody()
	}.retry(5) {
		delay(2_000)
		true
	}.collect()

	@OptIn(InternalIcureApi::class)
	private suspend fun assignAdminPermissionToUser(groupId: String, userId: String) = flow<Unit> {
		val authProvider = getAuthProvider(baseICurePath, "john", "LetMeIn")
		RawPermissionApiImpl(
			apiUrl = baseICurePath,
			authProvider = authProvider,
			rawApiConfig = RawApiConfig(
				httpClient = testHttpClient,
				json = Serialization.json,
				additionalHeaders = emptyMap(),
				requestTimeout = null,
				retryConfiguration = RequestRetryConfiguration(),
			)
		).modifyUserPermissions(
			"$groupId:$userId",
			Permission(
				grants = setOf(
					AlwaysPermissionItem(
						PermissionType.Admin
					)
				)
			)
		)
	}.retry(5) {
		delay(2_000)
		true
	}.collect()
}
