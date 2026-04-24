package com.example.voltwatch.core.useCase

import android.util.Log
import com.example.voltwatch.core.data.roomdatabase.entity.battery_data
import com.example.voltwatch.core.domain.BatteryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class addDataUseCase @Inject constructor(val repository: BatteryRepository) {
    suspend operator fun invoke(data: battery_data) {

        repository.saveBatteryData(data)
    }
}