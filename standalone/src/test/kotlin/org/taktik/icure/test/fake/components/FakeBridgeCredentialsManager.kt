package org.taktik.icure.test.fake.components

import com.icure.cardinal.sdk.api.raw.RawApiConfig
import com.icure.kryptom.crypto.defaultCryptoService
import com.icure.cardinal.sdk.api.raw.RawMessageGatewayApi
import com.icure.cardinal.sdk.api.raw.impl.RawAnonymousAuthApiImpl
import com.icure.cardinal.sdk.auth.UsernamePassword
import com.icure.cardinal.sdk.auth.services.JwtBasedAuthProvider
import com.icure.cardinal.sdk.options.AuthenticationMethod
import com.icure.cardinal.sdk.options.BasicSdkOptions
import com.icure.cardinal.sdk.options.RequestRetryConfiguration
import com.icure.cardinal.sdk.options.getAuthProvider
import com.icure.utils.InternalIcureApi
import com.icure.cardinal.sdk.utils.Serialization
import org.taktik.icure.config.BridgeConfig
import org.taktik.icure.security.BridgeCredentialsManager
import org.taktik.icure.test.testHttpClient

@OptIn(InternalIcureApi::class)
class FakeBridgeCredentialsManager(
    bridgeConfig: BridgeConfig,
    kmehrUsername: String,
    kmehrPwd: String
) : BridgeCredentialsManager {

    private val provider: JwtBasedAuthProvider = AuthenticationMethod.UsingCredentials(
        UsernamePassword(
            username = kmehrUsername,
            password = kmehrPwd
        )
    ).getAuthProvider(
        authApi = RawAnonymousAuthApiImpl(
            apiUrl = bridgeConfig.iCureUrl,
            rawApiConfig = RawApiConfig(
                httpClient = testHttpClient,
                json = Serialization.json,
                additionalHeaders = emptyMap(),
                requestTimeout = null,
                retryConfiguration = RequestRetryConfiguration(),
            )
        ),
        cryptoService = defaultCryptoService,
        applicationId = null,
        options = BasicSdkOptions(),
        messageGatewayApi = RawMessageGatewayApi(testHttpClient, defaultCryptoService),
		krakenUrl = bridgeConfig.iCureUrl
    ) as JwtBasedAuthProvider

    override suspend fun getModuleJwt() = provider.getBearerAndRefreshToken().bearer.token
}