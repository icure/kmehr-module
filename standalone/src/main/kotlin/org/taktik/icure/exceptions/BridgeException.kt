package org.taktik.icure.exceptions

class BridgeException : IllegalStateException(getCallerDetails()) {

	companion object {
		private fun getCallerDetails(): String {
			val stackTrace = Thread.currentThread().stackTrace
			val element = stackTrace.firstOrNull {
				it.className.startsWith("org.taktik.icure.asynclogic.bridge")
			}
			return if (element != null) {
				"Bridge method not implemented: ${element.className}.${element.methodName}"
			} else {
				"Bridge method not implemented"
			}
		}
	}
}
