package com.example.myapplicationempty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplicationempty.network.TasksRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_task_form.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import kotlinx.android.synthetic.main.activity_task_form.view.*


class TaskFormActivity : AppCompatActivity() {

    private val tasksRepo = TasksRepository()

    private var currId = 0

    private val coroutineScope = MainScope()

    private var isEditing = false
    private var currentTaskId = "" //If editing

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_form)

        val extras = intent.extras
        if (extras != null){
            titre.setText(extras.getString("task_title"))
            description.setText(extras.getString("task_content"))
            currentTaskId = extras.getString("task_id")!!
            isEditing = true
        }

        confirm_button.setOnClickListener {
            //TaskViewModel.onAddTask(titre.text.toString(), description.text.toString())
            coroutineScope.launch {
                if (isEditing){
                    tasksRepo.updateTask(currentTaskId, titre.text.toString(), description.text.toString())
                }else {
                    if (tasksRepo.addTask("id_"+ currId, titre.text.toString(), description.text.toString())){

                    }
                }
                isEditing = false
            }
            currId++
            finish() }


    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}
