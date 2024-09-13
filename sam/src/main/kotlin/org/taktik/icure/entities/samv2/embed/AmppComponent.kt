/*
 * Copyright (c) 2020. Taktik SA, All rights reserved.
 */

package org.taktik.icure.entities.samv2.embed

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.taktik.icure.utils.JacksonSamTextLenientDeserializer

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class AmppComponent(
	override val from: Long? = null,
	override val to: Long? = null,
	val contentType: ContentType? = null,
	val contentMultiplier: Int? = null,
	@JsonDeserialize(using = JacksonSamTextLenientDeserializer::class)
	val packSpecification: SamText? = null,
	val deviceType: DeviceType? = null,
	val packagingType: PackagingType? = null
) : DataPeriod
