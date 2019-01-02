package sola.angel.kunits

internal class UnitExpander(
    private val multiplierSeparator: String = "·",
    private val dividerSeparator: String = "/"
) {

    private val unitNameRegex = Regex("[A-Za-z]+")
    private val unitExponentRegex = Regex("[0-9]+")

    /**
     * Produces the numerator/denominator expansion of the given units.
     *
     * For example, an input of "N/m2" would yield:
     *  - numerator: ["N"]
     *  - denominator: ["m", "m"]
     */
    fun expand(units: String): UnitExpansion {
        val parts = units.replace(" ", "").split(dividerSeparator)
        val numeratorProduct = parts[0]
        val denominatorProduct = if (parts.size < 2) "" else parts[1]

        return UnitExpansion(
            numerator = expandProduct(numeratorProduct),
            denominator = expandProduct(denominatorProduct)
        )
    }

    private fun expandProduct(unitProduct: String): List<String> {
        if (unitProduct.isEmpty()) return emptyList()

        return unitProduct
            .split(multiplierSeparator)
            .flatMap { expandUnitTimes(it) }
    }

    private fun expandUnitTimes(unit: String): Iterable<String> {
        val unitName = unitNameRegex.find(unit)!!.value
        val unitExp = unitExponent(unit)
        return IntRange(1, unitExp).map { unitName }
    }

    private fun unitExponent(unit: String): Int {
        val exponentMatch = unitExponentRegex.find(unit)
        return if (exponentMatch != null) Integer.parseInt(exponentMatch.value) else 1
    }
}