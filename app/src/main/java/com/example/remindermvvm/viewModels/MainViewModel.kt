package com.example.remindermvvm.viewModels

import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.remindermvvm.TaskListener
import com.example.remindermvvm.models.Task
import com.example.remindermvvm.repositories.TaskRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(private val taskRepository: TaskRepository):ViewModel(), Observable{

    val task = taskRepository.tasks
    lateinit var editableTask : Task
    var taskListener: TaskListener? = null

    @Bindable
    val inputTask = MutableLiveData<String>()
    @Bindable
    val inputDescription = MutableLiveData<String>()
    @Bindable
    var datePicker  = MutableLiveData<String>()

    @Bindable
    var date_holder = MutableLiveData<String>()


    fun saveOrUpdate(view: View){
        val title = inputTask.value
        val description = inputDescription.value
        val date = datePicker.value
        if (title!!.trim().isNotBlank()) {
            insertTask(Task(id = 0, title, description, date))
            inputTask.value = null
            inputDescription.value = null
            datePicker.value = null

            taskListener?.onSuccess()
        }
        else{
            taskListener?.onFailure("Title cannot be empty")
            return
        }
    }



    private fun insertTask(task: Task):Job = viewModelScope.launch {
            taskRepository.insert(task)
        }

    private fun update(task: Task): Job = viewModelScope.launch {
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