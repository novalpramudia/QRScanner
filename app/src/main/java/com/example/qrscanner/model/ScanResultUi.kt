package com.example.qrscanner.model

data class ScanResultUi(
    val content: String,
    val type: ScanResultType,
    val timestamp: Long
)
