package com.example.workoutapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.workoutapp.models.History
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Insert
    suspend fun insert(history: History)

    @Query("SELECT * FROM `history-table`")
    fun fetchAllDates(): Flow<List<History>>
}