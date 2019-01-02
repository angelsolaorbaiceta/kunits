package sola.angel.kunits.config

internal data class UnitGroup(
    val name: String,
    val systems: Set<SystemFactors>,
    val systemConversion: Set<SystemsConversion>
) {
    fun hasUnitNamed(name: String): Boolean =
        systems.any { system -> system.hasUnitNamed(name) }

    fun conversionFactor(source: String, target: String): Double {
        val sourceSystem = systems.find { it.hasUnitNamed(source) }!!
        val targetSystem = systems.find { it.hasUnitNamed(target) }!!

        val factorToBase = sourceSystem.factors[source]!!
        val factorFromBase = 1.0 / targetSystem.factors[target]!!

        return if (sourceSystem == targetSystem) {
            factorToBase * factorFromBase
        } else {
            factorToBase * factorFromBase * systemsConversionFactor(sourceSystem, targetSystem)
        }
    }

    private fun systemsConversionFactor(source: SystemFactors, target: SystemFactors): Double =
        systemConversion.find { it.converts(source.systemName, target.systemName) }!!.factor
}