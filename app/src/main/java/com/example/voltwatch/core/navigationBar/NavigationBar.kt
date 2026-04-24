package com.example.voltwatch.core.navigationBar

import android.widget.Toast
import androidx.compose.ui.platform.LocalContext

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.voltwatch.core.prasentation.sccreen.DashboardScreen
import com.example.voltwatch.core.prasentation.sccreen.HistoryScreen
import com.example.voltwatch.core.prasentation.viewModel.batteryViewModel

@androidx.compose.runtime.Composable
fun NavigationBar(viewModel1: batteryViewModel) {

    val navController= rememberNavController()
val context= LocalContext.current
    NavHost(navController=navController, startDestination = "dashBoard"){
        composable("dashBoard") {


            DashboardScreen(navController, viewModel1)
        }

        composable("history"){
            HistoryScreen(viewModel1)
        }
    }
}