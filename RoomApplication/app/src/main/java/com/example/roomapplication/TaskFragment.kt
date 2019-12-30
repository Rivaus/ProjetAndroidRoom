package com.example.roomapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomapplication.data.Task
import com.example.roomapplication.data.TaskListAdapter
import com.example.roomapplication.data.TaskViewModel
import com.example.roomapplication.network.Api
import kotlinx.android.synthetic.main.fragment_task.*
import kotlinx.android.synthetic.main.fragment_task.view.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.random.Random
import android.net.NetworkInfo
import androidx.core.content.ContextCompat.getSystemService
import android.net.ConnectivityManager
import com.example.roomapplication.network.Api.tasksService


class TaskFragment : Fragment() {

    private val newTaskdActivityRequestCode = 1

    private lateinit var taskViewModel: TaskViewModel

    private lateinit var adapter : TaskListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_task, container, false)

        // On met en place la recycling view
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        adapter =  TaskListAdapter(view.context, taskViewModel)

        view.recyclerview.adapter = adapter
        view.recyclerview.layoutManager = LinearLayoutManager(context)


        taskViewModel.allTasks.observe(viewLifecycleOwner, Observer {tasks ->
            tasks?.let {
                if (it != null)
                    adapter.setTasks(it)
            }
        })

        // Le bouton d'ajout de tache
        view.addBtn.setOnClickListener{
            val intent = Intent(this.context, NewTaskActivity::class.java)
            startActivityForResult(intent, newTaskdActivityRequestCode)
        }

        return view
    }

    private val coroutineScope = MainScope()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // On gère l'ajout d'une nouvelle tache
        if (requestCode == newTaskdActivityRequestCode && resultCode == Activity.RESULT_OK){

            var id = Random.nextInt(0, 100000000).toString()

            data?.extras?.let {
                var task = Task(id, it.getString("NEW_TASK_TITLE")!!, it.getString("NEW_TASK_DESCRIPTION")!!)
                taskViewModel.insert(task)
            }

        }else {
            Toast.makeText(context, "Erreur : Ajout de tache non effectuée", Toast.LENGTH_LONG).show()
        }
    }



    override fun onResume() {
        super.onResume()
        coroutineScope.launch {
            if (Api.checkIfNetworkAvailable(activity)){
                val body = Api.userService.getInfo().body()
                val name = body?.firstName + " " + body?.lastName

                Toast.makeText(context, "Bonjour $name", Toast.LENGTH_LONG).show()
            }

            if (Api.checkIfNetworkAvailable(activity)){
                taskViewModel.refreshTasks()
            }


        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }

}
