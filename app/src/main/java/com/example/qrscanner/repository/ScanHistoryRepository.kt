package com.example.qrscanner.repository

import com.example.qrscanner.data.ScanHistoryDao
import com.example.qrscanner.model.ScanHistoryEntity
import kotlinx.coroutines.flow.Flow

class ScanHistoryRepository(private val dao: ScanHistoryDao) {

    fun getAllHistory(): Flow<List<ScanHistoryEntity>> = dao.getAll()

    suspend fun insert(entity: ScanHistoryEntity) = dao.insert(entity)

    suspend fun deleteById(id: Long) = dao.deleteById(id)

    suspend fun deleteAll() = dao.deleteAll()
}
