/*
 * Copyright (C) 2018 Taktik SA
 *
 * This file is part of iCureBackend.
 *
 * iCureBackend is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * iCureBackend is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with iCureBackend.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.taktik.icure.be.ehealth.logic.kmehr.patientinfo.impl.v20110701

import kotlinx.coroutines.flow.flow
import org.springframework.context.annotation.Profile
import org.taktik.icure.asynclogic.*
import org.taktik.icure.be.ehealth.dto.kmehr.v20110701.be.fgov.ehealth.standards.kmehr.cd.v1.CDTRANSACTION
import org.taktik.icure.be.ehealth.dto.kmehr.v20110701.be.fgov.ehealth.standards.kmehr.cd.v1.CDTRANSACTIONschemes
import org.taktik.icure.be.ehealth.dto.kmehr.v20110701.be.fgov.ehealth.standards.kmehr.schema.v1.AuthorType
import org.taktik.icure.be.ehealth.dto.kmehr.v20110701.be.fgov.ehealth.standards.kmehr.schema.v1.FolderType
import org.taktik.icure.be.ehealth.dto.kmehr.v20110701.be.fgov.ehealth.standards.kmehr.schema.v1.TransactionType
import org.taktik.icure.be.ehealth.logic.kmehr.Config
import org.taktik.icure.be.ehealth.logic.kmehr.emitMessage
import org.taktik.icure.be.ehealth.logic.kmehr.v20110701.KmehrExport
import org.taktik.icure.config.KmehrConfiguration
import org.taktik.icure.entities.HealthcareParty
import org.taktik.icure.entities.Patient
import java.time.Instant
import java.util.Date
import java.util.GregorianCalendar
import javax.xml.datatype.DatatypeConstants
import javax.xml.datatype.DatatypeFactory
import javax.xml.datatype.XMLGregorianCalendar

@Profile("kmehr")
@org.springframework.stereotype.Service
class PatientInfoFileExport(
    codeLogic: CodeLogic,
    contactLogic: ContactLogic,
    documentLogic: DocumentLogic,
    kmehrConfiguration: KmehrConfiguration,
    private val healthcarePartyLogic: HealthcarePartyLogic,
    private val userLogic: UserLogic,
) : KmehrExport(codeLogic, contactLogic, documentLogic, kmehrConfiguration) {

    /**
     * Creates a PatientInfo XML with the information provided.
     * @param patient the iCure Patient.
     * @param sender the iCure HCP responsible for the export.
     * @param language the language of the Content.
     * @return a Flow of DataBuffer containing the XML.
     */
    fun export(
        patient: Patient,
        sender: HealthcareParty,
        language: String,
    ) = flow {
        val config = Config(
            _kmehrId = System.currentTimeMillis().toString(),
            date = makeXGC(Instant.now().toEpochMilli())!!,
            time = makeXGC(Instant.now().toEpochMilli(), true)!!,
            soft = Config.Software(name = "iCure", version = kmehrConfig.kmehrVersion),
            defaultLanguage = language,
            format = Config.Format.KMEHR,
        )

        val message = initializeMessage(sender, config)
        message.folders.add(
            FolderType().apply {
                ids.add(idKmehr(1))
                this.patient = makePatient(patient, config)
                this.transactions.add(
                    TransactionType().apply {
                        ids.add(idKmehr(1))
                        cds.add(CDTRANSACTION().apply { s = CDTRANSACTIONschemes.CD_TRANSACTION; value = "note" })
                        date = config.date
                        time = config.time
                        author = AuthorType().apply {
                            hcparties.add(
                                healthcarePartyLogic.getHealthcareParty(
                                    patient.author?.let { userLogic.getUser(it, false)?.healthcarePartyId } ?: sender.id)?.let { createParty(it) }
                            )
                        }
                        isIscomplete = true
                        isIsvalidated = true
                    }
                )
            },
        )
        emitMessage(message).collect { emit(it) }
    }

    /**
     * Create an XML date from a timestamp.
     * @param date a timestamp.
     * @param unsetMillis whether to put undefined in the millisecond field.
     * @return a XMLGregorianCalendar.
     */
    fun makeXGC(date: Long?, unsetMillis: Boolean = false): XMLGregorianCalendar? =
        date?.let {
            DatatypeFactory.newInstance().newXMLGregorianCalendar(GregorianCalendar.getInstance().apply { time = Date(date) } as GregorianCalendar)
                .apply {
                    timezone = DatatypeConstants.FIELD_UNDEFINED
                    if (unsetMillis) {
                        millisecond = DatatypeConstants.FIELD_UNDEFINED
                    }
                }
        }
}
