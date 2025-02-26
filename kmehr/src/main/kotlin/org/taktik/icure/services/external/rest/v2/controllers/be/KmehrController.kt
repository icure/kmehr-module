/*
 * Copyright (c) 2020. Taktik SA, All rights reserved.
 */

package org.taktik.icure.services.external.rest.v2.controllers.be

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactor.mono
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Profile
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.MediaType
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.taktik.icure.asynclogic.DocumentLogic
import org.taktik.icure.asynclogic.HealthcarePartyLogic
import org.taktik.icure.asynclogic.PatientLogic
import org.taktik.icure.asynclogic.SessionInformationProvider
import org.taktik.icure.asynclogic.UserLogic
import org.taktik.icure.be.ehealth.dto.kmehr.v20161201.Utils
import org.taktik.icure.be.ehealth.logic.getAndDecryptMainAttachment
import org.taktik.icure.be.ehealth.logic.kmehr.Config
import org.taktik.icure.be.ehealth.logic.kmehr.diarynote.DiaryNoteLogic
import org.taktik.icure.be.ehealth.logic.kmehr.incapacity.IncapacityLogic
import org.taktik.icure.be.ehealth.logic.kmehr.medicationscheme.MedicationSchemeLogic
import org.taktik.icure.be.ehealth.logic.kmehr.note.KmehrNoteLogic
import org.taktik.icure.be.ehealth.logic.kmehr.patientinfo.PatientInfoFileLogic
import org.taktik.icure.be.ehealth.logic.kmehr.smf.SoftwareMedicalFileLogic
import org.taktik.icure.be.ehealth.logic.kmehr.sumehr.SumehrLogic
import org.taktik.icure.config.KmehrConfiguration
import org.taktik.icure.domain.mapping.ImportMapping
import org.taktik.icure.exceptions.NotFoundRequestException
import org.taktik.icure.services.external.rest.v2.dto.HealthElementDto
import org.taktik.icure.services.external.rest.v2.dto.be.kmehr.DiaryNoteExportInfoDto
import org.taktik.icure.services.external.rest.v2.dto.be.kmehr.IncapacityExportInfoDto
import org.taktik.icure.services.external.rest.v2.dto.be.kmehr.MedicationSchemeExportInfoDto
import org.taktik.icure.services.external.rest.v2.dto.be.kmehr.SoftwareMedicalFileExportDto
import org.taktik.icure.services.external.rest.v2.dto.be.kmehr.SumehrContentDto
import org.taktik.icure.services.external.rest.v2.dto.be.kmehr.SumehrExportInfoDto
import org.taktik.icure.services.external.rest.v2.dto.be.kmehr.SumehrStatus
import org.taktik.icure.services.external.rest.v2.dto.be.kmehr.SumehrValidityDto
import org.taktik.icure.services.external.rest.v2.mapper.IncapacityExportInfoV2Mapper
import org.taktik.icure.services.external.rest.v2.dto.embed.ContentDto
import org.taktik.icure.services.external.rest.v2.dto.embed.ServiceDto
import org.taktik.icure.services.external.rest.v2.mapper.HealthElementV2Mapper
import org.taktik.icure.services.external.rest.v2.mapper.HealthcarePartyV2Mapper
import org.taktik.icure.services.external.rest.v2.mapper.embed.ImportResultV2Mapper
import org.taktik.icure.services.external.rest.v2.mapper.embed.PartnershipV2Mapper
import org.taktik.icure.services.external.rest.v2.mapper.embed.PatientHealthCarePartyV2Mapper
import org.taktik.icure.services.external.rest.v2.mapper.embed.ServiceV2Mapper
import org.taktik.icure.utils.injectReactorContext
import java.time.Instant
import java.util.stream.Collectors

