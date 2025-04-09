package org.taktik.icure.asynclogic.datastore.impl

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.taktik.icure.asynclogic.datastore.DatastoreInstanceProvider
import org.taktik.icure.asynclogic.datastore.IDatastoreInformation
import org.taktik.icure.properties.CouchDbProperties
import java.net.URI

@Service
@Profile("sam")
class SAMDatastoreInstanceProvider(
    private val couchDbProperties: CouchDbProperties
): DatastoreInstanceProvider {
    override suspend fun getInstanceAndGroup(): IDatastoreInformation =
        SAMDatastoreInformation(URI(couchDbProperties.url))
}
