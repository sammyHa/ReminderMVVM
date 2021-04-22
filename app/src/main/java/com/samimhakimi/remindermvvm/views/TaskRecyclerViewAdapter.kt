package com.samimhakimi.remindermvvm.views

import android.content.ContentValues.TAG
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.samimhakimi.remindermvvm.R
import com.samimhakimi.remindermvvm.databinding.TaskItemsBinding
import com.samimhakimi.remindermvvm.models.Task

class TaskRecyclerViewAdapter(val taskList: MutableList<Task>,private val listener:onItemClickListener):RecyclerView.Adapter<TaskRecyclerViewAdapter.MyViewHolder>() {
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

    fun getTaskAt(position: Int): Task {
        return taskList[position]
    }


    inner class MyViewHolder(val binding: TaskItemsBinding):RecyclerView.ViewHolder(binding.root){

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION){
                        val task = getTaskAt(position)
                        listener.onItemClicked(task = task)
                    }


                }
                isCompleted.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION){
                        val task = getTaskAt(position)
                        listener.onCheckedBoxClicked(task, isCompleted.isChecked)
                    }
                }

            }
        }
        fun bind(task: Task){
            binding.apply {
                taskTitle.text = task.taskTitle
                taskDescription.text = task.taskDescription
                taskDueDate.text = task.taskDueDate
                isCompleted.isChecked = task.completed
                taskTitle.paint.isStrikeThruText = task.completed
                taskDescription.paint.isStrikeThruText = task.completed
                if (task.completed){
                    taskDueDate.setTextColor(Color.RED)
                }

            }

            binding.listItemLayout.setOnClickListener {
                print("Clicked")
            }

        }

    }
}



interface onItemClickListener{
    fun onItemClicked(task: Task)
    fun onCheckedBoxClicked(task: Task, isChecked:Boolean)
}