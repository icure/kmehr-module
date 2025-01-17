package org.taktik.icure.be.ehealth.logic.kmehr.incapacity.impl.v20170601

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.toList
import org.junit.jupiter.api.TestInstance
import org.taktik.icure.be.ehealth.logic.kmehr.incapacity.IncapacityLogic
import org.taktik.icure.domain.be.kmehr.IncapacityExportInfo
import org.taktik.icure.entities.HealthcareParty
import org.taktik.icure.entities.Patient
import org.taktik.icure.test.BaseKmehrTest
import org.taktik.icure.test.combineToString
import org.taktik.icure.test.uuid

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IncapacityExportTest(
	private val incapacityLogic: IncapacityLogic,
) : BaseKmehrTest() {

	init {
		incapacityExportTest()
	}

	private fun StringSpec.incapacityExportTest() {

		"CSM-445 - Incapacity export should have the correct software name and version" {
			val softwareName = "test-software"
			val softwareVersion = "test-version"
			val xml = incapacityLogic.createIncapacityExport(
				patient = Patient(id = uuid()),
				sender = HealthcareParty(id = uuid()),
				language = "en-US",
				exportInfo = IncapacityExportInfo(
					incapacityreason = "accident",
					softwareName = softwareName,
					softwareVersion = softwareVersion
				),
				timeZone = null
			).toList().combineToString()
			val folderMatches = Regex("<id S=\"LOCAL\" SV=\"$softwareVersion\" SL=\"$softwareName\">$softwareName-$softwareVersion</id>").findAll(xml).toList()
			folderMatches.size shouldBe 1
		}

	}

}