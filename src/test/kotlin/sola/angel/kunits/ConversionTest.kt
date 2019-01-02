package sola.angel.kunits

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ConversionTest {

    private val errorEpsilon = 1E-5
    private val engine = UnitConversionEngine()

    @Test
    fun `cannot convert unknown units`() {
        assertFalse(
            engine.canConvert("N", "foo")
        )
    }

    @Test
    fun `cannot convert from units from different groups`() {
        assertFalse(
            engine.canConvert("N", "cm")
        )
    }

    @Test
    fun `can convert units of same group but different systems`() {
        assertTrue(
            engine.canConvert("cm", "ft")
        )
    }

    @Test
    fun `convert from cm to m`() {
        assertEquals(
            2.5,
            engine.convert(250.0, "cm", "m"),
            errorEpsilon
        )
    }

    @Test
    fun `convert from cm2 to m2`() {
        assertEquals(
            0.0025,
            engine.convert(25.0, "cm2", "m2"),
            errorEpsilon
        )
    }

    @Test
    fun `convert from m to in`() {
        assertEquals(
            98.42519685,
            engine.convert(2.5, "m", "in"),
            errorEpsilon
        )
    }
}