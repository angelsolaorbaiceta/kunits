package sola.angel.kunits

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ConversionTest {

    private val errorEpsilon = 1E-5
    private val engine = UnitConversionEngine()

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