package org.taktik.icure.asynclogic.bridge.auth

import com.icure.cardinal.sdk.auth.JwtBearer
import com.icure.cardinal.sdk.auth.services.ProxyAuthProvider
import com.icure.utils.InternalIcureApi


@OptIn(InternalIcureApi::class)
class KmehrAuthProvider (
	private val token: String
): ProxyAuthProvider() {
	override suspend fun getToken(): JwtBearer = JwtBearer(token)
}