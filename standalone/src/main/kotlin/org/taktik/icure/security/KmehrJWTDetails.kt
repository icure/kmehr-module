package org.taktik.icure.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.taktik.icure.constants.Roles
import org.taktik.icure.entities.DataOwnerType
import org.taktik.icure.security.jwt.DATA_OWNER_ID
import org.taktik.icure.security.jwt.DATA_OWNER_TYPE
import org.taktik.icure.security.jwt.HCP_HIERARCHY
import org.taktik.icure.security.jwt.Jwt
import org.taktik.icure.security.jwt.JwtConverter
import org.taktik.icure.security.jwt.JwtDetails
import org.taktik.icure.security.jwt.USER_ID
import org.taktik.icure.utils.DynamicBitArray
import java.time.Instant

class KmehrJWTDetails(
	override val userId: String,
	override val dataOwnerId: String?,
	override val dataOwnerType: DataOwnerType?,
	override val hcpHierarchy: List<String>,
	override val expiration: Long?,
	val groupId: String,
	val isSuperAdmin: Boolean
) : AbstractUserDetails(), JwtDetails {

	override val principalPermissions = DynamicBitArray.bitVectorOfSize(0)
	override val authorities: Set<GrantedAuthority> = setOfNotNull(
		SimpleGrantedAuthority(Roles.GrantedAuthority.ROLE_HCP).takeIf { dataOwnerType == DataOwnerType.HCP },
		SimpleGrantedAuthority(Roles.GrantedAuthority.ROLE_ADMINISTRATOR).takeIf { isSuperAdmin },
	)
	companion object : JwtConverter<KmehrJWTDetails> {
		private const val GROUP_ID = "g"
		private const val IS_SUPER_ADMIN = "sa"

		override fun fromClaims(claims: Map<String, Any?>): KmehrJWTDetails =
			KmehrJWTDetails(
				userId = (claims[USER_ID] as String),
				groupId = (claims[GROUP_ID] as String),
				isSuperAdmin = ((claims[IS_SUPER_ADMIN] as Long?) ?: 0L) == 1L,
				dataOwnerId = claims[DATA_OWNER_ID] as String?,
				dataOwnerType = (claims[DATA_OWNER_TYPE] as String?)?.let { DataOwnerType.valueOfOrNullCaseInsensitive(it) },
				hcpHierarchy = ((claims[HCP_HIERARCHY] ?: emptyList<Any>()) as Collection<Any?>).mapNotNull { it as? String },
				expiration = (claims[Jwt.StandardClaims.EXPIRES_AT] as Instant).toEpochMilli(),
			)
	}

	override fun toClaimsOmittingExpiration(): Map<String, Any?> = mapOf(
		USER_ID to userId,
		GROUP_ID to groupId,
		DATA_OWNER_ID to dataOwnerId,
		DATA_OWNER_TYPE to dataOwnerType,
		HCP_HIERARCHY to hcpHierarchy,
		IS_SUPER_ADMIN to if(isSuperAdmin) 1 else 0
	)

}
