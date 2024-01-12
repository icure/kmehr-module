/*
 * Copyright (c) 2020. Taktik SA, All rights reserved.
 */

package org.taktik.icure.be.ehealth.logic.kmehr.medex.impl.v20131001

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.commons.io.output.ByteArrayOutputStream
import org.apache.commons.logging.LogFactory
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.taktik.icure.asynclogic.CodeLogic
import org.taktik.icure.asynclogic.DocumentLogic
import org.taktik.icure.be.ehealth.dto.kmehr.v20131001.Utils
import org.taktik.icure.be.ehealth.dto.kmehr.v20131001.be.fgov.ehealth.standards.kmehr.cd.v1.*
import org.taktik.icure.be.ehealth.dto.kmehr.v20131001.be.fgov.ehealth.standards.kmehr.dt.v1.TextType
import org.taktik.icure.be.ehealth.dto.kmehr.v20131001.be.fgov.ehealth.standards.kmehr.id.v1.IDHCPARTY
import org.taktik.icure.be.ehealth.dto.kmehr.v20131001.be.fgov.ehealth.standards.kmehr.id.v1.IDHCPARTYschemes
import org.taktik.icure.be.ehealth.dto.kmehr.v20131001.be.fgov.ehealth.standards.kmehr.id.v1.IDKMEHR
import org.taktik.icure.be.ehealth.dto.kmehr.v20131001.be.fgov.ehealth.standards.kmehr.id.v1.IDKMEHRschemes
import org.taktik.icure.be.ehealth.dto.kmehr.v20131001.be.fgov.ehealth.standards.kmehr.schema.v1.*
import org.taktik.icure.be.ehealth.logic.kmehr.Config
import org.taktik.icure.be.ehealth.logic.kmehr.medex.MedexLogic
import org.taktik.icure.be.ehealth.logic.kmehr.v20131001.KmehrExport
import org.taktik.icure.config.KmehrConfiguration
import org.taktik.icure.entities.HealthcareParty
import org.taktik.icure.entities.Patient
import java.io.OutputStreamWriter
import java.time.Instant
import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller

