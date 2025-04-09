package org.taktik.icure.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("sam")
@ConfigurationProperties("icure.couchdb.sam")
class SAMCouchDbProperties(
    var suffix: String = "",
    var nextVersionSuffix: String? = null,
)
