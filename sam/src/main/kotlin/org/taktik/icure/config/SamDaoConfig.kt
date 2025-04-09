package org.taktik.icure.config

import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.taktik.couchdb.dao.DesignDocumentProvider
import org.taktik.couchdb.id.IDGenerator
import org.taktik.icure.asyncdao.CouchDbDispatcher
import org.taktik.icure.asyncdao.samv2.AmpDAO
import org.taktik.icure.asyncdao.samv2.NmpDAO
import org.taktik.icure.asyncdao.samv2.ParagraphDAO
import org.taktik.icure.asyncdao.samv2.PharmaceuticalFormDAO
import org.taktik.icure.asyncdao.samv2.ProductIdDAO
import org.taktik.icure.asyncdao.samv2.SubstanceDAO
import org.taktik.icure.asyncdao.samv2.VerseDAO
import org.taktik.icure.asyncdao.samv2.VmpDAO
import org.taktik.icure.asyncdao.samv2.VmpGroupDAO
import org.taktik.icure.asyncdao.samv2.impl.AmpDAOImpl
import org.taktik.icure.asyncdao.samv2.impl.NmpDAOImpl
import org.taktik.icure.asyncdao.samv2.impl.ParagraphDAOImpl
import org.taktik.icure.asyncdao.samv2.impl.PharmaceuticalFormDAOImpl
import org.taktik.icure.asyncdao.samv2.impl.ProductIdDAOImpl
import org.taktik.icure.asyncdao.samv2.impl.SamUpdateDAOImpl
import org.taktik.icure.asyncdao.samv2.impl.SubstanceDAOImpl
import org.taktik.icure.asyncdao.samv2.impl.VerseDAOImpl
import org.taktik.icure.asyncdao.samv2.impl.VmpDAOImpl
import org.taktik.icure.asyncdao.samv2.impl.VmpGroupDAOImpl
import org.taktik.icure.asynclogic.datastore.DatastoreInstanceProvider
import org.taktik.icure.asynclogic.samv2.UpdatesBridge
import org.taktik.icure.asynclogic.samv2.impl.SamV2Updater
import org.taktik.icure.properties.SAMCouchDbProperties


@Configuration
@Profile("sam")
open class SamDaoConfig {

    @Bean
    open fun ampDao(
        @Qualifier("drugCouchDbDispatcher") couchDbDispatcher: CouchDbDispatcher,
        idGenerator: IDGenerator,
        datastoreInstanceProvider: DatastoreInstanceProvider,
        designDocumentProvider: DesignDocumentProvider
    ): AmpDAO = AmpDAOImpl(
        couchDbDispatcher = couchDbDispatcher,
        idGenerator = idGenerator,
        datastoreInstanceProvider = datastoreInstanceProvider,
        designDocumentProvider = designDocumentProvider
    )

    @Bean
    open fun nmpDao(
        @Qualifier("drugCouchDbDispatcher") couchDbDispatcher: CouchDbDispatcher,
        idGenerator: IDGenerator,
        datastoreInstanceProvider: DatastoreInstanceProvider,
        designDocumentProvider: DesignDocumentProvider
    ): NmpDAO = NmpDAOImpl(
        couchDbDispatcher = couchDbDispatcher,
        idGenerator = idGenerator,
        datastoreInstanceProvider = datastoreInstanceProvider,
        designDocumentProvider = designDocumentProvider
    )

    @Bean
    open fun vmpDao(
        @Qualifier("drugCouchDbDispatcher") couchDbDispatcher: CouchDbDispatcher,
        idGenerator: IDGenerator,
        datastoreInstanceProvider: DatastoreInstanceProvider,
        designDocumentProvider: DesignDocumentProvider
    ): VmpDAO = VmpDAOImpl(
        couchDbDispatcher = couchDbDispatcher,
        idGenerator = idGenerator,
        datastoreInstanceProvider = datastoreInstanceProvider,
        designDocumentProvider = designDocumentProvider
    )

    @Bean
    open fun vmpGroupDao(
        @Qualifier("drugCouchDbDispatcher") couchDbDispatcher: CouchDbDispatcher,
        idGenerator: IDGenerator,
        datastoreInstanceProvider: DatastoreInstanceProvider,
        designDocumentProvider: DesignDocumentProvider
    ): VmpGroupDAO = VmpGroupDAOImpl(
        couchDbDispatcher = couchDbDispatcher,
        idGenerator = idGenerator,
        datastoreInstanceProvider = datastoreInstanceProvider,
        designDocumentProvider = designDocumentProvider
    )

    @Bean
    open fun verseDao(
        @Qualifier("chapIVCouchDbDispatcher") couchDbDispatcher: CouchDbDispatcher,
        idGenerator: IDGenerator,
        datastoreInstanceProvider: DatastoreInstanceProvider,
        designDocumentProvider: DesignDocumentProvider
    ): VerseDAO = VerseDAOImpl(
        couchDbDispatcher = couchDbDispatcher,
        idGenerator = idGenerator,
        datastoreInstanceProvider = datastoreInstanceProvider,
        designDocumentProvider = designDocumentProvider
    )

    @Bean
    open fun paragraphDao(
        @Qualifier("chapIVCouchDbDispatcher") couchDbDispatcher: CouchDbDispatcher,
        idGenerator: IDGenerator,
        datastoreInstanceProvider: DatastoreInstanceProvider,
        designDocumentProvider: DesignDocumentProvider,
        ampDao: AmpDAO
    ): ParagraphDAO = ParagraphDAOImpl(
        couchDbDispatcher = couchDbDispatcher,
        idGenerator = idGenerator,
        datastoreInstanceProvider = datastoreInstanceProvider,
        designDocumentProvider = designDocumentProvider,
        ampDAO = ampDao,
    )

