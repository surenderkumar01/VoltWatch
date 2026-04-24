package com.example.voltwatch.core.domain

import com.example.voltwatch.core.data.roomdatabase.entity.battery_data
import kotlinx.coroutines.flow.Flow

interface BatteryRepository {

 suspend   fun saveBatteryData(battery_data:battery_data)
    fun getAllData(): Flow<List<battery_data>>

}