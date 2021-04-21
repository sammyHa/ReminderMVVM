package com.samimhakimi.remindermvvm.viewModels

import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samimhakimi.remindermvvm.TaskListener
import com.samimhakimi.remindermvvm.models.Task
import com.samimhakimi.remindermvvm.repositories.TaskRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(private val taskRepository: TaskRepository):ViewModel(), Observable{

    val taskDao = taskRepository.tasks
    lateinit var editableTask : Task
    var taskListener: TaskListener? = null

    private val taskEvenChannel = Channel<TaskEvent>()
    val taskEvent = taskEvenChannel.receiveAsFlow()

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

    fun updateTask(){
        inputTask.value = editableTask.taskTitle
        inputDescription.value = editableTask.taskDescription
        datePicker.value = editableTask.taskDueDate
    }



    private fun insertTask(task: Task):Job = viewModelScope.launch {
            taskRepository.insert(task)
        }

    private fun update(task: Task): Job = viewModelScope.launch {
        taskRepository.update(task)
    }

     fun delete(task: Task):Job = viewModelScope.launch {
        taskRepository.delete(task)
         taskEvenChannel.send(TaskEvent.ShowUndoDeleteMessage(task))
    }
    fun onUndoDeleted(task: Task): Job = viewModelScope.launch {
        insertTask(task)
    }

    fun onTaskSelected(task: Task){}
    fun onTaskCheckedChanged(task: Task, isChecked:Boolean) = viewModelScope.launch {
        update(task.copy(completed = isChecked))
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    sealed class TaskEvent{
        data class ShowUndoDeleteMessage(val task: Task):TaskEvent()
    }

}