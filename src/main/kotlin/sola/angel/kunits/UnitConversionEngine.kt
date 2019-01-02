package sola.angel.kunits

import sola.angel.kunits.config.ConfigReader
import sola.angel.kunits.config.UnitGroup

class UnitConversionEngine {

    private val unitGroups: Set<UnitGroup> = ConfigReader().parse()
    private val expander = UnitExpander()
    private val canConvertCache: MutableMap<String, Boolean> = mutableMapOf()
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

    private fun canConvert(source: UnitExpansion, target: UnitExpansion): Boolean {
        val cacheKey = makeCacheKey(source, target)
        if (canConvertCache.containsKey(cacheKey)) {
            return canConvertCache[cacheKey]!!
        }

        val srcNumUnits = source.numerator.map { groupForUnitNamed(it) }
        val srcDenUnits = source.denominator.map { groupForUnitNamed(it) }
        val tgtNumUnits = target.numerator.map { groupForUnitNamed(it) }
        val tgtDenUnits = target.denominator.map { groupForUnitNamed(it) }

        if (srcNumUnits.contains(null) || srcDenUnits.contains(null) ||
            tgtNumUnits.contains(null) || tgtDenUnits.contains(null)
        ) {
            return false
        }

        val canConvert = srcNumUnits == tgtNumUnits && srcDenUnits == tgtDenUnits
        canConvertCache[cacheKey] = canConvert
        return canConvert
    }

    private fun compoundFactor(sourceUnits: UnitExpansion, targetUnits: UnitExpansion): Double {
        val cacheKey = makeCacheKey(sourceUnits, targetUnits)
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
        groupForUnitNamed(sourceUnits)!!.conversionFactor(sourceUnits, targetUnits)

    private fun groupForUnitNamed(name: String): UnitGroup? = unitGroups.find { it.hasUnitNamed(name) }

    private fun makeCacheKey(source: UnitExpansion, target: UnitExpansion): String = "$source -> $target"
}