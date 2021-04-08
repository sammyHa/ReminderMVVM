package com.example.remindermvvm.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.remindermvvm.repositories.TaskRepository
import com.example.remindermvvm.viewModels.TaskViewModel
import java.lang.IllegalArgumentException

class TaskViewModelFactory(val taskRepository: TaskRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)){
            return TaskViewModel(taskRepository) as T
        }
        throw IllegalArgumentException("Unknow ViewModel Class")
    }

}