/*
 * Copyright (c) 2020. Taktik SA, All rights reserved.
 */

package org.taktik.icure.entities.samv2.embed

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class Commercialization(
	override val from: Long? = null,
	override val to: Long? = null,
	val reason: SamText? = null,
	val endOfComercialization: SamText? = null,
	val impact: SamText? = null,
	val additionalInformation: SamText? = null
) : DataPeriod
