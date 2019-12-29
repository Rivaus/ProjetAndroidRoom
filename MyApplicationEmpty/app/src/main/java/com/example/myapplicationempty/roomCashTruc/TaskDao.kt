package com.example.myapplicationempty.roomCashTruc

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplicationempty.Task


@Dao
interface TaskDao {

    @Query("SELECT * from tasks_table")
    fun getTasks(): LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    @Query("DELETE FROM tasks_table")
    suspend fun deleteAll()
}