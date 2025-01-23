package org.taktik.icure.entities.samv2.updates

enum class UpdateType(val urlComponent: String) {
	Diff("diff"),
	Snapshot("snapshot")
}