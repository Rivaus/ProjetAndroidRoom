package com.example.roomapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_new_task.*

class NewTaskActivity : AppCompatActivity() {

    private lateinit var editTaskTitle: EditText
    private lateinit var editTaskDescription: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)

        editTaskTitle = edit_task_title
        editTaskDescription = edit_task_description

        val addButton = button_save

        addButton.setOnClickListener {
            val intent = Intent()

            var error = ""
            if (TextUtils.isEmpty(editTaskTitle.text)){
                // TODO
                error += "Titre vide"
            }
            if (TextUtils.isEmpty(editTaskDescription.text)){
                // TODO
                error += "Description vide"
            }

            if (error == ""){
                // C'est ok
                var title = editTaskTitle.text.toString()
                var description = editTaskDescription.text.toString()

                intent.putExtra("NEW_TASK_TITLE", title)
                intent.putExtra("NEW_TASK_DESCRIPTION", description)
                setResult(Activity.RESULT_OK, intent)
            } else {
                setResult(Activity.RESULT_CANCELED, intent)
            }

            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.tasklistsql.REPLY"
    }
}
