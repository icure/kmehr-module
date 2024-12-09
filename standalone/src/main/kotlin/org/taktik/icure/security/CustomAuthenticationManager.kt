package org.taktik.icure.security

import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.mono
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.taktik.icure.constants.Roles
import org.taktik.icure.entities.DataOwnerType
import org.taktik.icure.security.jwt.EncodedJWTAuth
import org.taktik.icure.security.jwt.JwtDecoder
import reactor.core.publisher.Mono
import java.security.PublicKey

class CustomAuthenticationManager(
	private val jwtAuthPublicKey: PublicKey,
	private val defaultExpirationTimeMillis: Long
): ReactiveAuthenticationManager {

	override fun authenticate(authentication: Authentication?): Mono<Authentication> = mono {
		authentication
			.takeIf { it is EncodedJWTAuth }
			?.let {
				try {
					JwtDecoder.decodeAndGetClaims(
						jwt = (it as EncodedJWTAuth).token,
						ignoreExpiration = false,
						publicKey = jwtAuthPublicKey
					).let { claims ->
						JwtDecoder.jwtDetailsFromClaims(KmehrJWTDetails, claims, defaultExpirationTimeMillis)
					}
				} catch (_: Exception) { null }
			}?.let { jwt ->
				EncodedJWTAuth(
					token = (authentication as EncodedJWTAuth).token,
					claims = jwt,
					authorities = setOfNotNull(
						SimpleGrantedAuthority(Roles.GrantedAuthority.ROLE_HCP).takeIf { jwt.dataOwnerType == DataOwnerType.HCP },
						SimpleGrantedAuthority(Roles.GrantedAuthority.ROLE_ADMINISTRATOR).takeIf { jwt.isSuperAdmin },
					).toMutableSet()
				)
			}?.also {
				loadSecurityContext()?.map { ctx ->
					ctx.authentication = it
				}?.awaitFirstOrNull()
			} ?: throw BadCredentialsException("Invalid username or password")
	}
}
