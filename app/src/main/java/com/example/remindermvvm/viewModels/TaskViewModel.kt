package com.example.remindermvvm.viewModels

import android.widget.CheckBox
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remindermvvm.databinding.TaskItemsBinding
import com.example.remindermvvm.models.Task
import com.example.remindermvvm.repositories.TaskRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable.isCompleted
import kotlinx.coroutines.launch
import java.util.*

class TaskViewModel(private val taskRepository: TaskRepository):ViewModel(),Observable{

    val task = taskRepository.tasks

    @Bindable
    val inputTask = MutableLiveData<String>()

    @Bindable
    val inputDescription = MutableLiveData<String>()

    @Bindable
    val saveButton = MutableLiveData<String>()



    init {

        saveButton.value = "SAVE"
    }

    fun completed(){
        val title = inputTask.value!!
        val description = inputDescription.value!!
        delete(Task(id = 0, taskTitle = title, description))
    }



    fun saveOrUpdate(){
        val title = inputTask.value!!
        val description = inputDescription.value!!
        insert(Task(id = 0, title, description))
        inputTask.value = null
        inputDescription.value = null

    }

    fun insert(task: Task):Job = viewModelScope.launch {
            taskRepository.insert(task)
        }

    fun update(task: Task): Job = viewModelScope.launch {
        taskRepository.update(task)
    }

    fun delete(task: Task):Job = viewModelScope.launch {
        taskRepository.delete(task)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}