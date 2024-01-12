/*
 * Copyright (c) 2020. Taktik SA, All rights reserved.
 */

package org.taktik.icure.services.external.rest.v1.dto.samv2.stub

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import org.taktik.icure.services.external.rest.v1.dto.base.IdentifiableDto
import org.taktik.icure.services.external.rest.v1.dto.samv2.embed.SamTextDto

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class VmpStubDto(
	override val id: String,
	val code: String? = null,
	val vmpGroup: VmpGroupStubDto? = null,
	val name: SamTextDto? = null
) : IdentifiableDto<String>
