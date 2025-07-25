package org.taktik.icure.config

import com.icure.cardinal.sdk.CardinalBaseApis
import com.icure.cardinal.sdk.CardinalUnboundBaseSdk
import com.icure.cardinal.sdk.api.raw.RawApiConfig
import com.icure.cardinal.sdk.api.raw.RawCodeApi
import com.icure.cardinal.sdk.api.raw.RawContactApi
import com.icure.cardinal.sdk.api.raw.RawDocumentApi
import com.icure.cardinal.sdk.api.raw.RawEntityReferenceApi
import com.icure.cardinal.sdk.api.raw.RawFormApi
import com.icure.cardinal.sdk.api.raw.RawHealthElementApi
import com.icure.cardinal.sdk.api.raw.RawPatientApi
import com.icure.cardinal.sdk.api.raw.impl.RawCodeApiImpl
import com.icure.cardinal.sdk.api.raw.impl.RawContactApiImpl
import com.icure.cardinal.sdk.api.raw.impl.RawDocumentApiImpl
import com.icure.cardinal.sdk.api.raw.impl.RawEntityReferenceApiImpl
import com.icure.cardinal.sdk.api.raw.impl.RawFormApiImpl
import com.icure.cardinal.sdk.api.raw.impl.RawHealthElementApiImpl
import com.icure.cardinal.sdk.api.raw.impl.RawPatientApiImpl
import com.icure.cardinal.sdk.api.raw.wrap
import com.icure.cardinal.sdk.auth.JwtBearer
import com.icure.cardinal.sdk.auth.JwtBearerAndRefresh
import com.icure.cardinal.sdk.auth.services.JwtBasedAuthProvider
import com.icure.cardinal.sdk.auth.services.TokenBasedAuthService
import com.icure.cardinal.sdk.crypto.impl.NoAccessControlKeysHeadersProvider
import com.icure.cardinal.sdk.model.FormTemplate
import com.icure.cardinal.sdk.model.embed.AuthenticationClass
import com.icure.cardinal.sdk.options.AuthenticationMethod
import com.icure.cardinal.sdk.options.RequestRetryConfiguration
import com.icure.cardinal.sdk.options.UnboundBasicSdkOptions
import com.icure.cardinal.sdk.utils.RequestStatusException
import com.icure.cardinal.sdk.utils.Serialization
import com.icure.utils.InternalIcureApi
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import io.ktor.http.takeFrom
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.taktik.icure.asynclogic.impl.BridgeAsyncSessionLogic

