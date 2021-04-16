package com.example.remindermvvm.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.remindermvvm.R
import com.example.remindermvvm.databinding.TaskItemsBinding
import com.example.remindermvvm.models.Task

class TaskRecyclerViewAdapter(val taskList: MutableList<Task>, val clickListener: (Task) -> Unit):RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: TaskItemsBinding = DataBindingUtil.inflate(layoutInflater, R.layout.task_items, parent, false)
        return MyViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(taskList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    fun getTaskAt(position:Int):Task{
        return taskList.get(position)
    }
}


class MyViewHolder(val binding: TaskItemsBinding):RecyclerView.ViewHolder(binding.root){

    fun bind(task: Task, clickListener: (Task) -> Unit){

        binding.taskTitle.text = task.taskTitle
        binding.taskDescription.text = task.taskDescription
        binding.tasDueDate.text = task.taskDueDate
        binding.listItemLayout.setOnClickListener {
            clickListener(task)
            print("Clicked")
        }

    }



}