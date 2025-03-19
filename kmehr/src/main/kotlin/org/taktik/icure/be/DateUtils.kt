package org.taktik.icure.be

import org.taktik.icure.utils.FuzzyValues
import java.time.LocalDateTime

/**
 * Checks if the receiver fuzzy date is before now. If the receiver is null or equal to zero, or it is not a valid
 * date, false will be returned.
 */
fun Long?.isBeforeNow(): Boolean = this?.takeIf { it > 0 }
	?.let { FuzzyValues.getDateTime(it) }
	?.let { it < LocalDateTime.now() } ?: false