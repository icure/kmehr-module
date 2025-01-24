package org.taktik.icure.asynclogic.samv2.impl

class SamV2UpdateTaskLog(
	val status: Status,
	val time: Long,
	val msg: String
) {

	enum class Status { Started, Running, Error, Completed, Missing }

}