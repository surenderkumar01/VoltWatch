package com.example.voltwatch.core.prasentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voltwatch.core.data.dataclass.BatteryUiState
import com.example.voltwatch.core.data.roomdatabase.entity.battery_data
import com.example.voltwatch.core.useCase.addDataUseCase
import com.example.voltwatch.core.useCase.getBattery_dataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class batteryViewModel @Inject constructor(val useCase: getBattery_dataUseCase, val addDataUseCase: addDataUseCase): ViewModel() {

    private val _batteryState= MutableStateFlow<List<battery_data>>(emptyList())
    val battery_data: StateFlow<List<battery_data>> =_batteryState


    private val _state = MutableStateFlow(BatteryUiState())
    val state: StateFlow<BatteryUiState> = _state
    fun addAllData(data: battery_data){
        viewModelScope.launch {
           addDataUseCase.invoke(data)
        }
    }
    init {
        getAllData()
    }
    fun getAllData(){
        viewModelScope.launch {
            useCase.invoke().collect {
                    _batteryState.value=it
            }

        }
    }
    fun updateBattery(
        level: Int,
        temp: Int,
        voltage: Int,
        tech: String,
        health: String
    ) {

        _state.value = BatteryUiState(
            level = level,
            temp = temp / 10f,
            voltage = voltage / 1000f,
            tech = tech,
            health = health
        )
    }
}