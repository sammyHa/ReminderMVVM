package com.example.remindermvvm

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remindermvvm.databinding.ActivityMainBinding
import com.example.remindermvvm.models.Task
import com.example.remindermvvm.repositories.TaskRepository
import com.example.remindermvvm.source.local.TaskDao
import com.example.remindermvvm.source.local.TaskDatabase
import com.example.remindermvvm.utils.MainViewModelFactory
import com.example.remindermvvm.viewModels.MainViewModel
import com.example.remindermvvm.views.TaskRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    lateinit var binding:ActivityMainBinding
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao: TaskDao = TaskDatabase.getInstance(applicationContext).taskDao
        val repository = TaskRepository(dao)
        val factory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        binding.myViewModel = viewModel
        binding.lifecycleOwner = this
        initRecyclerView()
        addTask()
    }


    private fun initRecyclerView(){
        binding.taskRecyclerView.layoutManager = LinearLayoutManager(this)
        displayTasks()
    }

    private fun displayTasks(){
        viewModel.task.observe(this, {
            binding.taskRecyclerView.adapter = TaskRecyclerViewAdapter(taskList = it as MutableList<Task>
            ) { selectedItem: Task -> run {
                itemClicked(selectedItem)
                }
            }
        })
    }

    private fun itemClicked(task:Task){
        Toast.makeText(this, task.taskTitle, Toast.LENGTH_SHORT).show()
    }

    private fun addTask(){

        fab_add.setOnClickListener {
            Log.d("TAG","Saved")
            BottomSheetFragment().apply {
                show(supportFragmentManager, "BottomSheetFragment")
            }
        }
    }
}
