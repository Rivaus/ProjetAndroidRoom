package com.example.roomapplication.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {

    @Query("SELECT * from task_table")
    fun getTasks(): LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    @Query("DELETE FROM task_table WHERE status = 'online'")
    suspend fun deleteAllOnlineTask()

    @Delete
    suspend fun delete(task : Task)

    @Query("SELECT * FROM task_table WHERE status = 'local' ")
    suspend fun getAllLocalTasks() : List<Task>

    @Update
    suspend fun update(task : Task)
}