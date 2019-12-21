package com.example.myapplicationempty

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView

class TasksAdapter(private val tasks: MutableList<Task>,
                   val deleteCallback : (Task) -> Unit,
                   val onEditCallback : (Task) -> Unit)
                    : RecyclerView.Adapter<TaskViewHolder>() {


    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false),
                                deleteCallback, onEditCallback)
    }

}