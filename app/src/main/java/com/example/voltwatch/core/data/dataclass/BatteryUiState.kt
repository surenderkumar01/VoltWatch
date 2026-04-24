package com.example.voltwatch.core.data.dataclass

data class BatteryUiState(
    val level: Int = 0,
    val temp: Float = 0f,
    val voltage: Float = 0f,
    val tech: String = "",
    val health: String = ""
)