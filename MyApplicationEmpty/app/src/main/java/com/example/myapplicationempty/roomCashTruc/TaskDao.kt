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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tasks: List<Task>)

    @Query("DELETE FROM tasks_table")
    suspend fun deleteAll()

    @Query("DELETE FROM tasks_table WHERE id= :id")
    suspend fun deleteTask(id:String)

    @Query("UPDATE tasks_table SET title = :title, description = :description Where id = :id")
    suspend fun updateTask(id: String,title: String, description : String)
}