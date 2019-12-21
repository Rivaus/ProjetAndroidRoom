package com.example.myapplicationempty.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplicationempty.Task
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class TasksRepository {
    private val tasksService = Api.INSTANCE.tasksService
    private val coroutineScope = MainScope()

    fun getTasks(): LiveData<List<Task>?> {
        val tasks = MutableLiveData<List<Task>?>()
        coroutineScope.launch { tasks.postValue(loadTasks()) }
        return tasks
    }

    private suspend fun loadTasks(): List<Task>? {
        val tasksResponse = tasksService.getTasks()
        Log.e("loadTasks", tasksResponse.toString())
        return if (tasksResponse.isSuccessful) tasksResponse.body() else null
    }

    suspend fun  deleteTask(id : String): Boolean{
        val tasksResponse = tasksService.deleteTask(id)
        Log.e("deleteTask", tasksResponse.toString())
        return tasksResponse.isSuccessful
    }

    suspend fun addTask(id : String, title : String, description : String) : Boolean {
        val task = Task(id, title, description)
        val taskResponse = tasksService.createTask(task)
        Log.e("addTask", taskResponse.toString())
        return taskResponse.isSuccessful
    }

    suspend fun updateTask(id : String, title : String, description : String) : Boolean {
        val task = Task(id, title, description)
        val taskResponse = tasksService.updateTask(id, task)
        Log.e("updateTask", taskResponse.toString())
        return taskResponse.isSuccessful
    }
}