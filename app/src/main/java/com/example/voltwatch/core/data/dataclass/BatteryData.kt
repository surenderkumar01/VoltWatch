package com.example.voltwatch.core.data.dataclass

import androidx.room.PrimaryKey

data class BatteryData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val level: String="",
    val temperature: String="",
    val voltage: String="",
    val technology: String="",
    val timestamp: String=""
)
