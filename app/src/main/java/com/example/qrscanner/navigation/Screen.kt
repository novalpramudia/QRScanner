package com.example.qrscanner.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Scanner : Screen("scanner")
    object Result : Screen("result")
    object History : Screen("history")
}
