/*
 * Copyright (c) 2020. Taktik SA, All rights reserved.
 */

package org.taktik.icure.asyncdao.samv2.impl

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.github.benmanes.caffeine.cache.Caffeine
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.future.await
import kotlinx.coroutines.future.future
import org.taktik.couchdb.Client
import org.taktik.couchdb.ViewQueryResultEvent
import org.taktik.couchdb.ViewRowNoDoc
import org.taktik.couchdb.ViewRowWithDoc
import org.taktik.couchdb.ViewRowWithMissingDoc
import org.taktik.couchdb.annotation.View
import org.taktik.couchdb.dao.DesignDocumentProvider
import org.taktik.couchdb.entity.ComplexKey
import org.taktik.couchdb.entity.ViewQuery
import org.taktik.couchdb.queryView
import org.taktik.couchdb.queryViewIncludeDocs
import org.taktik.icure.asyncdao.CouchDbDispatcher
import org.taktik.icure.asyncdao.impl.InternalDAOImpl
import org.taktik.icure.asyncdao.samv2.AmpDAO
import org.taktik.icure.asyncdao.samv2.LocalEntityCache
import org.taktik.icure.datastore.DatastoreInstanceProvider
import org.taktik.icure.datastore.IDatastoreInformation
import org.taktik.icure.db.PaginationOffset
import org.taktik.icure.db.sanitizeForSorting
import org.taktik.icure.db.sanitizeString
import org.taktik.icure.entities.samv2.Amp
import org.taktik.icure.entities.samv2.SamVersion
import org.taktik.icure.exceptions.TooManyResultsException
import org.taktik.icure.utils.makeFromTo
import org.taktik.icure.utils.toInputStream
import java.util.zip.GZIPInputStream
import kotlin.time.Duration.Companion.minutes
import kotlin.time.toJavaDuration

