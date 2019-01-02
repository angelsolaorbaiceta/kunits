package sola.angel.kunits

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class UnitExpanderTest {

    private val expander = UnitExpander()

    @Test
    fun `expand simple unit in numerator`() {
        val expansion = expander.expand("m")

        assertEquals(
            listOf("m"),
            expansion.numerator
        )
        assertTrue(
            expansion.denominator.isEmpty()
        )
    }

    @Test
    fun `expand simple unit in denominator`() {
        val expansion = expander.expand("/m")

        assertTrue(
            expansion.numerator.isEmpty()
        )
        assertEquals(
            listOf("m"),
            expansion.denominator
        )
    }

    @Test
    fun `expand squared units`() {
        assertEquals(
            listOf("m", "m"),
            expander.expand("m2").numerator
        )
    }

    @Test
    fun `expand compound units`() {
        val expansion = expander.expand("kg/m2")

        assertEquals(
            listOf("kg"),
            expansion.numerator
        )
        assertEquals(
            listOf("m", "m"),
            expansion.denominator
        )
    }
}