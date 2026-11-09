/*
 * Copyright (c) 2020. Taktik SA, All rights reserved.
 */

package org.taktik.icure.asyncdao.impl

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import org.taktik.icure.asyncdao.samv2.LocalEntityCache
import org.taktik.icure.entities.base.StoredDocument
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.toJavaDuration

class CaffeineLocalEntityCache<T : StoredDocument>(
	maximumSize: Long = DEFAULT_MAXIMUM_SIZE,
	expireAfterWrite: Duration = 10.minutes,
) : LocalEntityCache<T> {

	companion object {
		const val DEFAULT_MAXIMUM_SIZE = 10_000L
	}

	private val cache: Cache<String, T> = Caffeine.newBuilder()
		.maximumSize(maximumSize)
		.expireAfterWrite(expireAfterWrite.toJavaDuration())
		.build()

	override fun getIfPresent(id: String): T? = cache.getIfPresent(id)

	override fun getAllPresent(ids: Collection<String>): Map<String, T> = cache.getAllPresent(ids)

	override fun put(entity: T) {
		cache.put(entity.id, entity)
	}

	override fun invalidate(id: String) {
		cache.invalidate(id)
	}
}
