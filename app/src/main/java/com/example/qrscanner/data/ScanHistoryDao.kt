package com.example.qrscanner.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.qrscanner.model.ScanHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScanHistoryDao {

    @Insert
    suspend fun insert(entity: ScanHistoryEntity)

    @Query("SELECT * FROM scan_history ORDER BY timestamp DESC")
    fun getAll(): Flow<List<ScanHistoryEntity>>

    @Query("DELETE FROM scan_history WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM scan_history")
    suspend fun deleteAll()
}
