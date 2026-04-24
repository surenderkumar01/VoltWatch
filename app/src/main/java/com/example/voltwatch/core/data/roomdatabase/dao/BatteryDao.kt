package com.example.voltwatch.core.data.roomdatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.voltwatch.core.data.roomdatabase.entity.battery_data
import kotlinx.coroutines.flow.Flow

@Dao
interface BatteryDao {

    @Insert
   suspend fun saveBatteryData(battery_data:battery_data)

    @Query("SELECT * FROM battery_data")
    fun getAllData(): Flow<List<battery_data>>
}