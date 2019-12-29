package com.example.myapplicationempty.roomCashTruc

import androidx.lifecycle.LiveData
import com.example.myapplicationempty.Task

class TaskRepository (private val taskDao: TaskDao){

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allTasks: LiveData<List<Task>> = taskDao.getTasks()

    suspend fun insert(task: Task) {
        taskDao.insert(task)
    }
}