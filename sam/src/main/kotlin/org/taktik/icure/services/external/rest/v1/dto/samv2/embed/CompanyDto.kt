/*
 * Copyright (c) 2020. Taktik SA, All rights reserved.
 */

package org.taktik.icure.services.external.rest.v1.dto.samv2.embed

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class CompanyDto(
	override val from: Long? = null,
	override val to: Long? = null,
	val authorisationNr: String? = null,
	val vatNr: Map<String, String>? = null,
	val europeanNr: String? = null,
	val denomination: String? = null,
	val legalForm: String? = null,
	val building: String? = null,
	val streetName: String? = null,
	val streetNum: String? = null,
	val postbox: String? = null,
	val postcode: String? = null,
	val city: String? = null,
	val countryCode: String? = null,
	val phone: String? = null,
	val language: String? = null,
	val website: String? = null
) : DataPeriodDto
