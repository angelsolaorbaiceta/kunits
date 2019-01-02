package sola.angel.kunits.config

import org.json.JSONArray
import org.json.JSONObject
import sola.angel.kunits.UnitConversionEngine

internal class ConfigReader {

    private var unitGroupsCache: Set<UnitGroup>? = null

    fun parse(): Set<UnitGroup> {
        if (unitGroupsCache == null) computeUnitGroups()
        return unitGroupsCache!!
    }

    private fun computeUnitGroups() {
        unitGroupsCache = conversionsJSON
            .keySet()
            .map { key -> Pair(key, conversionsJSON.getJSONObject(key)) }
            .map { keyJsonPair -> unitGroupFrom(keyJsonPair.first, keyJsonPair.second) }
            .toSet()
    }

    private fun unitGroupFrom(name: String, json: JSONObject): UnitGroup {
        val systems = json.getJSONArray(systemsKey)
        val systemConversions = json.getJSONArray(systemFactorsKey)

        return UnitGroup(
            name = name,
            systems = unitSystemsFrom(systems),
            systemConversion = systemConversionsFrom(systemConversions)
        )
    }

    private fun unitSystemsFrom(systemsJson: JSONArray): Set<SystemFactors> {
        return systemsJson
            .map { system ->
                val systemJson = system as JSONObject
                SystemFactors(
                    systemName = systemJson.getString(nameKey),
                    factors = factorsFrom(systemJson.getJSONObject(factorsKey))
                )
            }
            .toSet()
    }

    private fun factorsFrom(factorsJson: JSONObject): Map<String, Double> {
        return factorsJson
            .keySet()
            .map { key -> key to factorsJson.getDouble(key) }
            .toMap()
    }

    private fun systemConversionsFrom(json: JSONArray): Set<SystemsConversion> {
        return emptySet()
    }

    companion object {
        private const val systemsKey = "systems"
        private const val nameKey = "name"
        private const val factorsKey = "factors"
        private const val systemFactorsKey = "system_conversion_factors"

        private val conversionsJSON: JSONObject by lazy {
            JSONObject(
                UnitConversionEngine::class.java.getResource("/conversion.json").readText()
            )
        }
    }
}