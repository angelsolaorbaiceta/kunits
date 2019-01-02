package sola.angel.kunits

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LengthConversionTest {

    private var engine = UnitConversionEngine()

    @Test
    fun `convert from cm to m`() {
        assertEquals(
            2.5,
            engine.convert(250.0, "cm", "m")
        )
    }
}