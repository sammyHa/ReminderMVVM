package com.samimhakimi.remindermvvm

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.samimhakimi.remindermvvm.databinding.ActivityMainBinding
import com.samimhakimi.remindermvvm.models.Task
import com.samimhakimi.remindermvvm.repositories.TaskRepository
import com.samimhakimi.remindermvvm.source.local.TaskDao
import com.samimhakimi.remindermvvm.source.local.TaskDatabase
import com.samimhakimi.remindermvvm.utils.MainViewModelFactory
import com.samimhakimi.remindermvvm.utils.toast
import com.samimhakimi.remindermvvm.viewModels.MainViewModel
import com.samimhakimi.remindermvvm.views.TaskRecyclerViewAdapter
import com.samimhakimi.remindermvvm.views.onItemClickListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity(),onItemClickListener{

    lateinit var binding: ActivityMainBinding
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

        setContentView(binding.root)

        viewModel.taskDao.observe(this, {
            adapter =  TaskRecyclerViewAdapter(taskList = it as MutableList<Task>,this)

        })

        initRecyclerView()
        openBottomSheetDialog()

        binding.taskRecyclerView.setOnScrollChangeListener { _, _, _, _, oldScrollY ->
            if (oldScrollY < 0) fab_add.hide() else fab_add.show()
        }
    }



    private fun initRecyclerView(){
        displayTasks()
        binding.taskRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun displayTasks(){

        viewModel.taskDao.observe(this, {
            binding.taskRecyclerView.adapter = adapter
        })
        swipeToDelete()

    }

    private fun itemClicked(task: Task){
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

        lifecycleScope.launchWhenStarted {
            viewModel.taskEvent.collect {event ->
                when(event){
                    is MainViewModel.TaskEvent.ShowUndoDeleteMessage ->{
                        Snackbar.make(requireViewById(R.id.task_recyclerView), "1 Completed", Snackbar.LENGTH_LONG)
                            .setTextColor(Color.WHITE)
                            .setActionTextColor(ContextCompat.getColor(applicationContext, R.color.undoColor))
                            .setAction("UNDO"){
                                viewModel.onUndoDeleted(event.task)

                            }
                    }
                }.show()
            }
        }

    }

    override fun onItemClicked(task: Task) {

        viewModel.onTaskSelected(task)
        toast("task clicked ${task.taskTitle}")

    }

    override fun onCheckedBoxClicked(task: Task, isChecked: Boolean) {
        viewModel.onTaskCheckedChanged(task, isChecked)
    }
}
