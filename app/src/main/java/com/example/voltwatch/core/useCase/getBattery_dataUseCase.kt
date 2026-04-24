package com.example.voltwatch.core.useCase

import com.example.voltwatch.core.data.roomdatabase.entity.battery_data
import com.example.voltwatch.core.domain.BatteryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class getBattery_dataUseCase @Inject constructor(val repository: BatteryRepository) {

    operator fun invoke(): Flow<List<battery_data>> = flow {
        repository.getAllData().collect {
            emit(it)

    }}
}