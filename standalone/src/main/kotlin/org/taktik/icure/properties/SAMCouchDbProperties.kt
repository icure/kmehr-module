/*
 * Copyright (c) 2020. Taktik SA, All rights reserved.
 */

package org.taktik.icure.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("sam")
@ConfigurationProperties("icure.couchdb")
class StandaloneCouchDbProperties(
    override var url: String = "http://127.0.0.1:5984",
    override var altUrls: String = "",
    override var preferredUrl: String? = null,
    override var username: String? = null,
    override var password: String? = null,
    override var maxConnections: Int? = null,
    override var maxPendingAcquire: Int? = 10_000,
    override var maxIdleTimeMs: Long? = null
) : CouchDbProperties
