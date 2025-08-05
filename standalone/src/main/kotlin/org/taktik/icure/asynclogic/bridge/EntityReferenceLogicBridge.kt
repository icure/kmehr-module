package org.taktik.icure.asynclogic.bridge

import com.icure.cardinal.sdk.api.raw.RawEntityReferenceApi
import com.icure.cardinal.sdk.api.raw.successBodyOrNull404
import com.icure.utils.InternalIcureApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import org.springframework.stereotype.Service
import org.taktik.icure.asynclogic.EntityReferenceLogic
import org.taktik.icure.asynclogic.bridge.mappers.EntityReferenceMapper
import org.taktik.icure.entities.EntityReference

@OptIn(InternalIcureApi::class)
@Service
class EntityReferenceLogicBridge(
    private val rawEntityReferenceApi: RawEntityReferenceApi,
    private val entityReferenceMapper: EntityReferenceMapper
) : GenericLogicBridge<EntityReference>(), EntityReferenceLogic {

    override suspend fun getLatest(prefix: String): EntityReference? =
        rawEntityReferenceApi.getLatest(prefix).successBodyOrNull404()?.let(entityReferenceMapper::map)

    override fun createEntities(entities: Collection<EntityReference>): Flow<EntityReference> = flow {
        emitAll(
            rawEntityReferenceApi.let { api ->
                entities
                    .map { api.createEntityReference(entityReferenceMapper.map(it)).successBody() }
                    .map(entityReferenceMapper::map)
                    .asFlow()
            }
        )
    }

}