@ExperimentalCoroutinesApi
@RestController("kmehrControllerV2")
@Profile("kmehr")
@RequestMapping("/rest/v2/be_kmehr")
@Tag(name = "bekmehr")
class KmehrController(
	private val sessionLogic: SessionInformationProvider,
	@Qualifier("sumehrLogicV1") val sumehrLogicV1: SumehrLogic,
	@Qualifier("sumehrLogicV2") val sumehrLogicV2: SumehrLogic,
	private val softwareMedicalFileLogic: SoftwareMedicalFileLogic,
	private val medicationSchemeLogic: MedicationSchemeLogic,
	private val incapacityLogic: IncapacityLogic,
	private val diaryNoteLogic: DiaryNoteLogic,
	private val kmehrNoteLogic: KmehrNoteLogic,
	private val userLogic: UserLogic,
	val healthcarePartyLogic: HealthcarePartyLogic,
	val patientLogic: PatientLogic,
	val documentLogic: DocumentLogic,
	private val kmehrConfiguration: KmehrConfiguration,
	private val patientInfoFileLogic: PatientInfoFileLogic,
	private val healthElementV2Mapper: HealthElementV2Mapper,
	private val serviceV2Mapper: ServiceV2Mapper,
	private val healthcarePartyV2Mapper: HealthcarePartyV2Mapper,
	private val patientHealthCarePartyV2Mapper: PatientHealthCarePartyV2Mapper,
	private val partnershipV2Mapper: PartnershipV2Mapper,
	private val importResultV2Mapper: ImportResultV2Mapper,
	val incapacityExportInfoV2Mapper: IncapacityExportInfoV2Mapper,
) {

	@Operation(summary = "Generate diarynote", responses = [ApiResponse(responseCode = "200", content = [Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE, schema = Schema(type = "string", format = "binary"))])])
	@PostMapping("/diarynote/{patientId}/export", produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
	fun generateDiaryNote(
		@PathVariable patientId: String,
		@RequestBody info: DiaryNoteExportInfoDto,
	) = flow {
		patientLogic.getPatient(patientId)?.let {
			healthcarePartyLogic.getHealthcareParty(sessionLogic.getCurrentHealthcarePartyId())?.let { it1 ->
				emitAll(diaryNoteLogic.createDiaryNote(
					it,
					info.encryptionDecryptionKeys,
					it1,
					healthcarePartyV2Mapper.map(info.recipient!!),
					info.note,
					info.tags,
					info.contexts,
					info.psy ?: false,
					info.documentId,
					info.attachmentId
				))
			}
		} ?: throw IllegalArgumentException("Missing argument")
	}.injectReactorContext()

	@Operation(summary = "Generate sumehr", responses = [ApiResponse(responseCode = "200", content = [Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE, schema = Schema(type = "string", format = "binary"))])])
	@PostMapping("/sumehr/{patientId}/export", produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
	fun generateSumehr(
		@PathVariable patientId: String,
		@RequestParam language: String,
		@RequestBody info: SumehrExportInfoDto
	) = flow {
		patientLogic.getPatient(patientId)?.let {
			healthcarePartyLogic.getHealthcareParty(sessionLogic.getCurrentHealthcarePartyId())?.let { hcp ->
				emitAll(
					sumehrLogicV1.createSumehr(
						it,
						info.secretForeignKeys,
						hcp,
						healthcarePartyV2Mapper.map(info.recipient!!),
						language,
						info.comment,
						info.excludedIds,
						info.includeIrrelevantInformation ?: false,
						null,
						mapServices(info.services),
						mapHealthElements(info.healthElements),
						Config(
							_kmehrId = System.currentTimeMillis().toString(),
							date = Utils.makeXGC(Instant.now().toEpochMilli())!!,
							time = Utils.makeXGC(Instant.now().toEpochMilli(), true)!!,
							soft = Config.Software(name = info.softwareName ?: "iCure", version = info.softwareVersion ?: kmehrConfiguration.kmehrVersion),
							clinicalSummaryType = "",
							defaultLanguage = "en",
							format = Config.Format.SUMEHR,
						),
					),
				)
			}
		} ?: throw IllegalArgumentException("Missing argument")
	}

	@Operation(summary = "Validate sumehr", responses = [ApiResponse(responseCode = "200", content = [Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE, schema = Schema(type = "string", format = "binary"))])])
	@PostMapping("/sumehr/{patientId}/validate", produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
	fun validateSumehr(
		@PathVariable patientId: String,
		@RequestParam language: String,
		@RequestBody info: SumehrExportInfoDto
	) = flow {
		patientLogic.getPatient(patientId)?.let {
			healthcarePartyLogic.getHealthcareParty(sessionLogic.getCurrentHealthcarePartyId())?.let { hcp ->
				emitAll(sumehrLogicV1.validateSumehr(
					it,
					info.secretForeignKeys,
					hcp,
					healthcarePartyV2Mapper.map(info.recipient!!),
					language,
					info.comment,
					info.excludedIds,
					info.includeIrrelevantInformation ?: false,
					null,
					mapServices(info.services),
					mapHealthElements(info.healthElements),
					Config(
						_kmehrId = System.currentTimeMillis().toString(),
						date = Utils.makeXGC(Instant.now().toEpochMilli())!!,
						time = Utils.makeXGC(Instant.now().toEpochMilli(), true)!!,
						soft = Config.Software(name = info.softwareName ?: "iCure", version = info.softwareVersion ?: kmehrConfiguration.kmehrVersion),
						clinicalSummaryType = "",
						defaultLanguage = "en",
						format = Config.Format.SUMEHR,
					)
				))
			}
		} ?: throw IllegalArgumentException("Missing argument")
	}.injectReactorContext()

	@Operation(summary = "Get sumehr elements")
	@PostMapping("/sumehr/{patientId}/content")
	fun getSumehrContent(
		@PathVariable patientId: String,
		@RequestBody info: SumehrExportInfoDto
	) = mono {
		SumehrContentDto().apply {
			services = sumehrLogicV1.getAllServices(
				sessionLogic.getCurrentHealthcarePartyId(),
				info.secretForeignKeys,
				info.excludedIds,
				info.includeIrrelevantInformation ?: false,
				null
			).stream().map { s -> serviceV2Mapper.map(s) }.collect(Collectors.toList())
			healthElements = sumehrLogicV1.getHealthElements(
				sessionLogic.getCurrentHealthcarePartyId(),
				info.secretForeignKeys,
				info.excludedIds,
				info.includeIrrelevantInformation ?: false
			).stream().map { h -> healthElementV2Mapper.map(h) }.collect(Collectors.toList())
		}
	}

	@Operation(summary = "Check sumehr signature")
	@PostMapping("/sumehr/{patientId}/md5")
	fun getSumehrMd5(
		@PathVariable patientId: String,
		@RequestBody info: SumehrExportInfoDto,
	) = mono {
		ContentDto(
			stringValue = patientLogic.getPatient(patientId)?.let {
				sumehrLogicV1.getSumehrMd5(
					sessionLogic.getCurrentHealthcarePartyId(),
					it,
					info.secretForeignKeys,
					info.excludedIds,
					info.includeIrrelevantInformation ?: false
				)
			}
		)
	}

	@Operation(summary = "Get sumehr validity")
	@PostMapping("/sumehr/{patientId}/valid")
	fun isSumehrValid(
		@PathVariable patientId: String,
		@RequestBody info: SumehrExportInfoDto,
	) = mono {
		SumehrValidityDto(sumehrValid = patientLogic.getPatient(patientId)?.let {
			sumehrLogicV1.isSumehrValid(
				sessionLogic.getCurrentHealthcarePartyId(),
				it,
				info.secretForeignKeys,
				info.excludedIds,
				false
			).name
		}?.let { SumehrStatus.valueOf(it) } ?: SumehrStatus.absent)
	}

	@Operation(summary = "Generate sumehr", responses = [ApiResponse(responseCode = "200", content = [Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE, schema = Schema(type = "string", format = "binary"))])])
	@PostMapping("/sumehrv2/{patientId}/export", produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
	fun generateSumehrV2(
		@PathVariable patientId: String,
		@RequestParam language: String,
		@RequestBody info: SumehrExportInfoDto
	) = flow {
		patientLogic.getPatient(patientId)?.let {
			val hcpId = sessionLogic.getCurrentHealthcarePartyId()
			healthcarePartyLogic.getHealthcareParty(hcpId)?.let { hcp ->
				emitAll(
					sumehrLogicV2.createSumehr(
						it,
						info.secretForeignKeys,
						hcp,
						healthcarePartyV2Mapper.map(info.recipient!!),
						language,
						info.comment,
						info.excludedIds,
						info.includeIrrelevantInformation ?: false,
						null,
						mapServices(info.services),
						mapHealthElements(info.healthElements),
						Config(
							_kmehrId = System.currentTimeMillis().toString(),
							date = Utils.makeXGC(Instant.now().toEpochMilli())!!,
							time = Utils.makeXGC(Instant.now().toEpochMilli(), true)!!,
							soft = Config.Software(name = info.softwareName ?: "iCure", version = info.softwareVersion ?: kmehrConfiguration.kmehrVersion),
							clinicalSummaryType = "",
							defaultLanguage = "en",
							format = Config.Format.SUMEHR,
						)
					)
				)
			}
		} ?: throw IllegalArgumentException("Missing argument")
	}.injectReactorContext()

	@Operation(summary = "Validate sumehr", responses = [ApiResponse(responseCode = "200", content = [Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE, schema = Schema(type = "string", format = "binary"))])])
	@PostMapping("/sumehrv2/{patientId}/validate", produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
	fun validateSumehrV2(
		@PathVariable patientId: String,
		@RequestParam language: String,
		@RequestBody info: SumehrExportInfoDto
	) = flow {
		patientLogic.getPatient(patientId)?.let {
			healthcarePartyLogic.getHealthcareParty(sessionLogic.getCurrentHealthcarePartyId())?.let { hcp ->
				emitAll(sumehrLogicV2.validateSumehr(
					it,
					info.secretForeignKeys,
					hcp,
					healthcarePartyV2Mapper.map(info.recipient!!),
					language,
					info.comment,
					info.excludedIds,
					info.includeIrrelevantInformation ?: false,
					null,
					mapServices(info.services),
					mapHealthElements(info.healthElements),
					Config(
						_kmehrId = System.currentTimeMillis().toString(),
						date = Utils.makeXGC(Instant.now().toEpochMilli())!!,
						time = Utils.makeXGC(Instant.now().toEpochMilli(), true)!!,
						soft = Config.Software(name = info.softwareName ?: "iCure", version = info.softwareVersion ?: kmehrConfiguration.kmehrVersion),
						clinicalSummaryType = "",
						defaultLanguage = "en",
						format = Config.Format.SUMEHR,
					),
				))
			}
		} ?: throw IllegalArgumentException("Missing argument")
	}.injectReactorContext()

	@Operation(summary = "Get sumehr elements")
	@PostMapping("/sumehrv2/{patientId}/content")
	fun getSumehrV2Content(
		@PathVariable patientId: String,
		@RequestBody info: SumehrExportInfoDto,
	) = mono {
		SumehrContentDto().apply {
			services = sumehrLogicV2.getAllServices(
				sessionLogic.getCurrentHealthcarePartyId(),
				info.secretForeignKeys,
				info.excludedIds,
				info.includeIrrelevantInformation ?: false,
				null
			).stream().map { s -> serviceV2Mapper.map(s) }.collect(Collectors.toList())
			healthElements = sumehrLogicV2.getHealthElements(
				sessionLogic.getCurrentHealthcarePartyId(),
				info.secretForeignKeys,
				info.excludedIds,
				info.includeIrrelevantInformation
					?: false,
			).stream().map { h -> healthElementV2Mapper.map(h) }.collect(Collectors.toList())
			patientHealthcareParties = sumehrLogicV2.getPatientHealthcareParties(
				info.excludedIds,
				patientId
			).map { h -> patientHealthCarePartyV2Mapper.map(h) }
			partnerships = sumehrLogicV2.getContactPeople(
				info.excludedIds,
				patientId
			).map { h -> partnershipV2Mapper.map(h) }
		}
	}

	@Operation(summary = "Check sumehr signature")
	@PostMapping("/sumehrv2/{patientId}/md5")
	fun getSumehrV2Md5(
		@PathVariable patientId: String,
		@RequestBody info: SumehrExportInfoDto,
	) = mono {
		ContentDto(
			stringValue = patientLogic.getPatient(patientId)?.let {
				sumehrLogicV2.getSumehrMd5(
					sessionLogic.getCurrentHealthcarePartyId(),
					it,
					info.secretForeignKeys,
					info.excludedIds,
					info.includeIrrelevantInformation ?: false,
				)
			}
		)
	}

	@Operation(summary = "Get sumehr validity")
	@PostMapping("/sumehrv2/{patientId}/valid")
	fun isSumehrV2Valid(
		@PathVariable patientId: String,
		@RequestBody info: SumehrExportInfoDto,
	) = mono {
		SumehrValidityDto(
			patientLogic.getPatient(patientId)
				?.let {
					sumehrLogicV2.isSumehrValid(
						sessionLogic.getCurrentHealthcarePartyId(),
						it,
						info.secretForeignKeys,
						info.excludedIds,
						info.includeIrrelevantInformation ?: false
					).name
				}
				?.let { SumehrStatus.valueOf(it) } ?: SumehrStatus.absent,
		)
	}

	@Operation(summary = "Get SMF (Software Medical File) export", responses = [ApiResponse(responseCode = "200", content = [Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE, schema = Schema(type = "string", format = "binary"))])])
	@PostMapping("/smf/{patientId}/export", produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
	fun generateSmfExport(
		@PathVariable patientId: String,
		@RequestParam language: String,
		@RequestBody smfExportParams: SoftwareMedicalFileExportDto
	) = mono {
		patientLogic.getPatient(patientId)
			?.let {
				healthcarePartyLogic.getHealthcareParty(sessionLogic.getCurrentHealthcarePartyId())
					?.let { it1 ->
						softwareMedicalFileLogic.createSmfExport(
							it,
							smfExportParams.secretForeignKeys,
							it1,
							language,
							null,
							null,
							Config(
								_kmehrId = System.currentTimeMillis().toString(),
								date = Utils.makeXGC(Instant.now().toEpochMilli())!!,
								time = Utils.makeXGC(Instant.now().toEpochMilli(), true)!!,
								soft = Config.Software(name = smfExportParams.softwareName ?: "iCure", version = smfExportParams.softwareVersion ?: kmehrConfiguration.kmehrVersion),
								clinicalSummaryType = "",
								defaultLanguage = "en",
								format = Config.Format.SMF,
							),
						)
					}
			} ?: throw IllegalArgumentException("Missing argument")
	}

	@Operation(summary = "Get KMEHR Patient Info export", responses = [ApiResponse(responseCode = "200", content = [Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE, schema = Schema(type = "string", format = "binary"))])])
	@PostMapping("/patientinfo/{patientId}/export", produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
	fun generatePatientInfoExport(@PathVariable patientId: String, @RequestParam language: String?) = flow {
		val userHealthCareParty = healthcarePartyLogic.getHealthcareParty(sessionLogic.getCurrentHealthcarePartyId())
		val patient = patientLogic.getPatient(patientId)
		patient?.let {
			userHealthCareParty?.let {
				emitAll(patientInfoFileLogic.createExport(patient, userHealthCareParty, language ?: "fr"))
			}
		} ?: throw IllegalArgumentException("Missing argument")
	}.injectReactorContext()

	@Operation(summary = "Get Medicationscheme export", responses = [ApiResponse(responseCode = "200", content = [Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE, schema = Schema(type = "string", format = "binary"))])])
	@PostMapping("/medicationscheme/{patientId}/export", produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
	fun generateMedicationSchemeExport(
		@PathVariable patientId: String,
		@RequestParam language: String,
		@RequestParam recipientSafe: String,
		@RequestParam version: Int,
		@RequestBody medicationSchemeExportParams: MedicationSchemeExportInfoDto
	) = flow {
		val userHealthCareParty = healthcarePartyLogic.getHealthcareParty(sessionLogic.getCurrentHealthcarePartyId())
		val patient = patientLogic.getPatient(patientId)

		patient?.let {
			userHealthCareParty?.let {
				if (medicationSchemeExportParams.services.isEmpty()) {
					emitAll(medicationSchemeLogic.createMedicationSchemeExport(
						patient,
						medicationSchemeExportParams.secretForeignKeys,
						userHealthCareParty,
						language,
						recipientSafe,
						version,
						null)
					)
				} else {
					emitAll(medicationSchemeLogic.createMedicationSchemeExport(
						patient,
						userHealthCareParty,
						language,
						recipientSafe,
						version,
						medicationSchemeExportParams.services.map { s -> serviceV2Mapper.map(s) },
						null,
						null)
					)
				}
			}
		} ?: throw IllegalArgumentException("Missing argument")
	}.injectReactorContext()

	@Operation(summary = "Get Incapacity export", responses = [ApiResponse(responseCode = "200", content = [Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE, schema = Schema(type = "string", format = "binary"))])])
	@PostMapping("/incapacity/{patientId}/export", produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
	fun generateIncapacityExport(
		@PathVariable patientId: String,
		@RequestParam language: String,
		@RequestHeader("X-Timezone-Offset") tz: String?,
		@RequestBody incapacityExportParams: IncapacityExportInfoDto
	) = flow {
		val userHealthCareParty = healthcarePartyLogic.getHealthcareParty(sessionLogic.getCurrentHealthcarePartyId())
		val patient = patientLogic.getPatient(patientId)

		patient?.let {
			userHealthCareParty?.let {
				emitAll(
					incapacityLogic.createIncapacityExport(
						patient = patient,
						sender = userHealthCareParty,
						language = language,
						exportInfo = incapacityExportInfoV2Mapper.map(incapacityExportParams),
						timeZone = tz
					)
				)
			}
		} ?: throw IllegalArgumentException("Missing argument")
	}.injectReactorContext()

	@Operation(summary = "Get Kmehr contactreport", responses = [ApiResponse(responseCode = "200", content = [Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE, schema = Schema(type = "string", format = "binary"))])])
	@PostMapping("/contactreport/{patientId}/export/{id}", consumes = [MediaType.APPLICATION_OCTET_STREAM_VALUE], produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
	fun generateContactreportExport(
		@PathVariable patientId: String,
		@PathVariable id: String,
		@RequestParam date: Long,
		@RequestParam language: String,
		@RequestParam recipientNihii: String,
		@RequestParam recipientSsin: String,
		@RequestParam recipientFirstName: String,
		@RequestParam recipientLastName: String,
		@RequestParam mimeType: String,
		@Schema(type = "string", format = "binary") @RequestBody document: ByteArray,
		response: ServerHttpResponse,
	) = flow {
		val userHealthCareParty = healthcarePartyLogic.getHealthcareParty(sessionLogic.getCurrentHealthcarePartyId())
		val patient = patientLogic.getPatient(patientId)

		if (userHealthCareParty != null && patient != null) {
			emitAll(kmehrNoteLogic.createNote(id, userHealthCareParty, date, recipientNihii, recipientSsin, recipientFirstName, recipientLastName, patient, language, "contactreport", mimeType, document))
		} else {
			throw IllegalArgumentException("Missing argument")
		}
	}.injectReactorContext()

	@Operation(summary = "Get Kmehr labresult", responses = [ApiResponse(responseCode = "200", content = [Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE, schema = Schema(type = "string", format = "binary"))])])
	@PostMapping("/labresult/{patientId}/export/{id}", consumes = [MediaType.APPLICATION_OCTET_STREAM_VALUE], produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
	fun generateLabresultExport(
		@PathVariable patientId: String,
		@PathVariable id: String,
		@RequestParam date: Long,
		@RequestParam language: String,
		@RequestParam recipientNihii: String,
		@RequestParam recipientSsin: String,
		@RequestParam recipientFirstName: String,
		@RequestParam recipientLastName: String,
		@RequestParam mimeType: String,
		@Schema(type = "string", format = "binary") @RequestBody document: ByteArray
	) = flow {
		val userHealthCareParty = healthcarePartyLogic.getHealthcareParty(sessionLogic.getCurrentHealthcarePartyId())
		val patient = patientLogic.getPatient(patientId)

		if (userHealthCareParty != null && patient != null) {
			emitAll(kmehrNoteLogic.createNote(id, userHealthCareParty, date, recipientNihii, recipientSsin, recipientFirstName, recipientLastName, patient, language, "labresult", mimeType, document))
		} else {
			throw IllegalArgumentException("Missing argument")
		}
	}.injectReactorContext()

	@Operation(summary = "Get Kmehr note", responses = [ApiResponse(responseCode = "200", content = [Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE, schema = Schema(type = "string", format = "binary"))])])
	@PostMapping("/note/{patientId}/export/{id}", consumes = [MediaType.APPLICATION_OCTET_STREAM_VALUE], produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
	fun generateNoteExport(
		@PathVariable patientId: String,
		@PathVariable id: String,
		@RequestParam date: Long,
		@RequestParam language: String,
		@RequestParam recipientNihii: String,
		@RequestParam recipientSsin: String,
		@RequestParam recipientFirstName: String,
		@RequestParam recipientLastName: String,
		@RequestParam mimeType: String,
		@Schema(type = "string", format = "binary") @RequestBody document: ByteArray
	) = flow {
		val userHealthCareParty = healthcarePartyLogic.getHealthcareParty(sessionLogic.getCurrentHealthcarePartyId())
		val patient = patientLogic.getPatient(patientId)
		if (userHealthCareParty != null && patient != null) {
			emitAll(kmehrNoteLogic.createNote(id, userHealthCareParty, date, recipientNihii, recipientSsin, recipientFirstName, recipientLastName, patient, language, "note", mimeType, document))
		} else {
			throw IllegalArgumentException("Missing argument")
		}
	}.injectReactorContext()

	@Operation(summary = "Get Kmehr prescription", responses = [ApiResponse(responseCode = "200", content = [Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE, schema = Schema(type = "string", format = "binary"))])])
	@PostMapping("/prescription/{patientId}/export/{id}", consumes = [MediaType.APPLICATION_OCTET_STREAM_VALUE], produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
	fun generatePrescriptionExport(
		@PathVariable patientId: String,
		@PathVariable id: String,
		@RequestParam date: Long,
		@RequestParam language: String,
		@RequestParam recipientNihii: String,
		@RequestParam recipientSsin: String,
		@RequestParam recipientFirstName: String,
		@RequestParam recipientLastName: String,
		@RequestParam mimeType: String,
		@Schema(type = "string", format = "binary") @RequestBody document: ByteArray
	) = flow {
		val userHealthCareParty = healthcarePartyLogic.getHealthcareParty(sessionLogic.getCurrentHealthcarePartyId())
		val patient = patientLogic.getPatient(patientId)
		if (userHealthCareParty != null && patient != null) {
			emitAll(kmehrNoteLogic.createNote(id, userHealthCareParty, date, recipientNihii, recipientSsin, recipientFirstName, recipientLastName, patient, language, "prescription", mimeType, document))
		} else {
			throw IllegalArgumentException("Missing argument")
		}
	}.injectReactorContext()

	@Operation(summary = "Get Kmehr report", responses = [ApiResponse(responseCode = "200", content = [Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE, schema = Schema(type = "string", format = "binary"))])])
	@PostMapping("/report/{patientId}/export/{id}", consumes = [MediaType.APPLICATION_OCTET_STREAM_VALUE], produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
	fun generateReportExport(
		@PathVariable patientId: String,
		@PathVariable id: String,
		@RequestParam date: Long,
		@RequestParam language: String,
		@RequestParam recipientNihii: String,
		@RequestParam recipientSsin: String,
		@RequestParam recipientFirstName: String,
		@RequestParam recipientLastName: String,
		@RequestParam mimeType: String,
		@Schema(type = "string", format = "binary") @RequestBody document: ByteArray
	) = flow {
		val userHealthCareParty = healthcarePartyLogic.getHealthcareParty(sessionLogic.getCurrentHealthcarePartyId())
		val patient = patientLogic.getPatient(patientId)
		if (userHealthCareParty != null && patient != null) {
			emitAll(kmehrNoteLogic.createNote(id, userHealthCareParty, date, recipientNihii, recipientSsin, recipientFirstName, recipientLastName, patient, language, "report", mimeType, document))
		} else {
			throw IllegalArgumentException("Missing argument")
		}
	}.injectReactorContext()

	@Operation(summary = "Get Kmehr request", responses = [ApiResponse(responseCode = "200", content = [Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE, schema = Schema(type = "string", format = "binary"))])])
	@PostMapping("/request/{patientId}/export/{id}", consumes = [MediaType.APPLICATION_OCTET_STREAM_VALUE], produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
	fun generateRequestExport(
		@PathVariable patientId: String,
		@PathVariable id: String,
		@RequestParam date: Long,
		@RequestParam language: String,
		@RequestParam recipientNihii: String,
		@RequestParam recipientSsin: String,
		@RequestParam recipientFirstName: String,
		@RequestParam recipientLastName: String,
		@RequestParam mimeType: String,
		@Schema(type = "string", format = "binary") @RequestBody document: ByteArray
	) = flow {
		val userHealthCareParty = healthcarePartyLogic.getHealthcareParty(sessionLogic.getCurrentHealthcarePartyId())
		val patient = patientLogic.getPatient(patientId)
		if (userHealthCareParty != null && patient != null) {
			emitAll(kmehrNoteLogic.createNote(id, userHealthCareParty, date, recipientNihii, recipientSsin, recipientFirstName, recipientLastName, patient, language, "request", mimeType, document))
		} else {
			throw IllegalArgumentException("Missing argument")
		}
	}.injectReactorContext()

	@Operation(summary = "Get Kmehr result", responses = [ApiResponse(responseCode = "200", content = [Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE, schema = Schema(type = "string", format = "binary"))])])
	@PostMapping("/result/{patientId}/export/{id}", consumes = [MediaType.APPLICATION_OCTET_STREAM_VALUE], produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
	fun generateResultExport(
		@PathVariable patientId: String,
		@PathVariable id: String,
		@RequestParam date: Long,
		@RequestParam language: String,
		@RequestParam recipientNihii: String,
		@RequestParam recipientSsin: String,
		@RequestParam recipientFirstName: String,
		@RequestParam recipientLastName: String,
		@RequestParam mimeType: String,
		@Schema(type = "string", format = "binary") @RequestBody document: ByteArray
	) = flow {
		val userHealthCareParty = healthcarePartyLogic.getHealthcareParty(sessionLogic.getCurrentHealthcarePartyId())
		val patient = patientLogic.getPatient(patientId)
		if (userHealthCareParty != null && patient != null) {
			emitAll(kmehrNoteLogic.createNote(id, userHealthCareParty, date, recipientNihii, recipientSsin, recipientFirstName, recipientLastName, patient, language, "result", mimeType, document))
		} else {
			throw IllegalArgumentException("Missing argument")
		}
	}.injectReactorContext()

	@Operation(summary = "Import SMF into patient(s) using existing document")
	@PostMapping("/smf/{documentId}/import")
	fun importSmf(
		@PathVariable documentId: String,
		@RequestParam(required = false) documentKey: String?,
		@RequestParam(required = false) patientId: String?,
		@RequestParam(required = false) language: String?,
		@RequestParam(required = false) dryRun: Boolean?,
		@RequestBody(required = false) mappings: HashMap<String, List<ImportMapping>>?,
	) = mono {
		val userHealthCareParty = healthcarePartyLogic.getHealthcareParty(sessionLogic.getCurrentHealthcarePartyId())
		val document = documentLogic.getDocument(documentId)
		val attachment = document?.let { documentLogic.getAndDecryptMainAttachment(it.id, documentKey) }
		val user = userLogic.getUser(sessionLogic.getCurrentUserId(), false) ?: throw NotFoundRequestException("User not found")

		attachment?.let {
			softwareMedicalFileLogic.importSmfFile(
				it,
				user,
				language ?: userHealthCareParty?.languages?.firstOrNull() ?: "fr",
				dryRun ?: false,
				patientId?.let { patientLogic.getPatient(patientId) },
				mappings ?: HashMap(),
			)
		}?.map { importResultV2Mapper.map(it) }
	}

	@Operation(summary = "Check whether patients in SMF already exists in DB")
	@PostMapping("/smf/{documentId}/checkIfSMFPatientsExists")
	fun checkIfSMFPatientsExists(
		@PathVariable documentId: String,
		@RequestParam(required = false) documentKey: String?,
		@RequestParam(required = false) patientId: String?,
	) = mono {
		val document = documentLogic.getDocument(documentId)

		val attachmentId = document?.attachmentId
		val user = userLogic.getUser(sessionLogic.getCurrentUserId(), false) ?: throw NotFoundRequestException("User not found")

		attachmentId?.let {
			softwareMedicalFileLogic.checkIfSMFPatientsExists(
				documentLogic.getMainAttachment(documentId).map(DataBuffer::asByteBuffer),
				user,
				patientId?.let { patientLogic.getPatient(patientId) }
			)
		}
	}

	@Operation(summary = "Import sumehr into patient(s) using existing document")
	@PostMapping("/sumehr/{documentId}/import")
	fun importSumehr(
		@PathVariable documentId: String,
		@Parameter(description = "Dry run: do not save in database")
		@RequestParam(required = false)
		dryRun: Boolean?,
		@RequestParam(required = false) patientId: String?,
		@RequestParam(required = false) language: String?,
		@RequestBody(required = false) mappings: HashMap<String, List<ImportMapping>>?,
	) = mono {
		val userHealthCareParty = healthcarePartyLogic.getHealthcareParty(sessionLogic.getCurrentHealthcarePartyId())
		val document = documentLogic.getDocument(documentId)

		val attachmentId = document?.attachmentId
		val user = userLogic.getUser(sessionLogic.getCurrentUserId(), false) ?: throw NotFoundRequestException("User not found")

		attachmentId?.let {
			sumehrLogicV1.importSumehr(
				documentLogic.getMainAttachment(documentId).map(DataBuffer::asByteBuffer),
				user,
				language ?: userHealthCareParty?.languages?.firstOrNull() ?: "fr",
				patientId?.let { patientLogic.getPatient(patientId) },
				mappings ?: emptyMap(),
				dryRun != true
			)
		}?.map { importResultV2Mapper.map(it) }
	}

	@Operation(summary = "Import sumehr into patient(s) using existing document")
	@PostMapping("/sumehr/{documentId}/importbyitemid")
	fun importSumehrByItemId(
		@PathVariable documentId: String,
		@RequestParam itemId: String,
		@Parameter(description = "Dry run: do not save in database")
		@RequestParam(required = false)
		dryRun: Boolean?,
		@RequestParam(required = false) patientId: String?,
		@RequestParam(required = false) language: String?,
		@RequestBody(required = false) mappings: HashMap<String, List<ImportMapping>>?,
	) = mono {
		val userHealthCareParty = healthcarePartyLogic.getHealthcareParty(sessionLogic.getCurrentHealthcarePartyId())
		val document = documentLogic.getDocument(documentId)

		val attachmentId = document?.attachmentId
		val user = userLogic.getUser(sessionLogic.getCurrentUserId(), false) ?: throw NotFoundRequestException("User not found")
		attachmentId?.let {
			sumehrLogicV2.importSumehrByItemId(
				documentLogic.getMainAttachment(documentId).map(DataBuffer::asByteBuffer),
				itemId,
				user,
				language ?: userHealthCareParty?.languages?.firstOrNull() ?: "fr",
				patientId?.let { patientLogic.getPatient(patientId) },
				mappings ?: HashMap(),
				dryRun != true,
			)
		}?.map { importResultV2Mapper.map(it) }
	}

	@Operation(summary = "Import MedicationScheme into patient(s) using existing document")
	@PostMapping("/medicationscheme/{documentId}/import")
	fun importMedicationScheme(
		@PathVariable documentId: String,
		@Parameter(description = "Dry run: do not save in database")
		@RequestParam(required = false)
		dryRun: Boolean?,
		@RequestParam(required = false) patientId: String?,
		@RequestParam(required = false) language: String?,
		@RequestBody(required = false) mappings: HashMap<String, List<ImportMapping>>?
	) = mono {
		val userHealthCareParty = healthcarePartyLogic.getHealthcareParty(sessionLogic.getCurrentHealthcarePartyId())
		val document = documentLogic.getDocument(documentId)

		val attachmentId = document?.attachmentId
		val user = userLogic.getUser(sessionLogic.getCurrentUserId(), false) ?: throw NotFoundRequestException("User not found")
		attachmentId?.let {
			medicationSchemeLogic.importMedicationSchemeFile(
				documentLogic.getMainAttachment(documentId).map(DataBuffer::asByteBuffer),
				user,
				language
					?: userHealthCareParty?.languages?.firstOrNull() ?: "fr",
				patientId?.let { patientLogic.getPatient(patientId) },
				mappings ?: HashMap(),
				dryRun != true,
			)
		}?.map { importResultV2Mapper.map(it) }
	}

	private fun mapServices(services: List<ServiceDto>?) =
		services?.map { s -> serviceV2Mapper.map(s) }

	private fun mapHealthElements(healthElements: List<HealthElementDto>?) =
		healthElements?.map { s -> healthElementV2Mapper.map(s) }
}
