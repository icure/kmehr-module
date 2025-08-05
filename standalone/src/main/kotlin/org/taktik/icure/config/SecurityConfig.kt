package org.taktik.icure.config

import io.ktor.client.HttpClient
import io.ktor.client.plugins.expectSuccess
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.runBlocking
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository
import org.taktik.icure.constants.Roles
import org.taktik.icure.security.CustomAuthenticationManager
import org.taktik.icure.security.UnauthorizedEntryPoint
import org.taktik.icure.security.jwt.EncodedJWTAuth
import org.taktik.icure.security.jwt.JwtKeyUtils
import reactor.core.publisher.Mono

@Configuration
class SecurityConfig {

	@Bean
	fun authenticationManager(
		bridgeConfig: BridgeConfig,
		httpClient: HttpClient
	): CustomAuthenticationManager {
		val jwtAuthPublicKeyAsString = runBlocking {
			httpClient.get("${bridgeConfig.iCureUrl}/rest/v2/auth/publicKey/authJwt") {
				expectSuccess = true
			}.bodyAsText()
		}
		val jwtAuthPublicKey = JwtKeyUtils.decodePublicKeyFromString(jwtAuthPublicKeyAsString)
		return CustomAuthenticationManager(jwtAuthPublicKey)
	}
}

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfigAdapter(
	private val authenticationManager: CustomAuthenticationManager,
) {

	@Bean
	fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
		return http.authorizeExchange {
			it.pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				.pathMatchers("/ws/**").hasAnyAuthority(Roles.GrantedAuthority.ROLE_HCP, Roles.GrantedAuthority.ROLE_ADMINISTRATOR)
				.pathMatchers("/rest/*/be_samv2/chap/**").permitAll()
				.pathMatchers("/rest/v2/be_samv2/couchdb/notifyrotation").permitAll()
				.pathMatchers("/rest/*/ehr_module/v").permitAll()
				.pathMatchers("/actuator/**").permitAll()
				.pathMatchers("/**").hasAnyAuthority(Roles.GrantedAuthority.ROLE_HCP, Roles.GrantedAuthority.ROLE_ADMINISTRATOR)
		}.csrf {
			it.disable()
		}.addFilterAfter(
			AuthenticationWebFilter(authenticationManager).apply {
				this.setAuthenticationFailureHandler(
					ServerAuthenticationEntryPointFailureHandler(
						UnauthorizedEntryPoint(),
					),
				)
				this.setSecurityContextRepository(NoOpServerSecurityContextRepository.getInstance())

				this.setServerAuthenticationConverter { exchange ->
					// First I check for the JWT Header
					(exchange?.request?.headers?.get("Authorization")
						?.firstNotNullOfOrNull { h ->
							h?.takeIf { it.contains("Bearer") }?.replace("Bearer ", "")
						} ?: exchange?.request?.queryParams?.get("jwt")?.firstOrNull()
						)?.let {
							Mono.just(EncodedJWTAuth(token = it))
						} ?: Mono.empty()
				}
			},
			SecurityWebFiltersOrder.REACTOR_CONTEXT,
		)
			.build()
	}
}
