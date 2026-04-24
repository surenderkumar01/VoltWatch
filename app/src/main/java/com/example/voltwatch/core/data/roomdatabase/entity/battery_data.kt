package com.example.voltwatch.core.data.roomdatabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class battery_data(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val level: String="",
    val temperature: String="",
    val voltage: String="",
    val technology: String="",
    val timestamp: String=""
)
