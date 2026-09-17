package com.example.qrscanner.scanner

import android.annotation.SuppressLint
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Menganalisis setiap frame kamera untuk mendeteksi QR Code menggunakan ML Kit.
 * Setelah satu QR Code berhasil terdeteksi, analyzer akan berhenti memproses
 * frame berikutnya (isPaused = true) agar tidak memicu callback berulang kali
 * untuk QR Code yang sama.
 */
class QrCodeAnalyzer(
    private val onQrCodeScanned: (String) -> Unit
) : ImageAnalysis.Analyzer {

    private val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
        .build()

    private val scanner: BarcodeScanner = BarcodeScanning.getClient(options)

    private val isPaused = AtomicBoolean(false)

    @SuppressLint("UnsafeOptInUsageError")
    @ExperimentalGetImage
    override fun analyze(imageProxy: ImageProxy) {
        if (isPaused.get()) {
            imageProxy.close()
            return
        }

        val mediaImage = imageProxy.image
        if (mediaImage == null) {
            imageProxy.close()
            return
        }

        val inputImage = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

        scanner.process(inputImage)
            .addOnSuccessListener { barcodes ->
                val value = barcodes.firstOrNull { !it.rawValue.isNullOrBlank() }?.rawValue
                if (value != null && isPaused.compareAndSet(false, true)) {
                    onQrCodeScanned(value)
                }
            }
            .addOnFailureListener {
                // Abaikan kegagalan satu frame, proses akan dicoba lagi pada frame berikutnya.
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    }
}
