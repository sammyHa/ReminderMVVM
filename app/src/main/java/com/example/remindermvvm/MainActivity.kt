package com.example.remindermvvm

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.remindermvvm.databinding.ActivityMainBinding
import com.example.remindermvvm.models.Task
import com.example.remindermvvm.repositories.TaskRepository
import com.example.remindermvvm.source.local.TaskDao
import com.example.remindermvvm.source.local.TaskDatabase
import com.example.remindermvvm.utils.MainViewModelFactory
import com.example.remindermvvm.viewModels.MainViewModel
import com.example.remindermvvm.views.TaskRecyclerViewAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    lateinit var binding:ActivityMainBinding
    lateinit var viewModel: MainViewModel
    lateinit var adapter : TaskRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao: TaskDao = TaskDatabase.getInstance(applicationContext).taskDao
        val repository = TaskRepository(dao)
        val factory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        binding.myViewModel = viewModel
        binding.lifecycleOwner = this
        binding.taskRecyclerView.attachFab(fab_add)
        setContentView(binding.root)

        viewModel.task.observe(this, {
            adapter =  TaskRecyclerViewAdapter(taskList = it as MutableList<Task>)

        })

        initRecyclerView()
        openBottomSheetDialog()

    }

    private fun RecyclerView.attachFab(fab : FloatingActionButton) {
        this.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0)
                    fab.hide()
                else if (dy < 0)
                    fab.show()
            }
        })
    }

    private fun initRecyclerView(){
        displayTasks()
        binding.taskRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun displayTasks(){

        viewModel.task.observe(this, {
            binding.taskRecyclerView.adapter = adapter
        })
        swipeToDelete()

    }

    private fun itemClicked(task:Task){
        Toast.makeText(this, task.taskTitle, Toast.LENGTH_SHORT).show()
    }

    private fun openBottomSheetDialog(){
        fab_add.setOnClickListener {
            Log.d("TAG","Saved")
            BottomSheetFragment().apply {
                show(supportFragmentManager, "BottomSheetFragment")
            }
        }
    }

    private fun swipeToDelete(){
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.delete(adapter.getTaskAt(position))

            }

        }).attachToRecyclerView(binding.taskRecyclerView)


    }
}
