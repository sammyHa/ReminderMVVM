package com.example.remindermvvm.viewModels

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remindermvvm.models.Task
import com.example.remindermvvm.repositories.TaskRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(private val taskRepository: TaskRepository):ViewModel(), Observable{

    val task = taskRepository.tasks
    lateinit var editableTask : Task

    @Bindable
    val inputTask = MutableLiveData<String>()
    @Bindable
    val inputDescription = MutableLiveData<String>()

    @Bindable
    val datePicker  = MutableLiveData<String>()

    fun completed(){
        val title = inputTask.value!!
        val description = inputDescription.value!!
        val date = datePicker.value!!
        delete(Task(
            id = 0,
            taskTitle = title,
            taskDescription = description,
            date = date
        ))
    }


    fun saveOrUpdate(){
        val title = inputTask.value!!
        val description = inputDescription.value
        val date = datePicker.value
        insertTask(Task(id = 0, title, description, date))
        inputTask.value = null
        inputDescription.value = null

    }


    fun insertTask(task: Task):Job = viewModelScope.launch {
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