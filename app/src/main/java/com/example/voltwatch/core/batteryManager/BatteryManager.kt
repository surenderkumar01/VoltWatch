package com.example.voltwatch.core.batteryManager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.voltwatch.core.data.roomdatabase.entity.battery_data
import com.example.voltwatch.core.prasentation.sccreen.DashboardScreen
import com.example.voltwatch.core.prasentation.viewModel.batteryViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun BatteryManager(viewModel: batteryViewModel) {

    val context = LocalContext.current

    Toast.makeText(context, "MANAGER", Toast.LENGTH_LONG).show()


    DisposableEffect(Unit) {
        val reviever = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {

                intent ?: return
                val level = GetData(BatteryManager.EXTRA_LEVEL, intent)
                val scale = GetData(BatteryManager.EXTRA_SCALE, intent)

                val temp = GetData(BatteryManager.EXTRA_TEMPERATURE, intent)
                val voltage = GetData(BatteryManager.EXTRA_VOLTAGE, intent)

                val health = GetData(BatteryManager.EXTRA_HEALTH, intent)
                val healthtype=getHealthString(health)
                val tech = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY)


                val time = getCurrenttime(System.currentTimeMillis())
                val data = battery_data(
                    0,
                    level.toString(),
                    temp.toString(),
                    voltage.toString(),
                    tech.toString(),
                    time
                )

                Log.d("Data",level.toString())
                viewModel.updateBattery(
                    level = level,
                    temp = temp,
                    voltage = voltage,
                    tech = tech ?: "",
                    health = healthtype
                )
                viewModel.addAllData(data)

            }


        }
        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        context.registerReceiver(reviever, intentFilter)

        onDispose {
            context.unregisterReceiver(reviever)
        }
    }
}


fun GetData(extraLevel: String, intent: Intent): Int {
    return intent.getIntExtra(extraLevel, -1)

}

private fun getCurrenttime(currentTimeMillis: Long): String {
    return SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(
        Date(
            currentTimeMillis
        )
    )
}

fun getHealthString(health: Int): String {
    return when (health) {
        BatteryManager.BATTERY_HEALTH_GOOD -> "Good"
        BatteryManager.BATTERY_HEALTH_OVERHEAT -> "Overheat"
        BatteryManager.BATTERY_HEALTH_DEAD -> "Dead"
        BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> "Over Voltage"
        BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE -> "Failure"
        BatteryManager.BATTERY_HEALTH_COLD -> "Cold"
        else -> "Unknown"
    }
}