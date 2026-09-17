package com.example.qrscanner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qrscanner.model.ScanHistoryEntity
import com.example.qrscanner.model.ScanResultUi
import com.example.qrscanner.repository.ScanHistoryRepository
import com.example.qrscanner.utils.QrContentUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AppViewModel(
    private val repository: ScanHistoryRepository
) : ViewModel() {

    private val _currentResult = MutableStateFlow<ScanResultUi?>(null)
    val currentResult: StateFlow<ScanResultUi?> = _currentResult

    val history: StateFlow<List<ScanHistoryEntity>> = repository.getAllHistory()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    /** Dipanggil saat QR Code baru berhasil dipindai dari kamera. */
    fun onQrCodeScanned(rawValue: String) {
        val type = QrContentUtils.detectType(rawValue)
        val timestamp = System.currentTimeMillis()
        _currentResult.value = ScanResultUi(rawValue, type, timestamp)
        viewModelScope.launch {
            repository.insert(
                ScanHistoryEntity(
                    content = rawValue,
                    type = type.name,
                    timestamp = timestamp
                )
            )
        }
    }

    /** Dipanggil saat pengguna membuka salah satu item riwayat. */
    fun showHistoryItem(entity: ScanHistoryEntity) {
        _currentResult.value = ScanResultUi(
            content = entity.content,
            type = QrContentUtils.typeFromName(entity.type),
            timestamp = entity.timestamp
        )
    }

    fun clearCurrentResult() {
        _currentResult.value = null
    }

    fun deleteHistoryItem(id: Long) {
        viewModelScope.launch { repository.deleteById(id) }
    }

    fun clearAllHistory() {
        viewModelScope.launch { repository.deleteAll() }
    }
}
