package sola.angel.kunits.config

internal data class UnitGroup(
    val name: String,
    val systems: Set<SystemFactors>,
    val systemConversion: Set<SystemsConversion>
)