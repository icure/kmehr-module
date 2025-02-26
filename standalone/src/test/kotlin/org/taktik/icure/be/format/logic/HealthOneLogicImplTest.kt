package org.taktik.icure.be.format.logic

import java.io.BufferedReader
import java.io.StringReader
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.mockk
import org.taktik.icure.be.format.logic.impl.HealthOneLogicImpl

class HealthOneLogicImplTest : StringSpec({
	val logic = HealthOneLogicImpl(mockk(), mockk(), mockk(), mockk())
	fun resetMocks() {
		clearAllMocks()
	}

	beforeEach {
		resetMocks()
	}

	"CSM-474 - parse file with 2 opmerking comment lines" {
		val rawReport = """
A1\00820035771\CENTRUM VOOR MEDISCHE ANALYSE\
A2\00820035771\ALISE                   \KINKARRON                \F\24102142\
A3\00820035771\BINGTOWN 1            \1234\KINKARRON                \
A4\00820035771\MEDISPRING                     \03012025\\C\
A5\00820035771\0/000\123456 789 12            \1\ALISE, KINKARRON                  \     /     \
L1\00820035771\\HEMATOLOGIE\\\\\
L1\00820035771\EDTA    \Opmerking                \     -    \\\De EDTA-tube was onvoldoende gevuld; dit kan de
L1\00820035771\EDTA    \Opmerking                \     -    \\\resultaten hematologie beinvloeden.
L1\00820035771\BEZ1    \Bezinking na 1 uur       \          -20        \mm             \ \2         \
END
		"""
		val parsed = logic.parseReportsAndLabs("fr", listOf("00820035771"), BufferedReader(StringReader(rawReport)))
		parsed.first().services[0].content["fr"]?.stringValue shouldBe " "
		parsed.first().services[1].content["fr"]?.stringValue shouldBe "De EDTA-tube was onvoldoende gevuld; dit kan de\nresultaten hematologie beinvloeden."
		parsed.first().services[2].content["fr"]?.measureValue?.value shouldBe 2.0
	}

	"parse simple file" {
		val parsed = logic.parseReportsAndLabs("fr", listOf("001/04248757"), BufferedReader(StringReader("""A1\  001/04248757\CENTRE HOSPITALIER REGIONAL, NAMUR\
A2\  001/04248757\JOCQU…\SAM\M\21021972\
A3\  001/04248757\FOND DE MALONNE, 48\5020\MALONNE\
A4\  001/04248757\MARCHAND Pierre-Michel\10112022\\C\
L1\  001/04248757\\HEMATOLOGIE\\\\\
L1\  001/04248757\11504\  TESTA\14-60\ng/mL\--\<0,5\
END
""")))
		parsed.size shouldBe 1
		parsed.first().services.size shouldBe 2
		parsed.first().services[0].content["fr"]?.stringValue shouldBe " "
		parsed.first().services[1].content["fr"]?.measureValue?.value shouldBe 0.5
		parsed.first().services[1].content["fr"]?.measureValue?.sign shouldBe "<"
	}

	"parse multiline without sign" {
		val parsed = logic.parseReportsAndLabs("fr", listOf("001/04248757"), BufferedReader(StringReader("""A1\  001/04248757\CENTRE HOSPITALIER REGIONAL, NAMUR\
A2\  001/04248757\JOCQU…\SAM\M\21021972\
A3\  001/04248757\FOND DE MALONNE, 48\5020\MALONNE\
A4\  001/04248757\MARCHAND Pierre-Michel\10112022\\C\
L1\  001/04248757\\HEMATOLOGIE\\\\\
L1\  001/04248757\11504\  TESTA\14-60\ng/mL\--\0,5\
L1\  001/04248757\11504\  TESTA\\\\some text\
END
""")))
		parsed.size shouldBe 1
		parsed.first().services.size shouldBe 2
		parsed.first().services[0].content["fr"]?.stringValue shouldBe " "
		parsed.first().services[1].content["fr"]?.measureValue?.value shouldBe 0.5
		parsed.first().services[1].content["fr"]?.measureValue?.sign shouldBe null
	}

	"parse multiline with sign" {
		val parsed = logic.parseReportsAndLabs("fr", listOf("001/04248757"), BufferedReader(StringReader("""A1\  001/04248757\CENTRE HOSPITALIER REGIONAL, NAMUR\
A2\  001/04248757\JOCQU…\SAM\M\21021972\
A3\  001/04248757\FOND DE MALONNE, 48\5020\MALONNE\
A4\  001/04248757\MARCHAND Pierre-Michel\10112022\\C\
L1\  001/04248757\\HEMATOLOGIE\\\\\
L1\  001/04248757\11504\  TESTA\14-60\ng/mL\--\<0,5\
L1\  001/04248757\11504\  TESTA\\\\some text\
END
""")))
		parsed.size shouldBe 1
		parsed.first().services.size shouldBe 2
		parsed.first().services[0].content["fr"]?.stringValue shouldBe " "
		parsed.first().services[1].content["fr"]?.measureValue?.value shouldBe 0.5
		parsed.first().services[1].content["fr"]?.measureValue?.sign shouldBe "<"
		parsed.first().services[1].content["fr"]?.measureValue?.comment shouldBe "some text"
	}

})
