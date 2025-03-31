package org.taktik.icure.test

import org.taktik.icure.be.ehealth.dto.kmehr.v20170901.Utils
import org.taktik.icure.be.ehealth.dto.kmehr.v20170901.be.fgov.ehealth.standards.kmehr.schema.v1.MomentType

fun makeFuzzyLongFromMomentType(moment: MomentType): Long? {
	return if (moment.year != null) {
		Utils.makeFuzzyLongFromDateAndTime(moment.year, moment.time)
	} else if (moment.yearmonth != null) {
		Utils.makeFuzzyLongFromDateAndTime(moment.yearmonth, moment.time)
	} else {
		Utils.makeFuzzyLongFromDateAndTime(moment.date, moment.time)
	}
}