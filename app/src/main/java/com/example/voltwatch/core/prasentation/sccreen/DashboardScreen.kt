package com.example.voltwatch.core.prasentation.sccreen

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voltwatch.core.data.dataclass.BatteryUiState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.app.NotificationCompat
import androidx.navigation.NavHostController
import com.example.voltwatch.R
import com.example.voltwatch.core.data.dataclass.ItemsCard
import com.example.voltwatch.core.prasentation.viewModel.batteryViewModel


@Composable
fun DashboardScreen(navController: NavHostController, viewModel: batteryViewModel) {

    val data=viewModel.state.collectAsState().value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B1A2B))
            .padding(16.dp)
    ) {
Row(modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically) {
    Text(
        text = "VoltWatch",
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Green
    )
    TextButton(
        onClick = {
            navController.navigate("history")
        },

        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.textButtonColors(
            containerColor = Color(0xFF00E676),
            contentColor = Color.Black
        )
    ) {
        Text(
            text = "View History",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }

}

        Spacer(modifier = Modifier.height(10.dp))


        Spacer(modifier = Modifier.height(24.dp))


        BatteryCircle(data.level)


        Spacer(modifier = Modifier.height(24.dp))


        BatteryInfoCard(data )

        Spacer(modifier = Modifier.height(16.dp))


        TargetAlert(data.level)

        Spacer(modifier = Modifier.height(16.dp))



    }
}

@Composable
fun BatteryCircle(percent: Int) {

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

        Canvas(modifier = Modifier.size(220.dp)) {


            drawArc(
                color = Color.DarkGray,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(20f, cap = StrokeCap.Round)
            )


            drawArc(
                color = Color.Green,
                startAngle = -90f,
                sweepAngle = (percent / 100f) * 360f,
                useCenter = false,
                style = Stroke(20f, cap = StrokeCap.Round)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("$percent%", fontSize = 32.sp, color = Color.Green)
            Text("Battery Level", color = Color.Gray)
        }
    }
}

@Composable
fun BatteryInfoCard(data: BatteryUiState) {

    val list = listOf(
        ItemsCard("Temperature", R.drawable.temprature, "${data.temp} °C"),
        ItemsCard("Voltage", R.drawable.lightning, "${data.voltage} V"),
        ItemsCard("Technology", R.drawable.charging, data.tech),
        ItemsCard("Health", R.drawable.cardiogram, data.health)
    )

    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF16263A)),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth()
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            items(list) { item ->

                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E2F45)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Image(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.itemName,
                            modifier = Modifier.size(28.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = item.itemName,
                            color = Color.Gray,
                            fontSize = 12.sp
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = item.value,
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}




@Composable
fun TargetAlert(percentage: Int) {

    var value by remember { mutableStateOf(20f) }

    Card(
        colors = CardDefaults.cardColors(Color(0xFF16263A)),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Text("Target Alert", color = Color.White)

            Slider(
                value = value,
                onValueChange = { value = it },
                valueRange = 0f..100f
            )

            Text("${value.toInt()}%", color = Color.Yellow)
        }
    }

    if(percentage>value){
        val context= LocalContext.current
        showNotification(context,"Battery reached your target level: ${value}%")
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
