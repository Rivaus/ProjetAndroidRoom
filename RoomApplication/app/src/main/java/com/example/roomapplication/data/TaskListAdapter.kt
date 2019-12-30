package com.example.roomapplication.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.roomapplication.R
import com.example.roomapplication.network.Api
import kotlinx.android.synthetic.main.task_item.view.*

class TaskListAdapter internal constructor(
    val context: Context,
    val taskViewModel: TaskViewModel
) : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    var tasks = emptyList<Task>() // Cached copy

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind (task : Task){
            // set text
            itemView.titleText.text = task.title + " (" + task.status + ")"
            itemView.descriptionText.text = task.description

            // set button listener
            itemView.deleteTaskButton.setOnClickListener {
                taskViewModel.delete(task)
                notifyDataSetChanged()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = inflater.inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    internal fun setTasks(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    override fun getItemCount() = tasks.size
}