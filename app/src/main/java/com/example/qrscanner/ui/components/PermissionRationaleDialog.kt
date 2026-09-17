package com.example.qrscanner.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun PermissionRationaleDialog(
    onOpenSettings: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = { Icon(Icons.Filled.CameraAlt, contentDescription = null) },
        title = { Text("Izin Kamera Diperlukan") },
        text = {
            Text(
                "Aplikasi ini membutuhkan akses kamera untuk memindai QR Code. " +
                    "Silakan aktifkan izin kamera melalui Pengaturan aplikasi."
            )
        },
        confirmButton = {
            TextButton(onClick = onOpenSettings) { Text("Buka Pengaturan") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Batal") }
        }
    )
}
