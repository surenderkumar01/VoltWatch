package com.example.voltwatch.core.data.roomdatabase.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.voltwatch.core.data.roomdatabase.dao.BatteryDao
import com.example.voltwatch.core.data.roomdatabase.entity.battery_data

@Database(entities = [battery_data::class], version = 1)
abstract class VoltWatchDatabase : RoomDatabase() {
    abstract fun batteryDao(): BatteryDao
}