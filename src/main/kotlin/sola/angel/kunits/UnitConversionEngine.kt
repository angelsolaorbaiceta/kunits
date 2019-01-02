package sola.angel.kunits

import sola.angel.kunits.config.ConfigReader
import sola.angel.kunits.config.UnitGroup

class UnitConversionEngine {

    private val unitGroups: Set<UnitGroup> = ConfigReader().parse()
    private val expander = UnitExpander()
    private val factorsCache: MutableMap<String, Double> = mutableMapOf()

    fun convert(amount: Double, from: String, to: String): Double {
        val sourceUnitsExpansion = expander.expand(from)
        val targetUnitsExpansion = expander.expand(to)

        if (!canConvert(sourceUnitsExpansion, targetUnitsExpansion)) {
            throw ConversionException(sourceUnitsExpansion, targetUnitsExpansion)
        }

        return amount * compoundFactor(sourceUnitsExpansion, targetUnitsExpansion)
    }

    fun canConvert(from: String, to: String): Boolean =
        canConvert(
            expander.expand(from),
            expander.expand(to)
        )

    private fun canConvert(from: UnitExpansion, to: UnitExpansion): Boolean {
        // TODO
        return true
    }

    private fun compoundFactor(sourceUnits: UnitExpansion, targetUnits: UnitExpansion): Double {
        val cacheKey = "$sourceUnits -> $targetUnits"
        if (factorsCache.containsKey(cacheKey)) {
            return factorsCache[cacheKey]!!
        }

        val numeratorFactor = reduceConversionFactor(sourceUnits.numerator, targetUnits.numerator)
        val denominatorFactor = reduceConversionFactor(sourceUnits.denominator, targetUnits.denominator)
        val factor = numeratorFactor / denominatorFactor

        factorsCache[cacheKey] = factor
        return factor
    }

    private fun reduceConversionFactor(sourceUnits: Iterable<String>, targetUnits: Iterable<String>): Double =
        sourceUnits.zip(targetUnits)
            .map { simpleConversionFactor(it.first, it.second) }
            .fold(1.0) { acc, factor -> acc * factor }

    private fun simpleConversionFactor(sourceUnits: String, targetUnits: String): Double =
        unitGroups
            .find { it.hasUnitNamed(sourceUnits) }!!
            .conversionFactor(sourceUnits, targetUnits)
}