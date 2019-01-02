package sola.angel.kunits.config

internal class SystemsConversion(
    val from: String,
    val to: String,
    val factor: Double
) {
    fun converts(from: String, to: String): Boolean = this.from == from && this.to == to
}