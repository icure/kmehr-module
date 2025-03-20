package org.taktik.icure.be.ehealth.logic.kmehr.v20170901

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.taktik.icure.be.ehealth.dto.kmehr.v20170901.be.fgov.ehealth.standards.kmehr.cd.v1.CDLIFECYCLEvalues
import org.taktik.icure.config.KmehrConfiguration
import org.taktik.icure.entities.Contact
import org.taktik.icure.entities.HealthElement
import org.taktik.icure.entities.base.CodeStub
import org.taktik.icure.entities.embed.Service
import org.taktik.icure.test.makeFuzzyLongFromMomentType
import org.taktik.icure.test.uuid

class KmehrExportTest : StringSpec({

	val kmehrConfig = mockk<KmehrConfiguration> {
		every { kmehrVersion } returns "0.0.0-test"
	}
	val kmehrExport = KmehrExport(
		codeLogic = mockk(),
		documentLogic = mockk(),
		kmehrConfig = kmehrConfig
	)

	"In an item created from an HealthElement, the opening date should be used first, then the value date" {
		val he1 = HealthElement(
			id = uuid(),
			openingDate = 20250320101010
		)
		val item1 = kmehrExport.createItemWithContent(
			he = he1,
			idx = 0,
			cdItem = "something",
			contents = emptyList(),
			localIdName = "iCure-Service",
		).shouldNotBeNull()
		makeFuzzyLongFromMomentType(item1.beginmoment.shouldNotBeNull()) shouldBe he1.openingDate.shouldNotBeNull()

		val he2 = HealthElement(
			id = uuid(),
			valueDate = 20250320101010
		)
		val item2 = kmehrExport.createItemWithContent(
			he = he2,
			idx = 0,
			cdItem = "something",
			contents = emptyList(),
			localIdName = "iCure-Service",
		).shouldNotBeNull()
		makeFuzzyLongFromMomentType(item2.beginmoment.shouldNotBeNull()) shouldBe he2.valueDate.shouldNotBeNull()

		val he3 = HealthElement(id = uuid())
		val item3 = kmehrExport.createItemWithContent(
			he = he3,
			idx = 0,
			cdItem = "something",
			contents = emptyList(),
			localIdName = "iCure-Service",
		).shouldNotBeNull()
		item3.beginmoment shouldBe null
	}

	"A service with an irrelevant status should be exported with an inactive lifecycle" {
		val service = Service(status = 2)
		val contact = Contact(id = uuid())
		val item = kmehrExport.createItemWithContent(
			svc = service,
			ctc = contact,
			idx = 0,
			cdItem = "something",
			contents = emptyList(),
			localIdName = "iCure-Service",
			mfId = null
		).shouldNotBeNull()
		item.lifecycle.cd.value shouldBe CDLIFECYCLEvalues.INACTIVE
	}

	"A service with a past closing date should be exported with an inactive lifecycle" {
		val service = Service(closingDate = 19700101101010)
		val contact = Contact(id = uuid())
		val item = kmehrExport.createItemWithContent(
			svc = service,
			ctc = contact,
			idx = 0,
			cdItem = "something",
			contents = emptyList(),
			localIdName = "iCure-Service",
			mfId = null
		).shouldNotBeNull()
		item.lifecycle.cd.value shouldBe CDLIFECYCLEvalues.INACTIVE
	}

	"A relevant service with a valid lifecycle tag should be exported with that tag" {
		val service = Service(tags = setOf(CodeStub(id = "CD-LIFECYCLE|todo|1", type = "CD-LIFECYCLE", code = "todo", version = "1")))
		val contact = Contact(id = uuid())
		val item = kmehrExport.createItemWithContent(
			svc = service,
			ctc = contact,
			idx = 0,
			cdItem = "something",
			contents = emptyList(),
			localIdName = "iCure-Service",
			mfId = null
		).shouldNotBeNull()
		item.lifecycle.cd.value shouldBe CDLIFECYCLEvalues.TODO
	}

	"A relevant service with a non-valid lifecycle tag should not be exported" {
		val service = Service(tags = setOf(CodeStub(id = "CD-LIFECYCLE|todo|1", type = "CD-LIFECYCLE", code = "todo", version = "1")))
		val contact = Contact(id = uuid())
		kmehrExport.createItemWithContent(
			svc = service,
			ctc = contact,
			idx = 0,
			cdItem = "something",
			contents = emptyList(),
			localIdName = "iCure-Service",
			mfId = null
		)
	}

	"A relevant service without a lifecycle tag and with a cdItem `medication` should be exported with a prescribed lifecycle" {
		val service = Service()
		val contact = Contact(id = uuid())
		val item = kmehrExport.createItemWithContent(
			svc = service,
			ctc = contact,
			idx = 0,
			cdItem = "medication",
			contents = emptyList(),
			localIdName = "iCure-Service",
			mfId = null
		).shouldNotBeNull()
		item.lifecycle.cd.value shouldBe CDLIFECYCLEvalues.PRESCRIBED
	}

	"A relevant service without a lifecycle tag and with a generic cdItem should be exported with an active lifecycle" {
		val service = Service()
		val contact = Contact(id = uuid())
		val item = kmehrExport.createItemWithContent(
			svc = service,
			ctc = contact,
			idx = 0,
			cdItem = "something",
			contents = emptyList(),
			localIdName = "iCure-Service",
			mfId = null
		).shouldNotBeNull()
		item.lifecycle.cd.value shouldBe CDLIFECYCLEvalues.ACTIVE
	}

})