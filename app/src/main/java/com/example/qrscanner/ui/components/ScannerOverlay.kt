package com.example.qrscanner.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

/**
 * Overlay gelap dengan kotak/frame transparan di tengah layar untuk memandu
 * pengguna mengarahkan QR Code, lengkap dengan sudut penanda dan animasi
 * garis pemindaian yang bergerak naik-turun.
 */
@Composable
fun ScannerOverlay(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "scan_line_transition")
    val lineProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "line_progress"
    )

    val primaryColor = MaterialTheme.colorScheme.primary

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
    ) {
        val frameSize = size.minDimension * 0.65f
        val left = (size.width - frameSize) / 2f
        val top = (size.height - frameSize) / 2f
        val cornerRadiusPx = 24.dp.toPx()

        // Scrim gelap dengan area tengah transparan (cutout)
        drawRect(color = Color.Black.copy(alpha = 0.55f))
        drawRoundRect(
            color = Color.Transparent,
            topLeft = Offset(left, top),
            size = Size(frameSize, frameSize),
            cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx),
            blendMode = BlendMode.Clear
        )

        // Penanda sudut (corner brackets)
        val strokeWidth = 6.dp.toPx()
        val cornerLength = frameSize * 0.12f

        fun drawCorner(cx: Float, cy: Float, dx1: Float, dy1: Float, dx2: Float, dy2: Float) {
            drawLine(primaryColor, Offset(cx, cy), Offset(cx + dx1, cy + dy1), strokeWidth)
            drawLine(primaryColor, Offset(cx, cy), Offset(cx + dx2, cy + dy2), strokeWidth)
        }

        drawCorner(left, top, cornerLength, 0f, 0f, cornerLength)
        drawCorner(left + frameSize, top, -cornerLength, 0f, 0f, cornerLength)
        drawCorner(left, top + frameSize, cornerLength, 0f, 0f, -cornerLength)
        drawCorner(left + frameSize, top + frameSize, -cornerLength, 0f, 0f, -cornerLength)

        // Garis animasi pemindaian
        val lineY = top + frameSize * lineProgress
        drawLine(
            brush = Brush.horizontalGradient(
                listOf(Color.Transparent, primaryColor, Color.Transparent)
            ),
            start = Offset(left + 8f, lineY),
            end = Offset(left + frameSize - 8f, lineY),
            strokeWidth = 4.dp.toPx()
        )
    }
}
