package com.example.remindermvvm

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remindermvvm.databinding.AddTasksBinding
import com.example.remindermvvm.models.Task
import com.example.remindermvvm.repositories.TaskRepository
import com.example.remindermvvm.source.local.TaskDao
import com.example.remindermvvm.source.local.TaskDatabase
import com.example.remindermvvm.utils.TaskViewModelFactory
import com.example.remindermvvm.viewModels.TaskViewModel
import com.example.remindermvvm.views.TaskRecyclerViewAdapter

class AddTastActivity:AppCompatActivity() {

    lateinit var binding: AddTasksBinding
    lateinit var taskViewModel:TaskViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.add_tasks)
        val dao:TaskDao = TaskDatabase.getInstance(applicationContext).taskDao
        val repository = TaskRepository(dao)
        val factory = TaskViewModelFactory(repository)
        taskViewModel = ViewModelProvider(this, factory).get(TaskViewModel::class.java)
        binding.taskViewModel = taskViewModel
        binding.lifecycleOwner = this
        initRecyclerView()

    }

    fun initRecyclerView(){
        binding.taskRecyclerView.layoutManager = LinearLayoutManager(this)
        displayTasks()
    }

    fun displayTasks(){
        taskViewModel.task.observe(this, Observer {
            binding.taskRecyclerView.adapter = TaskRecyclerViewAdapter(taskList = it
            ) { selectedItem: Task -> run { itemClicked(selectedItem) } }

        })
    }

    fun itemClicked(task:Task){
        Toast.makeText(this, task.taskTitle, Toast.LENGTH_SHORT).show()
    }
}