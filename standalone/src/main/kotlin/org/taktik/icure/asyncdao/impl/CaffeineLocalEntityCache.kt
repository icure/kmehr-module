/*
 * Copyright (c) 2020. Taktik SA, All rights reserved.
 */

package org.taktik.icure.asyncdao.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import org.taktik.icure.asyncdao.samv2.LocalEntityCache
import org.taktik.icure.entities.base.StoredDocument
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.toJavaDuration

/**
 * [LocalEntityCache] backed by a Caffeine cache.
 *
 * The cache is bounded by weight: each entity weighs the size in bytes of its JSON serialization, so the
 * [maximumSizeBytes] is an estimate of the data held by the cache, not of its exact heap footprint.
 */
class CaffeineLocalEntityCache<T : StoredDocument>(
	private val objectMapper: ObjectMapper,
	maximumSizeBytes: Long = DEFAULT_MAXIMUM_SIZE_BYTES,
	expireAfterWrite: Duration = 10.minutes,
) : LocalEntityCache<T> {

	companion object {
		const val DEFAULT_MAXIMUM_SIZE_BYTES = 500L * 1024 * 1024
	}

	private val cache: Cache<String, T> = Caffeine.newBuilder()
		.maximumWeight(maximumSizeBytes)
		.weigher { _: String, entity: T -> weightOf(entity) }
		.expireAfterWrite(expireAfterWrite.toJavaDuration())
		.build()

	private fun weightOf(entity: T): Int =
		runCatching { objectMapper.writeValueAsBytes(entity).size }.getOrDefault(1)

	override fun getIfPresent(id: String): T? = cache.getIfPresent(id)

	override fun getAllPresent(ids: Collection<String>): Map<String, T> = cache.getAllPresent(ids)

	override fun put(entity: T) {
		cache.put(entity.id, entity)
	}

	override fun invalidate(id: String) {
		cache.invalidate(id)
	}
}
