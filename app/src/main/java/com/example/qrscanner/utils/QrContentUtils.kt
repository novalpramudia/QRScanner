package com.example.qrscanner.utils

import com.example.qrscanner.model.ScanResultType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object QrContentUtils {

    fun detectType(content: String): ScanResultType {
        val trimmed = content.trim()
        return when {
            trimmed.startsWith("http://", true) || trimmed.startsWith("https://", true) ||
                trimmed.startsWith("www.", true) -> ScanResultType.URL

            trimmed.startsWith("mailto:", true) ||
                Regex("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$").matches(trimmed) -> ScanResultType.EMAIL

            trimmed.startsWith("tel:", true) ||
                Regex("^\\+?[0-9\\s-]{6,15}$").matches(trimmed) -> ScanResultType.PHONE

            trimmed.startsWith("WIFI:", true) -> ScanResultType.WIFI

            trimmed.startsWith("smsto:", true) || trimmed.startsWith("sms:", true) -> ScanResultType.SMS

            trimmed.startsWith("geo:", true) -> ScanResultType.GEO

            else -> ScanResultType.TEXT
        }
    }

    fun typeFromName(name: String): ScanResultType {
        return try {
            ScanResultType.valueOf(name)
        } catch (e: IllegalArgumentException) {
            ScanResultType.TEXT
        }
    }

    fun formatTimestamp(timestamp: Long): String {
        val formatter = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("id", "ID"))
        return formatter.format(Date(timestamp))
    }

    fun isUrl(type: ScanResultType): Boolean = type == ScanResultType.URL

    fun normalizeUrl(content: String): String {
        val trimmed = content.trim()
        return if (trimmed.startsWith("www.", true)) "https://$trimmed" else trimmed
    }
}
