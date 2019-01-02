package sola.angel.kunits

internal data class UnitExpansion(
    val numerator: List<String>,
    val denominator: List<String>
) {
    override fun toString(): String {
        val num = numerator.joinToString("·")
        val den = denominator.joinToString("·")
        return "$num/$den"
    }
}