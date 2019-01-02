package sola.angel.kunits

import sola.angel.kunits.config.ConfigReader
import sola.angel.kunits.config.UnitGroup

class UnitConversionEngine {

    private val unitGroups: Set<UnitGroup> = ConfigReader().parse()

    fun convert(amount: Double, from: String, to: String): Double {
        return amount
    }
}