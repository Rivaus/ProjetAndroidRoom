package com.example.myapplicationempty

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_task.view.*

class TaskViewHolder (itemView: View,
                      val onDeleteClickListener : (Task) -> Unit,
                      val onEdit : (Task) -> Unit)
                        : RecyclerView.ViewHolder(itemView) {


    fun bind(task: Task) {
        itemView.task_title.text = task.title
        itemView.task_description.text = task.description

        itemView.imageButton.setOnClickListener{onDeleteClickListener(task)}

        itemView.editButton.setOnClickListener {onEdit(task)}
    }
}