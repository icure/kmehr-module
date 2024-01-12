/*
 * Copyright (c) 2020. Taktik SA, All rights reserved.
 */

package org.taktik.icure.services.external.rest.v1.dto.samv2.embed

import java.io.Serializable

interface DataPeriodDto : Serializable {
	val from: Long?
	val to: Long?
}