@Profile("kmehr")
@Service
class MedexLogicImpl(
    codeLogic: CodeLogic,
    documentLogic: DocumentLogic,
    kmehrConfiguration: KmehrConfiguration
) : MedexLogic, KmehrExport(codeLogic, documentLogic, kmehrConfiguration) {

    override val log = LogFactory.getLog(MedexLogicImpl::class.java)

    internal val config = Config(
        _kmehrId = System.currentTimeMillis().toString(),
        date = makeXGC(Instant.now().toEpochMilli())!!,
        time = Utils.makeXGC(Instant.now().toEpochMilli(), true)!!,
        soft = Config.Software(name = "iCure", version = kmehrConfiguration.kmehrVersion),
        clinicalSummaryType = "",
        defaultLanguage = "en",
    )

    override suspend fun createMedex(
        author: HealthcareParty,
        patient: Patient,
        lang: String,
        incapacityType: String,
        incapacityReason: String,
        outOfHomeAllowed: Boolean,
        certificateDate: Long?,
        contentDate: Long?,
        beginDate: Long,
        endDate: Long,
        diagnosisICD: String?,
        diagnosisICPC: String?,
        diagnosisDescr: String?,
    ): String {
        val message = Kmehrmessage().apply {
            header = HeaderType().apply {
                standard = StandardType().apply { cd = CDSTANDARD().apply { value = this@MedexLogicImpl.standard } }
                ids.add(IDKMEHR().apply { s = IDKMEHRschemes.ID_KMEHR; value = (author.nihii ?: author.id) + "." + System.currentTimeMillis() })
                this.date = makeXGC(Instant.now().toEpochMilli())
                this.time = makeXGC(Instant.now().toEpochMilli())
                this.sender = SenderType().apply {
                    hcparties.add(
                        HcpartyType().apply {
                            ids.add(IDHCPARTY().apply { s = IDHCPARTYschemes.LOCAL; sl = config.soft?.name; sv = config.soft?.version; value = "${config.soft?.name}-${config.soft?.version}" })
                            cds.addAll(listOf(CDHCPARTY().apply { s(CDHCPARTYschemes.CD_HCPARTY); value = "application" })); this.name = "iCure ${kmehrConfiguration.kmehrVersion}"
                        },
                    )
                    hcparties.add(createParty(author, emptyList()))
                }
                this.recipients.add(
                    RecipientType().apply {
                        hcparties.add(HcpartyType().apply { this.cds.addAll(listOf(CDHCPARTY().apply { s(CDHCPARTYschemes.CD_HCPARTY); value = "application" })); this.name = "medex" })
                    }
                )
            }
            folders.add(
                FolderType().apply {
                    this.ids.add(IDKMEHR().apply { s = IDKMEHRschemes.ID_KMEHR; value = 1.toString() })
                    this.patient = makePerson(patient, config)

                    this.transactions.add(
                        TransactionType().apply {
                            this.ids.add(IDKMEHR().apply { s = IDKMEHRschemes.ID_KMEHR; value = 1.toString() })
                            this.cds.add(CDTRANSACTION().apply { s(CDTRANSACTIONschemes.CD_TRANSACTION); value = "notification" })
                            this.cds.add(CDTRANSACTION().apply { s(CDTRANSACTIONschemes.CD_TRANSACTION_TYPE); value = incapacityType })
                            this.date = makeXGC(certificateDate)
                            this.time = makeXGC(certificateDate)
                            this.author = AuthorType().apply {
                                hcparties.add(createParty(author, emptyList()))
                            }
                            this.isIscomplete = true
                            this.isIsvalidated = true

                            this.headingsAndItemsAndTexts.add(
                                ItemType().apply {
                                    this.ids.add(IDKMEHR().apply { s = IDKMEHRschemes.ID_KMEHR; value = 1.toString() })
                                    this.cds.add(CDITEM().apply { s(CDITEMschemes.CD_ITEM); value = "incapacity" })

                                    this.beginmoment = Utils.makeMomentTypeFromFuzzyLong(beginDate)
                                    this.endmoment = Utils.makeMomentTypeFromFuzzyLong(endDate)

                                    this.contents.add(
                                        ContentType().apply {
                                            this.incapacity = IncapacityType().apply {
                                                this.cds.add(CDINCAPACITY().apply { value = CDINCAPACITYvalues.WORK })
                                                this.incapacityreason = IncapacityreasonType().apply {
                                                    this.cd = CDINCAPACITYREASON().apply { value = CDINCAPACITYREASONvalues.fromValue(incapacityReason) }
                                                }
                                                this.isOutofhomeallowed = outOfHomeAllowed
                                            }

                                            contentDate?.let {
                                                this.date = makeXGC(contentDate)
                                            }
                                        },
                                    )
                                },
                            )

                            this.headingsAndItemsAndTexts.add(
                                ItemType().apply {
                                    this.ids.add(IDKMEHR().apply { s = IDKMEHRschemes.ID_KMEHR; sv = "1.0"; value = 2.toString() })
                                    this.cds.add(CDITEM().apply { s(CDITEMschemes.CD_ITEM); value = "diagnosis" })

                                    this.contents.add(
                                        ContentType().apply {
                                            diagnosisICD?.let {
                                                this.cds.add(CDCONTENT().apply { s(CDCONTENTschemes.ICD); value = diagnosisICD })
                                            }
                                            diagnosisICPC?.let {
                                                this.cds.add(CDCONTENT().apply { s(CDCONTENTschemes.ICPC); value = diagnosisICPC })
                                            }
                                        },
                                    )

                                    diagnosisDescr?.let {
                                        this.contents.add(
                                            ContentType().apply {
                                                this.texts.add(
                                                    TextType().apply {
                                                        this.l = lang
                                                        this.value = diagnosisDescr
                                                    }
                                                )
                                            }
                                        )
                                    }
                                }
                            )
                        }
                    )
                }
            )
        }

        val jaxbMarshaller = JAXBContext.newInstance("org.taktik.icure.be.ehealth.dto.kmehr.v20131001.be.fgov.ehealth.standards.kmehr.schema.v1", com.sun.xml.bind.v2.ContextFactory::class.java.classLoader).createMarshaller()
        val bos = ByteArrayOutputStream(10000)

        // output pretty printed
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)
        jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8")

        withContext(Dispatchers.IO) {
            jaxbMarshaller.marshal(message, OutputStreamWriter(bos, "UTF-8"))
        }

        return bos.toString("UTF-8")
    }
}
