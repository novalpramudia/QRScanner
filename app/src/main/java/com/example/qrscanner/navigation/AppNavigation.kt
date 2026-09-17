package com.example.qrscanner.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.qrscanner.ui.screens.HistoryScreen
import com.example.qrscanner.ui.screens.HomeScreen
import com.example.qrscanner.ui.screens.ResultScreen
import com.example.qrscanner.ui.screens.ScannerScreen
import com.example.qrscanner.viewmodel.AppViewModel

@Composable
fun AppNavigation(
    viewModel: AppViewModel,
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {

        composable(Screen.Home.route) {
            HomeScreen(
                onScanClick = { navController.navigate(Screen.Scanner.route) },
                onHistoryClick = { navController.navigate(Screen.History.route) }
            )
        }

        composable(Screen.Scanner.route) {
            ScannerScreen(
                onQrDetected = { rawValue ->
                    viewModel.onQrCodeScanned(rawValue)
                    navController.navigate(Screen.Result.route)
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Result.route) {
            val result by viewModel.currentResult.collectAsState()
            ResultScreen(
                result = result,
                onScanAgain = {
                    viewModel.clearCurrentResult()
                    navController.navigate(Screen.Scanner.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                onDone = {
                    viewModel.clearCurrentResult()
                    navController.popBackStack(Screen.Home.route, inclusive = false)
                }
            )
        }

        composable(Screen.History.route) {
            val history by viewModel.history.collectAsState()
            HistoryScreen(
                history = history,
                onItemClick = { entity ->
                    viewModel.showHistoryItem(entity)
                    navController.navigate(Screen.Result.route)
                },
                onDeleteItem = { id -> viewModel.deleteHistoryItem(id) },
                onClearAll = { viewModel.clearAllHistory() },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
