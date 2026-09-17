package com.example.qrscanner.model

enum class ScanResultType(val label: String) {
    URL("Tautan (URL)"),
    EMAIL("Alamat Email"),
    PHONE("Nomor Telepon"),
    WIFI("Jaringan WiFi"),
    SMS("Pesan SMS"),
    GEO("Lokasi"),
    TEXT("Teks")
}
