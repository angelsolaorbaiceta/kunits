package sola.angel.kunits.config

internal data class SystemFactors(
    val systemName: String,
    val factors: Map<String, Double>
) {
    fun hasUnitNamed(name: String): Boolean = factors.containsKey(name)
}