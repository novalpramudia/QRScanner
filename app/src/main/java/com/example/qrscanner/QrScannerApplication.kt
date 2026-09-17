package com.example.qrscanner

import android.app.Application
import com.example.qrscanner.data.AppDatabase
import com.example.qrscanner.repository.ScanHistoryRepository

class QrScannerApplication : Application() {

    val database: AppDatabase by lazy { AppDatabase.getInstance(this) }

    val repository: ScanHistoryRepository by lazy {
        ScanHistoryRepository(database.scanHistoryDao())
    }
}
