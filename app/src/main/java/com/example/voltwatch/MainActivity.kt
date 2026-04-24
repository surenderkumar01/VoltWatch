package com.example.voltwatch

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.app.NotificationCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.voltwatch.core.batteryManager.BatteryManager
import com.example.voltwatch.core.navigationBar.NavigationBar

import com.example.voltwatch.core.prasentation.viewModel.batteryViewModel
import com.example.voltwatch.ui.theme.VoltWatchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
        }
        registerReceiver(broadcastReceiver(), filter)
        enableEdgeToEdge()
        setContent {
            VoltWatchTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel: batteryViewModel = hiltViewModel()  // ✅ SINGLE VM
                    BatteryManager(viewModel)
                    NavigationBar(viewModel)
//                    DashboardScreen()
                }
            }
        }
    }
}

class broadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, p1: Intent?) {
        when (p1?.action) {
            Intent.ACTION_POWER_CONNECTED -> {
                showNotification(context!!, "Charger Connected")
            }

            Intent.ACTION_POWER_DISCONNECTED -> {
                showNotification(context!!, "Charger Disconnected")
            }
        }
    }

}

fun showNotification(context: Context, message: String) {
    val channelId = "battery_channel"
    val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        val channel =
            NotificationChannel(channelId, "Battery Alerts", NotificationManager.IMPORTANCE_HIGH)
        manager.createNotificationChannel(channel)
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("VoltWatch")
            .setContentText(message)
            .setSmallIcon(android.R.drawable.ic_lock_idle_charging)
            .build()

        manager.notify(1, notification)
    }
}