    @Bean
    open fun pharmaceuticalFormDao(
        @Qualifier("drugCouchDbDispatcher") couchDbDispatcher: CouchDbDispatcher,
        idGenerator: IDGenerator,
        datastoreInstanceProvider: DatastoreInstanceProvider,
        designDocumentProvider: DesignDocumentProvider
    ): PharmaceuticalFormDAO = PharmaceuticalFormDAOImpl(
        couchDbDispatcher = couchDbDispatcher,
        idGenerator = idGenerator,
        datastoreInstanceProvider = datastoreInstanceProvider,
        designDocumentProvider = designDocumentProvider
    )

    @Bean
    open fun substanceDao(
        @Qualifier("drugCouchDbDispatcher") couchDbDispatcher: CouchDbDispatcher,
        idGenerator: IDGenerator,
        datastoreInstanceProvider: DatastoreInstanceProvider,
        designDocumentProvider: DesignDocumentProvider
    ): SubstanceDAO = SubstanceDAOImpl(
        couchDbDispatcher = couchDbDispatcher,
        idGenerator = idGenerator,
        datastoreInstanceProvider = datastoreInstanceProvider,
        designDocumentProvider = designDocumentProvider
    )

    @Bean
    open fun productIdDao(
        @Qualifier("drugCouchDbDispatcher") couchDbDispatcher: CouchDbDispatcher,
        idGenerator: IDGenerator,
        datastoreInstanceProvider: DatastoreInstanceProvider,
        designDocumentProvider: DesignDocumentProvider
    ): ProductIdDAO = ProductIdDAOImpl(
        couchDbDispatcher = couchDbDispatcher,
        idGenerator = idGenerator,
        datastoreInstanceProvider = datastoreInstanceProvider,
        designDocumentProvider = designDocumentProvider
    )

    @Bean
    @ConditionalOnProperty(name = ["icure.sam.updaterUrl"], matchIfMissing = false)
    open fun samV2Updater(
        @Qualifier("drugCouchDbDispatcher") drugsCouchDbDispatcher: CouchDbDispatcher,
        @Qualifier("drugNextVersionCouchDbDispatcher") drugNextVersionCouchDbDispatcher: CouchDbDispatcher?,
        @Qualifier("chapIVCouchDbDispatcher") chapIVCouchDbDispatcher: CouchDbDispatcher,
        @Qualifier("chapIVNextVersionCouchDbDispatcher") chapIVNextVersionCouchDbDispatcher: CouchDbDispatcher?,
        ampDAO: AmpDAO,
        vmpDAO: VmpDAO,
        vmpGroupDAO: VmpGroupDAO,
        nmpDAO: NmpDAO,
        paragraphDAO: ParagraphDAO,
        pharmaceuticalFormDAO: PharmaceuticalFormDAO,
        substanceDAO: SubstanceDAO,
        verseDAO: VerseDAO,
        updatesBridge: UpdatesBridge,
        idGenerator: IDGenerator,
        datastoreInstanceProvider: DatastoreInstanceProvider,
        designDocumentProvider: DesignDocumentProvider,
        samCouchDbProperties: SAMCouchDbProperties
    ) = drugNextVersionCouchDbDispatcher?.let { cdd ->
        checkNotNull(chapIVNextVersionCouchDbDispatcher) { "chapIVNextVersionCouchDbDispatcher should not be null" }
        val ampDAO = AmpDAOImpl(
            cdd,
            idGenerator,
            datastoreInstanceProvider,
            designDocumentProvider
        )
        runBlocking {
            SamV2Updater(
                cdd,
                ampDAO.apply { forceInitStandardDesignDocument(true) },
                VmpDAOImpl(cdd, idGenerator, datastoreInstanceProvider, designDocumentProvider).apply { forceInitStandardDesignDocument(true) },
                VmpGroupDAOImpl(cdd, idGenerator, datastoreInstanceProvider, designDocumentProvider).apply { forceInitStandardDesignDocument(true) },
                NmpDAOImpl(cdd, idGenerator, datastoreInstanceProvider, designDocumentProvider).apply { forceInitStandardDesignDocument(true) },
                ParagraphDAOImpl(
                    chapIVNextVersionCouchDbDispatcher,
                    idGenerator,
                    datastoreInstanceProvider,
                    designDocumentProvider,
                    ampDAO
                ).apply { forceInitStandardDesignDocument(true) },
                PharmaceuticalFormDAOImpl(cdd, idGenerator, datastoreInstanceProvider, designDocumentProvider).apply { forceInitStandardDesignDocument(true) },
                SubstanceDAOImpl(cdd, idGenerator, datastoreInstanceProvider, designDocumentProvider).apply { forceInitStandardDesignDocument(true) },
                VerseDAOImpl(
                    chapIVNextVersionCouchDbDispatcher,
                    idGenerator,
                    datastoreInstanceProvider,
                    designDocumentProvider
                ).apply { forceInitStandardDesignDocument(true) },
                SamUpdateDAOImpl(cdd, idGenerator, datastoreInstanceProvider, designDocumentProvider).apply { forceInitStandardDesignDocument(true) },
                updatesBridge,
                datastoreInstanceProvider
            )
        }
    } ?: SamV2Updater(
        drugsCouchDbDispatcher,
        ampDAO,
        vmpDAO,
        vmpGroupDAO,
        nmpDAO,
        paragraphDAO,
        pharmaceuticalFormDAO,
        substanceDAO,
        verseDAO,
        SamUpdateDAOImpl(drugsCouchDbDispatcher, idGenerator, datastoreInstanceProvider, designDocumentProvider),
        updatesBridge,
        datastoreInstanceProvider
    )
}
