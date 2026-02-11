package org.taktik.icure.asynclogic.bridge

import com.icure.cardinal.sdk.CardinalBaseApis
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import org.springframework.stereotype.Service
import org.taktik.icure.asynclogic.InsuranceLogic
import org.taktik.icure.asynclogic.bridge.mappers.InsuranceMapper
import org.taktik.icure.db.PaginationOffset
import org.taktik.icure.entities.Insurance
import org.taktik.icure.exceptions.BridgeException
import org.taktik.icure.pagination.PaginationElement

@Service
class InsuranceLogicBridge(
	private val sdk: CardinalBaseApis,
	private val insuranceMapper: InsuranceMapper
) : GenericLogicBridge<Insurance>(), InsuranceLogic {

	override suspend fun createInsurance(insurance: Insurance): Insurance {
		throw BridgeException()
	}

	override fun getAllInsurances(paginationOffset: PaginationOffset<Nothing>): Flow<PaginationElement> {
		throw BridgeException()
	}

	override suspend fun getInsurance(insuranceId: String): Insurance? =
		sdk.insurance.getInsurance(insuranceId)?.let(insuranceMapper::map)


	override fun getInsurances(ids: Set<String>): Flow<Insurance> {
		throw BridgeException()
	}

	override fun listInsurancesByCode(code: String): Flow<Insurance> =
		if(code.isNotBlank())
			flow {
				sdk.insurance.listInsurancesByCode(code)
					.map(insuranceMapper::map)
					.forEach { emit(it) }
			}
		else emptyFlow()

	override fun listInsurancesByName(name: String): Flow<Insurance> {
		throw BridgeException()
	}

	override suspend fun modifyInsurance(insurance: Insurance): Insurance {
		throw BridgeException()
	}
}
