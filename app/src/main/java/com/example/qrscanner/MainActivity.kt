package com.example.qrscanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.qrscanner.navigation.AppNavigation
import com.example.qrscanner.ui.theme.QRScannerTheme
import com.example.qrscanner.viewmodel.AppViewModel
import com.example.qrscanner.viewmodel.AppViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QrScannerRoot()
        }
    }
}

@Composable
private fun QrScannerRoot() {
    val application = LocalContext.current.applicationContext as QrScannerApplication
    val viewModel: AppViewModel = viewModel(factory = AppViewModelFactory(application.repository))

    QRScannerTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            AppNavigation(viewModel = viewModel)
        }
    }
}
