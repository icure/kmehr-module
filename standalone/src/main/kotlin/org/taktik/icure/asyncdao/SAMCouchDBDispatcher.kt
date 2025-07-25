package org.taktik.icure.asyncdao

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.benmanes.caffeine.cache.Caffeine
import io.icure.asyncjacksonhttpclient.net.web.WebClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.springframework.cache.caffeine.CaffeineCache
import org.taktik.couchdb.Client
import org.taktik.couchdb.ClientImpl
import org.taktik.icure.asynccache.AsyncSafeCache
import org.taktik.icure.datastore.IDatastoreInformation
import org.taktik.icure.asynclogic.datastore.impl.SAMDatastoreInformation
import org.taktik.icure.security.CouchDbCredentialsProvider
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalCoroutinesApi::class)
class SAMCouchDBDispatcher (
    private val httpClient: WebClient,
    private val objectMapper: ObjectMapper,
    private val prefix: String,
    private val dbFamily: String,
    private val couchDbCredentialsProvider: CouchDbCredentialsProvider,
    private val createdReplicasIfNotExists: Int? = null
): CouchDbDispatcher {

    private val connectors = AsyncSafeCache<CouchDbConnectorReference, Client>(
        CaffeineCache(
            "Connectors",
            Caffeine.newBuilder()
                .maximumSize(10000)
                .expireAfterAccess(240, TimeUnit.MINUTES)
                .build()
        )
    )

    private suspend fun attemptConnection(datastoreInformation: IDatastoreInformation, trials: Int): Client = try {
        connectors.get(
            CouchDbConnectorReference(datastoreInformation),
            object : AsyncSafeCache.AsyncValueProvider<CouchDbConnectorReference, Client> {
                override suspend fun getValue(key: CouchDbConnectorReference): Client {
                    return ClientImpl(
                        httpClient = httpClient,
                        couchDBUri = (datastoreInformation as SAMDatastoreInformation).dbInstanceUrl,
                        dbName = "$prefix-__-$dbFamily",
                        objectMapper = objectMapper,
                        credentialsProvider = couchDbCredentialsProvider
                    ).also {
                        if (createdReplicasIfNotExists != null) {
                            if (!it.exists()) {
                                it.create(3, createdReplicasIfNotExists)
                            }
                        }
                    }
                }
            }
        ) ?: if (trials > 1) attemptConnection(datastoreInformation, trials - 1) else throw IllegalStateException("Cannot instantiate client")
    } catch (e: Exception) {
        if (trials > 1) attemptConnection(datastoreInformation, trials - 1) else throw e
    }
    override suspend fun getClient(datastoreInformation: IDatastoreInformation, retry: Int): Client = attemptConnection(datastoreInformation, retry)

    private data class CouchDbConnectorReference(val datastoreInformation: IDatastoreInformation)

}
