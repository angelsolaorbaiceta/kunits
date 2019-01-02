package sola.angel.kunits

class ConversionException internal constructor(
    val from: UnitExpansion,
    val to: UnitExpansion
) : Exception("Cannot convert from $from to $to")