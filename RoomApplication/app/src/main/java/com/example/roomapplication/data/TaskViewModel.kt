package com.example.roomapplication.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.roomapplication.network.Api
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    //Reference to the repository to get data.
    private val repository: TaskRepository

    // LiveData gives us updated tasks when they change.
    val allTasks: LiveData<List<Task>>

    init {
        val tasksDao = TaskRoomDatabase.getDatabase(application, viewModelScope).taskDao()
        repository = TaskRepository(tasksDao)
        allTasks = repository.allTasks
    }

    fun insert(task: Task) = viewModelScope.launch {
        repository.insert(task)
    }

    fun delete(task : Task) = viewModelScope.launch {
        repository.delete(task)
    }

    fun refreshTasks() = viewModelScope.launch {
        val onlineTasks = repository.getOnlineTasks()
        onlineTasks?.forEach {
            it.status = "online"
            Log.d("TASK", it.title)
            insert(it)
        }

        //Send online all lccal task
        val localTasks = repository.getLocalTasks()
        localTasks?.forEach {
            Log.d("HEY", it.title)
            it.status = "online"
            repository.update(it)
            Api.tasksService.createTask(it)
        }


    }
}