package org.taktik.icure.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ser.FilterProvider
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.icure.cardinal.sdk.utils.Serialization
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.taktik.icure.spring.encoder.FluxStringJsonEncoder
import org.taktik.icure.spring.encoder.PaginatedCollectingJackson2JsonEncoder

@Configuration
@EnableWebFlux
class WebConfig: WebFluxConfigurer {

	private val legacyJacksonFilter: FilterProvider = SimpleFilterProvider().addFilter(
		"healthElementFilter",
		SimpleBeanPropertyFilter.serializeAll()
	)

	private val legacyObjectMapper: ObjectMapper =
		ObjectMapper().registerModule(
			KotlinModule.Builder()
				.configure(KotlinFeature.NullIsSameAsDefault, true)
				.build()
		).apply {
			setSerializationInclusion(JsonInclude.Include.NON_NULL)
			setFilterProvider(legacyJacksonFilter)
		}

	@Bean
	fun legacyObjectMapper(): ObjectMapper = legacyObjectMapper

	override fun configureHttpMessageCodecs(configurer: ServerCodecConfigurer) {
		configurer.defaultCodecs().maxInMemorySize(128 * 1024 * 1024)

		configurer.customCodecs().register(FluxStringJsonEncoder())

		configurer.defaultCodecs().jackson2JsonEncoder(
			PaginatedCollectingJackson2JsonEncoder(legacyObjectMapper)
		)
		configurer.defaultCodecs().jackson2JsonDecoder(
			Jackson2JsonDecoder(
				ObjectMapper().registerModule(
					KotlinModule.Builder()
						.configure(KotlinFeature.NullIsSameAsDefault, true)
						.build()
				)
			).apply { maxInMemorySize = 128 * 1024 * 1024 }
		)
	}

	override fun addCorsMappings(registry: CorsRegistry) {
		registry
			.addMapping("/**")
			.allowCredentials(true)
			.allowedOriginPatterns("*")
			.allowedMethods("*")
			.allowedHeaders("*")
	}

	@Bean
	fun ktorHttpClient(): HttpClient = HttpClient(OkHttp) {
		install(ContentNegotiation) {
			json(json = Serialization.json)
		}
	}

}
