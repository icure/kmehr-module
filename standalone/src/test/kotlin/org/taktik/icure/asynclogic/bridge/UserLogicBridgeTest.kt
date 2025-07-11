package org.taktik.icure.asynclogic.bridge

import com.icure.cardinal.sdk.api.raw.impl.RawUserApiImpl
import com.icure.utils.InternalIcureApi
import com.icure.cardinal.sdk.utils.Serialization
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Value
import org.taktik.icure.asynclogic.bridge.mappers.UserMapper
import org.taktik.icure.config.BridgeConfig
import org.taktik.icure.entities.User
import org.taktik.icure.entities.security.AuthenticationToken
import org.taktik.icure.exceptions.BridgeException
import org.taktik.icure.security.jwt.JwtKeyUtils
import org.taktik.icure.test.*
import java.time.Instant

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserLogicBridgeTest(
	private val bridgeConfig: BridgeConfig,
	private val userMapper: UserMapper,
	@Value("\${jwt.auth.pub.key}") jwtAuthPublicKeyAsString: String
) : BaseKmehrTest() {

	private val jwtAuthPublicKey = JwtKeyUtils.decodePublicKeyFromString(jwtAuthPublicKeyAsString)

	private val hcp = runBlocking {
		createHealthcarePartyUser(
			bridgeConfig.iCureUrl,
			KmehrTestApplication.masterHcp.login,
			KmehrTestApplication.masterHcp.password,
			jwtAuthPublicKey
		)
	}

	init {
		runBlocking {
			val userBridge = UserLogicBridge(
				KmehrTestApplication.fakeSessionLogic,
				bridgeConfig,
				userMapper
			)

			userLogicBridgeTest(userBridge, hcp)
		}
	}

	@OptIn(InternalIcureApi::class)
	private val userApi = RawUserApiImpl(
		apiUrl = bridgeConfig.iCureUrl,
		authProvider = getAuthProvider(bridgeConfig.iCureUrl, hcp.userId, hcp.password),
		httpClient = testHttpClient,
		json = Serialization.json
	)

	@OptIn(InternalIcureApi::class)
	private suspend fun createUser(user: User) = userApi.createUser(userMapper.map(user)).successBody().let {
		userMapper.map(it.copy(
			authenticationTokens = it.authenticationTokens.mapValues { (_, token) -> token.copy(token = "*") }
		))
	}

	private fun StringSpec.userLogicBridgeTest(
		userBridge: UserLogicBridge,
		credentials: UserCredentials
	) {

		"Can get a User" {
			withAuthenticatedReactorContext(credentials) {
				val newUser = createUser(
					User(
						id = uuid(),
						login = uuid(),
						email = generateEmail(),
						healthcarePartyId = uuid(),
						passwordHash = uuid(),
						authenticationTokens = mapOf("test" to AuthenticationToken("token", Instant.now()))
					)
				)

				val retrievedUser = userBridge.getUser(newUser.id, false).shouldNotBeNull()
				retrievedUser.id shouldBe newUser.id
			}
		}

		"Getting a user that does not exists returns null" {
			withAuthenticatedReactorContext(credentials) {
				userBridge.getUser(uuid(), false) shouldBe null
			}
		}

		"Can retrieve users by HCP id" {
			withAuthenticatedReactorContext(credentials) {
				val newUsers = List(5) {
					createUser(
						User(
							id = uuid(),
							login = uuid(),
							email = generateEmail(),
							healthcarePartyId = uuid(),
							passwordHash = uuid(),
							authenticationTokens = mapOf("test" to AuthenticationToken("token", Instant.now()))
						)
					)
				}
				newUsers.size shouldBe 5

				val result = userBridge.listUserIdsByHcpartyId(newUsers.first().healthcarePartyId.shouldNotBeNull()).toList()
				result.size shouldBe 1
				result.first() shouldBe newUsers.first().id
			}
		}

		"Retrieving users by HCP id will return an empty list if no user with that id is found" {
			withAuthenticatedReactorContext(credentials) {
				userBridge.listUserIdsByHcpartyId(uuid()).count() shouldBe 0
			}
		}

		"The bridge cannot create a user" {
			withAuthenticatedReactorContext(credentials) {
				shouldThrow<UnsupportedOperationException> {
					userBridge.createUser(User(id = uuid()))
				}
			}
		}

		"The bridge cannot delete a user" {
			withAuthenticatedReactorContext(credentials) {
				shouldThrow<BridgeException> {
					userBridge.deleteEntity(uuid(), null)
				}
			}
		}

		"The bridge cannot disable a user" {
			withAuthenticatedReactorContext(credentials) {
				shouldThrow<UnsupportedOperationException> {
					userBridge.disableUser(uuid())
				}
			}
		}

		"The bridge cannot enable a user" {
			withAuthenticatedReactorContext(credentials) {
				shouldThrow<UnsupportedOperationException> {
					userBridge.enableUser(uuid())
				}
			}
		}

		"The bridge cannot modify a user" {
			withAuthenticatedReactorContext(credentials) {
				shouldThrow<UnsupportedOperationException> {
					userBridge.modifyUser(User(id = uuid()))
				}
			}
		}

		"The bridge cannot undelete a user" {
			withAuthenticatedReactorContext(credentials) {
				shouldThrow<BridgeException> {
					userBridge.undeleteEntity(uuid(), null)
				}
			}
		}

		"The bridge cannot set the properties for a user" {
			withAuthenticatedReactorContext(credentials) {
				shouldThrow<UnsupportedOperationException> {
					userBridge.setProperties(uuid(), emptyList())
				}
			}
		}
	}
}


