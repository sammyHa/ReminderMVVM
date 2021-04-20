package com.example.remindermvvm.views

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.remindermvvm.R
import com.example.remindermvvm.databinding.TaskItemsBinding
import com.example.remindermvvm.models.Task

class TaskRecyclerViewAdapter(val taskList: MutableList<Task>):RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: TaskItemsBinding = DataBindingUtil.inflate(layoutInflater, R.layout.task_items, parent, false)
        return MyViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(taskList[position])

    }

    override fun getItemCount(): Int {
        Log.e(TAG, "getItemCount: ${taskList.size}", )
        return taskList.size
    }

    fun getTaskAt(position: Int):Task{
        return taskList[position]
    }

}


class MyViewHolder(val binding: TaskItemsBinding):RecyclerView.ViewHolder(binding.root){

    fun bind(task: Task){
        binding.taskTitle.text = task.taskTitle
        binding.taskDescription.text = task.taskDescription
        binding.taskDueDate.text = task.taskDueDate

        binding.listItemLayout.setOnClickListener {
            print("Clicked")
        }

    }




}