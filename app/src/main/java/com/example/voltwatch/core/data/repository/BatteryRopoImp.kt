package com.example.voltwatch.core.data.repository

import android.util.Log
import com.example.voltwatch.core.data.roomdatabase.dao.BatteryDao
import com.example.voltwatch.core.data.roomdatabase.entity.battery_data
import com.example.voltwatch.core.domain.BatteryRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class BatteryRopoImp @Inject constructor(private val dao: BatteryDao): BatteryRepository {

    override suspend fun saveBatteryData(battery_data: battery_data) {
        Log.d("DATABASE",battery_data.level)
        Log.d("DATABASE",battery_data.timestamp)
        dao.saveBatteryData(battery_data)
    }

    override  fun getAllData(): Flow<List<battery_data>> {
        val  data= dao.getAllData()
       return data
    }
}