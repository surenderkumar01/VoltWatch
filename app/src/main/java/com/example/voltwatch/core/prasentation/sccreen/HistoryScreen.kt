package com.example.voltwatch.core.prasentation.sccreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.voltwatch.core.data.roomdatabase.entity.battery_data
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.collectAsState

import androidx.compose.ui.Alignment

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.voltwatch.core.prasentation.viewModel.batteryViewModel@Composable
fun HistoryScreen(viewModel: batteryViewModel = hiltViewModel()) {

    val data = viewModel.battery_data.collectAsState().value
val list=data.reversed()
    LazyColumn {
        items(list) { item ->

            val level = item.level.toIntOrNull() ?: 0

            val levelColor = when {
                level > 60 -> Color(0xFF00E676)
                level > 20 -> Color(0xFFFFC107)
                else -> Color(0xFFFF5252)
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF16263A)),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {

                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // 🔋 Battery Circle
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(levelColor.copy(alpha = 0.2f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$level%",
                            color = levelColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))


                    Column(modifier = Modifier.weight(1f)) {

                        Text("Battery", color = Color.Gray, fontSize = 12.sp)

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = if (level >= 100) "⚡ Full" else " Active",
                            color = Color.White,
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "🌡 ${item.temperature}°C   ⚡ ${item.voltage}V",
                            color = Color.LightGray,
                            fontSize = 13.sp
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = item.technology,
                            color = Color(0xFF64B5F6),
                            fontSize = 13.sp
                        )
                    }

                    // 🕒 Time
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = item.timestamp,
                            color = Color(0xFFFFC107),
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}