package com.example.roomapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Task::class), version = 1, exportSchema = false)
abstract class TaskRoomDatabase : RoomDatabase() {

    abstract fun taskDao() : TaskDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: TaskRoomDatabase? = null

        fun getDatabase(context: Context,
                        scope : CoroutineScope): TaskRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskRoomDatabase::class.java,
                    "task_database_02.db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private class TaskDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.taskDao())
                }
            }
        }

        suspend fun populateDatabase(taskDao: TaskDao) {
            taskDao.deleteAllOnlineTask()

            var task = Task("id_01", "Fait à manger", "Croque monsieur")
            taskDao.insert(task)
            task = Task("id_02", "Rappelle Emma", "Elle a surement encore faim.")
            taskDao.insert(task)
        }
    }
}

