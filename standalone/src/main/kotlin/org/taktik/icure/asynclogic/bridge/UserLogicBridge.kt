package org.taktik.icure.asynclogic.bridge

import com.icure.cardinal.sdk.CardinalBaseApis
import com.icure.utils.InternalIcureApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import org.springframework.stereotype.Service
import org.taktik.couchdb.ViewQueryResultEvent
import org.taktik.icure.asynclogic.UserLogic
import org.taktik.icure.asynclogic.bridge.mappers.UserMapper
import org.taktik.icure.db.PaginationOffset
import org.taktik.icure.domain.filter.chain.FilterChain
import org.taktik.icure.entities.User
import org.taktik.icure.entities.base.PropertyStub
import org.taktik.icure.exceptions.BridgeException
import org.taktik.icure.pagination.PaginationElement

@Service
class UserLogicBridge(
    private val sdk: CardinalBaseApis,
    private val userMapper: UserMapper
) : GenericLogicBridge<User>(), UserLogic {

    override suspend fun createOrUpdateToken(
        userIdentifier: String,
        key: String,
        tokenValidity: Long,
        token: String?,
        useShortToken: Boolean
    ): String {
        throw BridgeException()
    }

    override suspend fun createUser(user: User): User? {
        throw UnsupportedOperationException("The Kmehr module cannot create users")
    }

    override suspend fun disableUser(userId: String): User? {
        throw UnsupportedOperationException("The Kmehr module cannot disable users")
    }

    override suspend fun enableUser(userId: String): User? {
        throw UnsupportedOperationException("The Kmehr module cannot enable users")
    }

    override fun filterUsers(
        paginationOffset: PaginationOffset<Nothing>,
        filter: FilterChain<User>
    ): Flow<ViewQueryResultEvent> {
        throw BridgeException()
    }

    override fun findByNameEmailPhone(
        searchString: String,
        pagination: PaginationOffset<String>
    ): Flow<ViewQueryResultEvent> {
        throw BridgeException()
    }

    override fun findByPatientId(patientId: String): Flow<String> {
        throw BridgeException()
    }

    override suspend fun getUser(id: String, includeMetadataFromGlobalUser: Boolean): User? =
        sdk.user.getUser(id)?.let(userMapper::map)

    override suspend fun getUserByEmail(email: String): User? {
        throw BridgeException()
    }

    override suspend fun getUserByGenericIdentifier(genericIdentifier: String): User? {
        throw BridgeException()
    }

    override suspend fun getUserByLogin(login: String): User? {
        throw BridgeException()
    }

    override suspend fun getUserByPhone(phone: String): User? {
        throw BridgeException()
    }

    override fun getUsers(ids: List<String>): Flow<User> {
        throw BridgeException()
    }

    override fun getUsersByLogin(login: String): Flow<User> {
        throw BridgeException()
    }

    override fun listUserIdsByHcpartyId(hcpartyId: String): Flow<String> = flow {
        emitAll(sdk.user.findByHcpartyId(hcpartyId).asFlow())
    }

    override fun listUsers(
        paginationOffset: PaginationOffset<String>,
        skipPatients: Boolean
    ): Flow<PaginationElement> {
        throw BridgeException()
    }

    override suspend fun modifyUser(modifiedUser: User): User? {
        throw UnsupportedOperationException("The Kmehr module cannot modify users")
    }

    override suspend fun setProperties(userId: String, properties: List<PropertyStub>): User? {
        throw UnsupportedOperationException("The Kmehr module cannot modify users")
    }
}
