package org.taktik.icure.security

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
import io.ktor.client.*
import org.springframework.stereotype.Component
import org.taktik.icure.config.BridgeConfig

@OptIn(InternalIcureApi::class)
@Component
class BridgeCredentialsManagerImpl(
    bridgeConfig: BridgeConfig,
    httpClient: HttpClient,
) : BridgeCredentialsManager {

    private val provider: JwtBasedAuthProvider = AuthenticationMethod.UsingCredentials(
        UsernamePassword(
            username = bridgeConfig.kmehrUsername,
            password = bridgeConfig.kmehrPwd
        )
    ).getAuthProvider(
        authApi = RawAnonymousAuthApiImpl(
            apiUrl = bridgeConfig.iCureUrl,
            rawApiConfig = RawApiConfig(
                httpClient = httpClient,
                json = Serialization.json,
                additionalHeaders = emptyMap(),
                requestTimeout = null,
                retryConfiguration = RequestRetryConfiguration(),
            )
        ),
        cryptoService = defaultCryptoService,
        applicationId = null,
        options = BasicSdkOptions(),
        messageGatewayApi = RawMessageGatewayApi(httpClient, defaultCryptoService),
        krakenUrl = bridgeConfig.iCureUrl,
    ) as JwtBasedAuthProvider

    override suspend fun getModuleJwt() = provider.getBearerAndRefreshToken().bearer.token

}