@View(name = "all", map = "function(doc) { if (doc.java_type == 'org.taktik.icure.entities.samv2.Amp' && !doc.deleted) emit( null, doc._id )}")
class AmpDAOImpl(
	couchDbDispatcher: CouchDbDispatcher,
	datastoreInstanceProvider: DatastoreInstanceProvider,
	designDocumentProvider: DesignDocumentProvider,
	/**
	 * Optional cache of the [Amp]s by id. When provided, the view queries are executed without docs and the
	 * [Amp]s are taken from the cache, falling back to the database for the cache misses.
	 * The cache is not aware of the datastore: it must only be shared by DAOs pointing to the same database.
	 */
	private val localEntityCache: LocalEntityCache<Amp>? = null,
) : InternalDAOImpl<Amp>(
	entityClass = Amp::class.java,
	couchDbDispatcher = couchDbDispatcher,
	datastoreInstanceProvider = datastoreInstanceProvider,
	designDocumentProvider = designDocumentProvider
), AmpDAO {

	companion object {
		const val ampPaginationLimit = 101
	}

	private val amppCache = Caffeine.newBuilder()
		.maximumWeight(1_000_000)
		.weigher { _: String, value: List<Pair<String, String>> -> value.size }
		.expireAfterAccess(2.minutes.toJavaDuration())
		.buildAsync<String, List<Pair<String, String>>>()

	private val ampCache = Caffeine.newBuilder()
		.maximumWeight(1_000_000)
		.weigher { _: String, value: List<String> -> value.size }
		.expireAfterAccess(2.minutes.toJavaDuration())
		.buildAsync<String, List<String>>()

	@View(name = "by_dmppcode", map = "classpath:js/amp/By_dmppcode.js")
	override fun findAmpsByDmppCode(datastoreInformation: IDatastoreInformation, dmppCode: String) = flow {
		val client = couchDbDispatcher.getClient(datastoreInformation)
		emitAll(
			client.queryAmpView(
				createQuery("by_dmppcode")
					.startKey(dmppCode)
					.endKey(dmppCode)
					.includeDocs(true)
					.limit(ampPaginationLimit),
				String::class.java, String::class.java
			)
		)
	}

	@View(name = "by_ampcode", map = "classpath:js/amp/By_ampcode.js")
	override fun findAmpsByAmpCode(datastoreInformation: IDatastoreInformation, ampCode: String) = flow {
		val client = couchDbDispatcher.getClient(datastoreInformation)
		emitAll(
			client.queryAmpView(
				createQuery("by_ampcode")
					.startKey(ampCode)
					.endKey(ampCode)
					.includeDocs(true)
					.limit(ampPaginationLimit),
				String::class.java, String::class.java
			)
		)
	}

	@View(name = "by_groupcode", map = "classpath:js/amp/By_groupcode.js")
	override fun findAmpsByVmpGroupCode(datastoreInformation: IDatastoreInformation, vmpgCode: String, paginationOffset: PaginationOffset<String>) = flow {
		val client = couchDbDispatcher.getClient(datastoreInformation)
		emitAll(client.queryAmpView(pagedViewQuery("by_groupcode", vmpgCode, vmpgCode, paginationOffset.copy(limit = paginationOffset.limit.coerceAtMost(ampPaginationLimit)), false), String::class.java, String::class.java))
	}

	@View(name = "by_atc", map = "classpath:js/amp/By_atc.js")
	override fun findAmpsByAtc(datastoreInformation: IDatastoreInformation, atc: String, paginationOffset: PaginationOffset<String>) = flow {
		val client = couchDbDispatcher.getClient(datastoreInformation)
		emitAll(client.queryAmpView(pagedViewQuery("by_atc", atc, atc, paginationOffset.copy(limit = paginationOffset.limit.coerceAtMost(ampPaginationLimit)), false), String::class.java, String::class.java))
	}

	@View(name = "by_groupid", map = "classpath:js/amp/By_groupid.js")
	override fun findAmpsByVmpGroupId(datastoreInformation: IDatastoreInformation, vmpgId: String, paginationOffset: PaginationOffset<String>) = flow {
		val client = couchDbDispatcher.getClient(datastoreInformation)
		emitAll(client.queryAmpView(pagedViewQuery("by_groupid", vmpgId, vmpgId, paginationOffset.copy(limit = paginationOffset.limit.coerceAtMost(ampPaginationLimit)), false), String::class.java, String::class.java))
	}

	@View(name = "by_vmpcode", map = "classpath:js/amp/By_vmpcode.js")
	override fun findAmpsByVmpCode(datastoreInformation: IDatastoreInformation, vmpCode: String, paginationOffset: PaginationOffset<String>) = flow {
		val client = couchDbDispatcher.getClient(datastoreInformation)
		emitAll(client.queryAmpView(pagedViewQuery("by_vmpcode", vmpCode, vmpCode, paginationOffset.copy(limit = paginationOffset.limit.coerceAtMost(ampPaginationLimit)), false), String::class.java, String::class.java))
	}

	@View(name = "by_vmpid", map = "classpath:js/amp/By_vmpid.js")
	override fun findAmpsByVmpId(datastoreInformation: IDatastoreInformation, vmpId: String, paginationOffset: PaginationOffset<String>) = flow {
		val client = couchDbDispatcher.getClient(datastoreInformation)
		emitAll(client.queryAmpView(pagedViewQuery("by_vmpid", vmpId, vmpId, paginationOffset, false), String::class.java, String::class.java))
	}

	override fun listAmpIdsByVmpGroupCode(datastoreInformation: IDatastoreInformation, vmpgCode: String) = flow {
		val client = couchDbDispatcher.getClient(datastoreInformation)
		emitAll(
			client.queryView<String, String>(
				createQuery("by_groupcode")
					.startKey(vmpgCode)
					.endKey(vmpgCode)
					.reduce(false)
					.includeDocs(false)
			).map { it.id }
		)
	}

	override fun listAmpIdsByVmpGroupId(datastoreInformation: IDatastoreInformation, vmpgId: String) = flow {
		val client = couchDbDispatcher.getClient(datastoreInformation)
		emitAll(
			client.queryView<String, String>(
				createQuery("by_groupid")
					.startKey(vmpgId)
					.endKey(vmpgId)
					.reduce(false)
					.includeDocs(false)
			).map { it.id }
		)
	}

	override fun listAmpIdsByVmpCode(datastoreInformation: IDatastoreInformation, vmpCode: String) = flow {
		val client = couchDbDispatcher.getClient(datastoreInformation)
		emitAll(
			client.queryView<String, String>(
				createQuery("by_code")
					.startKey(vmpCode)
					.endKey(vmpCode)
					.reduce(false)
					.includeDocs(false)
			).map { it.id }
		)
	}

	override fun listAmpIdsByVmpId(datastoreInformation: IDatastoreInformation, vmpId: String) = flow {
		val client = couchDbDispatcher.getClient(datastoreInformation)
		emitAll(
			client.queryView<String, String>(
				createQuery("by_id")
					.startKey(vmpId)
					.endKey(vmpId)
					.reduce(false)
					.includeDocs(false)
			).map { it.id }
		)
	}

	override fun listAmpsByVmpGroupCodes(datastoreInformation: IDatastoreInformation, vmpgCodes: List<String>) = flow {
		val client = couchDbDispatcher.getClient(datastoreInformation)
		emitAll(
			client.queryAmps(
				createQuery("by_groupcode")
					.keys(vmpgCodes)
					.reduce(false)
					.includeDocs(true)
					.limit(ampPaginationLimit)
			)
		)
	}

	override fun listAmpsByDmppCodes(datastoreInformation: IDatastoreInformation, dmppCodes: List<String>) = flow {
		val client = couchDbDispatcher.getClient(datastoreInformation)
		emitAll(
			client.queryAmps(
				createQuery("by_dmppcode")
					.keys(dmppCodes)
					.reduce(false)
					.includeDocs(true)
					.limit(ampPaginationLimit)
			)
		)
	}

	override fun listAmpsByVmpGroupIds(datastoreInformation: IDatastoreInformation, vmpGroupIds: List<String>) = flow {
		val client = couchDbDispatcher.getClient(datastoreInformation)
		emitAll(
			client.queryAmps(
				createQuery("by_groupid")
					.keys(vmpGroupIds)
					.reduce(false)
					.includeDocs(true)
					.limit(ampPaginationLimit)
			)
		)
	}

	override fun listAmpsByVmpCodes(datastoreInformation: IDatastoreInformation, vmpCodes: List<String>) = flow {
		val client = couchDbDispatcher.getClient(datastoreInformation)
		emitAll(
			client.queryAmps(
				createQuery("by_vmpcode")
					.keys(vmpCodes)
					.reduce(false)
					.includeDocs(true)
					.limit(ampPaginationLimit)
			)
		)
	}

	override fun listAmpsByVmpIds(datastoreInformation: IDatastoreInformation, vmpIds: List<String>) = flow {
		val client = couchDbDispatcher.getClient(datastoreInformation)
		emitAll(
			client.queryAmps(
				createQuery("by_vmpid")
					.keys(vmpIds)
					.reduce(false)
					.includeDocs(true)
					.limit(ampPaginationLimit)
			)
		)
	}

	override fun getEntities(ids: Collection<String>): Flow<Amp> = localEntityCache?.let { cache ->
		flow {
			val client = couchDbDispatcher.getClient(datastoreInstanceProvider.getInstanceAndGroup())
			val ampsById = client.getAmpsWithCache(cache, ids)
			ids.forEach { id -> ampsById[id]?.let { emit(it) } }
		}
	} ?: super.getEntities(ids)

	/**
	 * Executes a view query that returns [Amp]s as docs.
	 * If there is no [localEntityCache], the [query] is executed as is.
	 * Otherwise, the [query] is executed without docs and every row is filled with the [Amp] taken from the cache
	 * or, for the cache misses, from a single bulk get on the database. All the events are emitted in the same order
	 * as the view result. Rows whose doc cannot be found are emitted as [ViewRowWithMissingDoc], as the database
	 * would do with includeDocs=true.
	 */
	private fun <K, V> Client.queryAmpView(query: ViewQuery, keyType: Class<K>, valueType: Class<V>): Flow<ViewQueryResultEvent> {
		val cache = localEntityCache ?: return queryView(query, keyType, valueType, Amp::class.java)
		return flow {
			val events = queryView(query.includeDocs(false), keyType, valueType, Nothing::class.java).toList()
			val ampsById = getAmpsWithCache(cache, events.mapNotNull { (it as? ViewRowNoDoc<*, *>)?.id })
			events.forEach { event ->
				emit(
					if (event is ViewRowNoDoc<*, *>) {
						ampsById[event.id]?.let { ViewRowWithDoc(event.id, event.key, event.value, it) }
							?: ViewRowWithMissingDoc(event.id, event.key, event.value)
					} else event
				)
			}
		}
	}

	/**
	 * Executes a view query that returns [Amp]s as docs, and emits the [Amp]s only.
	 * If there is no [localEntityCache], the [query] is executed as is.
	 * Otherwise, the [query] is executed without docs, and the [Amp]s are taken from the cache or, for the cache misses,
	 * from a single bulk get on the database. The [Amp]s are emitted in the same order as the view result, rows whose
	 * doc cannot be found are skipped.
	 */
	private fun Client.queryAmps(query: ViewQuery): Flow<Amp> {
		val cache = localEntityCache
			?: return queryViewIncludeDocs<String, Int, Amp>(query).map { it.doc }
		return flow {
			val ids = queryView<String, Int>(query.includeDocs(false)).map { it.id }.toList()
			val ampsById = getAmpsWithCache(cache, ids)
			ids.forEach { id -> ampsById[id]?.let { emit(it) } }
		}
	}

	/**
	 * Gets the [Amp]s with the given [ids], taking them from the [cache] when present and loading all the
	 * missing ones from the database in a single request, in the same order as [ids].
	 * The loaded [Amp]s are added to the [cache].
	 *
	 * @return the [Amp]s found, indexed by id.
	 */
	private suspend fun Client.getAmpsWithCache(cache: LocalEntityCache<Amp>, ids: Collection<String>): Map<String, Amp> {
		val distinctIds = ids.distinct()
		val cachedAmps = cache.getAllPresent(distinctIds)
		val missingIds = distinctIds.filter { it !in cachedAmps }
		if (missingIds.isEmpty()) {
			return cachedAmps
		}
		val loadedAmps = get(missingIds, Amp::class.java).toList().onEach { cache.put(it) }
		return cachedAmps + loadedAmps.associateBy { it.id }
	}

	override suspend fun getVersion(datastoreInformation: IDatastoreInformation) =
		couchDbDispatcher.getClient(datastoreInformation).get("org.taktik.icure.samv2", SamVersion::class.java)

	override suspend fun getSignature(datastoreInformation: IDatastoreInformation, clazz: String) =
		couchDbDispatcher.getClient(datastoreInformation).get("org.taktik.icure.samv2.signatures.$clazz", SamVersion::class.java)

	override suspend fun getProductIdsFromSignature(datastoreInformation: IDatastoreInformation, type: String): Map<String, String> {
		val client = couchDbDispatcher.getClient(datastoreInformation)

		return client.get("org.taktik.icure.samv2.signatures.$type", SamVersion::class.java)?.let { samVersion ->
			GZIPInputStream(client.getAttachment(samVersion.id, "signatures", samVersion.rev).toInputStream()).reader(Charsets.UTF_8).useLines {
				it.fold(HashMap()) { acc, l -> acc.also { l.split('|').let { parts -> acc[parts[0]] = parts[1] } } }
			}
		} ?: throw IllegalArgumentException("Signature $type does not exist")
	}

	@View(name = "by_language_label", map = "classpath:js/amp/By_language_label.js")
	override fun findAmpsByLabel(datastoreInformation: IDatastoreInformation, language: String?, label: String?, pagination: PaginationOffset<List<String>>): Flow<ViewQueryResultEvent> = flow {
		val client = couchDbDispatcher.getClient(datastoreInformation)

		val (from, to) = makeFromTo(label, language)
		val viewQuery = pagedViewQuery(
			"by_language_label",
			from,
			to,
			pagination.toPaginationOffset { sk -> ComplexKey.of(*sk.mapIndexed { i, s -> if (i == 1) sanitizeString(s) else s }.toTypedArray()) }.let {
			  it.copy(limit = it.limit.coerceAtMost(ampPaginationLimit))
			},
			false
		)
		emitAll(client.queryAmpView(viewQuery, ComplexKey::class.java, String::class.java))
	}

	@View(name = "by_chapter_paragraph", map = "classpath:js/amp/By_chapter_paragraph.js")
	override fun findAmpsByChapterParagraph(datastoreInformation: IDatastoreInformation, chapter: String, paragraph: String, paginationOffset: PaginationOffset<List<String>>): Flow<ViewQueryResultEvent> = flow {
		val client = couchDbDispatcher.getClient(datastoreInformation)

		val viewQuery = pagedViewQuery(
			"by_chapter_paragraph",
			ComplexKey.of(chapter, paragraph),
			ComplexKey.of(chapter, paragraph),
			paginationOffset.toPaginationOffset { sk -> ComplexKey.of(*sk.mapIndexed { i, s -> if (i == 1) sanitizeString(s) else s }.toTypedArray()) },
			false
		)
		emitAll(client.queryAmpView(viewQuery, ComplexKey::class.java, Int::class.java))
	}

	@View(name = "by_language_label_official_sort", map = "classpath:js/amp/By_language_label_official_sort.js", secondaryPartition = "official-sort")
	override fun listAmpAmppIdsByLabel(
		datastoreInformation: IDatastoreInformation,
		language: String,
		label: String
	): Flow<Pair<String, String>> = flow {
		require(label.length >= 3) { "Label must be at least 3 characters long" }
		val rowIds = coroutineScope {
			//TODO check the relevance of using a SupervisorScope to avoid failure on parallel cancellation
			//TODO Use Pair<Long,Long> for key (SHA) and it
			//NOTE: the datastoreInformation always point to the same db and must not be part of the cache key
			amppCache.get(label) { key, _ ->
				future {
					val client = couchDbDispatcher.getClient(datastoreInformation)
					makeFromTo(key, language).let { (from, to) ->
						val viewQuery = createQuery("by_language_label_official_sort", "official-sort")
							.startKey(from)
							.endKey(to)
							.reduce(false)
							.limit(10001)
							.includeDocs(false)
						client.queryView<ComplexKey, AmppRef>(viewQuery)
							.mapNotNull { it.value?.let { value -> ViewRowNoDoc(it.id, it.key, AmppRef(
								value.vmpGroupName,
								value.index,
								value.name?.let { sanitizeForSorting(it) },
								value.ctiExtended)
							) } }.toList()
							.also { if (it.size > 10000) throw TooManyResultsException("Too many results for label '$label', please provide a more precise label") }
							.sortedWith(compareBy({it.value?.vmpGroupName}, {it.value?.index}, {it.value?.name}))
							.mapNotNull { it.value?.ctiExtended?.let { ctiExtended -> it.id to ctiExtended } }
					}
				}
			}.await()
		}
		emitAll(rowIds.asFlow())
	}


	override fun listAmpIdsByLabel(datastoreInformation: IDatastoreInformation, language: String, label: String): Flow<String> = flow {
		require(label.length >= 3) { "Label must be at least 3 characters long" }
		val rowIds = coroutineScope {
			//TODO check the relevance of using a SupervisorScope to avoid failure on parallel cancellation
			//TODO Use Pair<Long,Long> for key (SHA) and it
			//NOTE: the datastoreInformation always point to the same db and must not be part of the cache key
			ampCache.get(label) { key, _ ->
				future {
					val client = couchDbDispatcher.getClient(datastoreInformation)
					makeFromTo(key, language).let { (from, to) ->
						val viewQuery = createQuery("by_language_label")
							.startKey(from)
							.endKey(to)
							.reduce(false)
							.limit(10001)
							.includeDocs(false)
						client.queryView<ComplexKey, String>(viewQuery).map { ViewRowNoDoc(it.id, it.key, sanitizeForSorting(it.value)) }.toList()
							.also { if (it.size > 10000) throw TooManyResultsException("Too many results for label '$label', please provide a more precise label") }
							.sortedBy { it.value }.map { it.id }
					}
				}
			}.await()
		}
		emitAll(rowIds.asFlow())
	}
}

class AmppRefDeserializer : JsonDeserializer<AmppRef>() {
	override fun deserialize(p: JsonParser, ctxt: DeserializationContext?): AmppRef {
		val array = p.codec.readTree<com.fasterxml.jackson.databind.node.ArrayNode>(p)
		check(array.size() == 4) { "AmppRef array must have exactly 4 elements" }

		val vmpGroupName = if (array[0].isNull) null else array[0].asText()
		val index = array[1].asDouble()
		val name = if (array[2].isNull) null else array[2].asText()
		val ctiExtended = if (array[3].isNull) null else array[3].asText()

		return AmppRef(vmpGroupName, index, name, ctiExtended)
	}
}

@JsonDeserialize(using = AmppRefDeserializer::class)
data class AmppRef(
	val vmpGroupName: String? = null,
	val index: Double,
	val name: String?,
	val ctiExtended: String?
)
