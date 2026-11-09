/*
 * Copyright (c) 2020. Taktik SA, All rights reserved.
 */

package org.taktik.icure.asyncdao.samv2

import org.taktik.icure.entities.base.StoredDocument

/**
 * An in-memory, process-local cache of entities indexed by their id.
 *
 * The cache is not aware of the datastore the entities come from: it must only be used by DAOs that always
 * point to the same database.
 */
interface LocalEntityCache<T : StoredDocument> {
	/**
	 * @return the entity with the given [id] if it is in the cache, null otherwise.
	 */
	fun getIfPresent(id: String): T?

	/**
	 * @return a map with all the entities among [ids] that are in the cache, indexed by id.
	 * The ids that are not in the cache are absent from the map.
	 */
	fun getAllPresent(ids: Collection<String>): Map<String, T>

	/**
	 * Adds [entity] to the cache, replacing any previous entity with the same id.
	 */
	fun put(entity: T)

	/**
	 * Removes the entity with the given [id] from the cache, if present.
	 */
	fun invalidate(id: String)
}
