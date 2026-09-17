package com.example.qrscanner.scanner

import android.content.Context
import android.net.Uri
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * Utility untuk memindai QR Code dari gambar statis (mis. hasil pilihan dari galeri),
 * menggunakan mesin ML Kit yang sama dengan pemindaian kamera secara real-time.
 */
object GalleryQrScanner {

    /** Mengembalikan isi QR Code pertama yang ditemukan pada gambar, atau null jika tidak ada. */
    suspend fun scanFromUri(context: Context, uri: Uri): String? {
        return try {
            val options = BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                .build()
            val scanner = BarcodeScanning.getClient(options)
            val inputImage = InputImage.fromFilePath(context, uri)

            suspendCancellableCoroutine { continuation ->
                scanner.process(inputImage)
                    .addOnSuccessListener { barcodes ->
                        val value = barcodes.firstOrNull { !it.rawValue.isNullOrBlank() }?.rawValue
                        if (continuation.isActive) continuation.resume(value)
                    }
                    .addOnFailureListener {
                        if (continuation.isActive) continuation.resume(null)
                    }
            }
        } catch (e: Exception) {
            null
        }
    }
}