@Configuration
class CardinalSdkConfig(
	private val bridgeConfig: BridgeConfig,
	private val bridgeHttpClient: HttpClient
) {

	@OptIn(InternalIcureApi::class)
	private val tokenBasedAuthService = object : TokenBasedAuthService<JwtBearer> {
		override suspend fun invalidateCurrentAuthentication(
			error: RequestStatusException,
			requiredAuthClass: AuthenticationClass?
		) {
			throw UnsupportedOperationException("Invalidating current authentication")
		}

		private suspend fun getJwtFromContextOrInitialJwt() =
			BridgeAsyncSessionLogic.getCurrentAuthentication()?.token
				?: throw IllegalStateException("No current authentication in the context")

		override suspend fun setAuthenticationInRequest(
			builder: HttpRequestBuilder,
			authenticationClass: AuthenticationClass?
		) {
			builder.bearerAuth(getJwtFromContextOrInitialJwt())
		}

		override suspend fun getToken(): JwtBearer = JwtBearer(getJwtFromContextOrInitialJwt())
	}

	@OptIn(InternalIcureApi::class)
	private val sessionAuthProvider = object : JwtBasedAuthProvider {
		override fun getAuthService() = tokenBasedAuthService

		override suspend fun getBearerAndRefreshToken(): JwtBearerAndRefresh {
			throw UnsupportedOperationException("Cannot get bearer and refresh token from unbound api")
		}

		override suspend fun switchGroup(newGroupId: String): com.icure.cardinal.sdk.auth.services.AuthProvider {
			throw UnsupportedOperationException("Cannot switch group on unbound api")
		}
	}

	@OptIn(InternalIcureApi::class)
	@Bean
	fun cardinalUnboundSdk(): CardinalBaseApis = CardinalUnboundBaseSdk.initialize(
		baseUrl = bridgeConfig.iCureUrl,
		authenticationMethod = AuthenticationMethod.UsingAuthProvider(sessionAuthProvider),
		options = UnboundBasicSdkOptions()
	)

	@OptIn(InternalIcureApi::class)
	@Bean
	fun rawCodeApi(): RawCodeApi = RawCodeApiImpl(
		apiUrl = bridgeConfig.iCureUrl,
		authProvider = sessionAuthProvider,
		rawApiConfig = RawApiConfig(
			httpClient = bridgeHttpClient,
			json = Serialization.json,
			additionalHeaders = emptyMap(),
			requestTimeout = null,
			retryConfiguration = RequestRetryConfiguration(),
		)
	)

	@OptIn(InternalIcureApi::class)
	@Bean
	fun rawContactApi(): RawContactApi = RawContactApiImpl(
		apiUrl = bridgeConfig.iCureUrl,
		authProvider = sessionAuthProvider,
		accessControlKeysHeadersProvider = NoAccessControlKeysHeadersProvider,
		rawApiConfig = RawApiConfig(
			httpClient = bridgeHttpClient,
			json = Serialization.json,
			additionalHeaders = emptyMap(),
			requestTimeout = null,
			retryConfiguration = RequestRetryConfiguration(),
		)
	)

	@OptIn(InternalIcureApi::class)
	@Bean
	fun rawDocumentApi(): RawDocumentApi = RawDocumentApiImpl(
		apiUrl = bridgeConfig.iCureUrl,
		authProvider = sessionAuthProvider,
		accessControlKeysHeadersProvider = NoAccessControlKeysHeadersProvider,
		rawApiConfig = RawApiConfig(
			httpClient = bridgeHttpClient,
			json = Serialization.json,
			additionalHeaders = emptyMap(),
			requestTimeout = null,
			retryConfiguration = RequestRetryConfiguration(),
		)
	)

	@OptIn(InternalIcureApi::class)
	@Bean
	fun rawEntityReferenceApi(): RawEntityReferenceApi = RawEntityReferenceApiImpl(
		apiUrl = bridgeConfig.iCureUrl,
		authProvider = sessionAuthProvider,
		rawApiConfig = RawApiConfig(
			httpClient = bridgeHttpClient,
			json = Serialization.json,
			additionalHeaders = emptyMap(),
			requestTimeout = null,
			retryConfiguration = RequestRetryConfiguration(),
		)
	)

	@OptIn(InternalIcureApi::class)
	@Bean
	fun rawFormApi(): RawFormApi = RawFormApiImpl(
		apiUrl = bridgeConfig.iCureUrl,
		authProvider = sessionAuthProvider,
		accessControlKeysHeadersProvider = NoAccessControlKeysHeadersProvider,
		rawApiConfig = RawApiConfig(
			httpClient = bridgeHttpClient,
			json = Serialization.json,
			additionalHeaders = emptyMap(),
			requestTimeout = null,
			retryConfiguration = RequestRetryConfiguration(),
		)
	)

	@OptIn(InternalIcureApi::class)
	@Bean
	fun rawHealthElementApi(): RawHealthElementApi = RawHealthElementApiImpl(
		apiUrl = bridgeConfig.iCureUrl,
		authProvider = sessionAuthProvider,
		accessControlKeysHeadersProvider = NoAccessControlKeysHeadersProvider,
		rawApiConfig = RawApiConfig(
			httpClient = bridgeHttpClient,
			json = Serialization.json,
			additionalHeaders = emptyMap(),
			requestTimeout = null,
			retryConfiguration = RequestRetryConfiguration(),
		)
	)

	@OptIn(InternalIcureApi::class)
	@Bean
	fun rawPatientApi(): RawPatientApi = RawPatientApiImpl(
		apiUrl = bridgeConfig.iCureUrl,
		authProvider = sessionAuthProvider,
		accessControlKeysHeadersProvider = NoAccessControlKeysHeadersProvider,
		rawApiConfig = RawApiConfig(
			httpClient = bridgeHttpClient,
			json = Serialization.json,
			additionalHeaders = emptyMap(),
			requestTimeout = null,
			retryConfiguration = RequestRetryConfiguration(),
		)
	)

	interface FormTemplateLegacyApi {
		suspend fun getFormTemplatesByGuid(formTemplateGuid: String, specialityCode: String): List<FormTemplate>
	}

	@OptIn(InternalIcureApi::class)
	@Bean
	fun formTemplateLegacyApi(): FormTemplateLegacyApi = object : FormTemplateLegacyApi {

		override suspend fun getFormTemplatesByGuid(
			formTemplateGuid: String,
			specialityCode: String
		): List<FormTemplate> =
			bridgeHttpClient.get {
				url {
					takeFrom(bridgeConfig.iCureUrl)
					appendPathSegments("rest", "v2", "form", "template", specialityCode, "guid", formTemplateGuid)
				}
				bearerAuth(
					BridgeAsyncSessionLogic.getCurrentAuthentication()?.token
						?: throw IllegalStateException("No current authentication in the context")
				)
			}.wrap<List<FormTemplate>>().successBody()


	}
}