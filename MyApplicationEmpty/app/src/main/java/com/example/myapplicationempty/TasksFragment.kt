package com.example.myapplicationempty

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplicationempty.network.Api
import com.example.myapplicationempty.network.TasksRepository
import kotlinx.android.synthetic.main.fragment_tasks.*
import kotlinx.android.synthetic.main.fragment_tasks.view.*
import kotlinx.coroutines.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class TasksFragment : Fragment() {

    private val tasksRepo = TasksRepository()

    private val tasks = mutableListOf<Task>()

    private val coroutineScope = MainScope()

    val onDeleteClickListener : (Task) -> Unit = {
            task ->
        coroutineScope.launch {
            if(tasksRepo.deleteTask(task.id)){
                tasks.remove(task)
                adapter.notifyDataSetChanged()
            }

        }
    }

    val onEditTaskCallback : (Task) -> Unit = {
        task ->
        val intent = Intent(this.context, TaskFormActivity::class.java)
        intent.putExtra("task_id", task.id)
        intent.putExtra("task_title", task.title)
        intent.putExtra("task_content", task.description)
        startActivity(intent)
    }

    private val adapter = TasksAdapter (tasks, onDeleteClickListener, onEditTaskCallback)


    override fun onResume() {
        super.onResume()
        tasksRepo.getTasks().observe(this, Observer {
            if (it != null) {
                tasks.clear()
                tasks.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle? ): View? {
        val view = inflater.inflate(R.layout.fragment_tasks, container, false)
        view.tasks_recycler_view.adapter = adapter
        view.tasks_recycler_view.layoutManager = LinearLayoutManager(context)

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }


}
