package com.example.myapplicationempty.roomCashTruc

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplicationempty.Task
import com.example.myapplicationempty.network.TasksRepository
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val repository : TaskRepository
    val allTasks: LiveData<List<Task>>


    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        // the correct WordRepository.
        val tasksDao = TaskRoomDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(tasksDao)
        allTasks = repository.allTasks
    }

    /**
     * The implementation of insert() in the database is completely hidden from the UI.
     * Room ensures that you're not doing any long running operations on
     * the main thread, blocking the UI, so we don't need to handle changing Dispatchers.
     * ViewModels have a coroutine scope based on their lifecycle called
     * viewModelScope which we can use here.
     */
    fun insert(task: Task) = viewModelScope.launch {
        repository.insert(task)
    }

}