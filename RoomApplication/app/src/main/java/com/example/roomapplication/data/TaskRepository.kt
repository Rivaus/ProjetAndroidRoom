package com.example.roomapplication.data


import android.os.Debug
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.roomapplication.network.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepository (private val taskDao: TaskDao) {

    val allTasks: LiveData<List<Task>> = taskDao.getTasks()


    private val tasksService = Api.tasksService

    suspend fun insert(task : Task) {
        taskDao.insert(task)
    }

    suspend fun delete(task : Task){
        taskDao.delete(task)
        tasksService.deleteTask(task.id)
    }

    suspend fun update(task : Task){
        taskDao.update(task)
    }

    // Refresh the local database with tasks from the Internet
    suspend fun getOnlineTasks() : List<Task>?{
        taskDao.deleteAllOnlineTask()
        val tasks = tasksService.getTasks().body()
        return tasks
    }

    suspend fun getLocalTasks() : List<Task>?{
        return taskDao.getAllLocalTasks()
    }
}