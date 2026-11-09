/*
 * Copyright (c) 2020. Taktik SA, All rights reserved.
 */

package org.taktik.icure.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.taktik.icure.asyncdao.impl.CaffeineLocalEntityCache
import org.taktik.icure.asyncdao.samv2.LocalEntityCache
import org.taktik.icure.entities.samv2.Amp

@Profile("sam")
@Configuration
class LocalEntityCacheConfig {
	@Bean
	fun ampLocalEntityCache(objectMapper: ObjectMapper): LocalEntityCache<Amp> =
		CaffeineLocalEntityCache(objectMapper)
}
