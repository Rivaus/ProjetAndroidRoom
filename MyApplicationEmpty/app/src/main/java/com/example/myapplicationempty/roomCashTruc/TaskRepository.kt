package com.example.myapplicationempty.roomCashTruc

import androidx.lifecycle.LiveData
import com.example.myapplicationempty.Task
import com.example.myapplicationempty.network.Api

class TaskRepository (private val taskDao: TaskDao){

    private val tasksService = Api.INSTANCE.tasksService

    suspend fun deleteTask(id: String): Boolean {
        val tasksResponse = tasksService.deleteTask(id)
        return tasksResponse.isSuccessful
    }

    suspend fun loadTasks(): List<Task>? {
        val tasksResponse = tasksService.getTasks()
        return if (tasksResponse.isSuccessful) tasksResponse.body() else null
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allTasks: LiveData<List<Task>> = taskDao.getTasks()

    suspend fun insert(task: Task) {
        taskDao.insert(task)
    }
}