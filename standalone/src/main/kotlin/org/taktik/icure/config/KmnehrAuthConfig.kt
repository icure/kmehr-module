package org.taktik.icure.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.taktik.icure.properties.AuthProperties
import kotlin.time.Duration.Companion.milliseconds

@Configuration
class KmehrAuthProperties(
	override val jwt: KmehrJwtProperties
): AuthProperties {
	override val validationSkewSeconds: Long = 0
}

@Configuration
class KmehrJwtProperties(
	@Value("\${icure.auth.jwt.expirationMillis}") defaultExpirationTimeMillis: Long
): AuthProperties.Jwt {

	override val refreshExpirationSeconds: Long = 0
	override val expirationSeconds: Long = defaultExpirationTimeMillis.milliseconds.inWholeSeconds
}