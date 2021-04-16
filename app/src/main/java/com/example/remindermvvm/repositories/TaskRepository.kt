package com.example.remindermvvm.repositories

import com.example.remindermvvm.models.Task
import com.example.remindermvvm.source.local.TaskDao

class TaskRepository(private val dao:TaskDao) {
    val tasks = dao.getTask()

    suspend fun insert(task: Task){
        dao.insertTask(task)
    }

    suspend fun update(task: Task){
        dao.updateTask(task)
    }

    suspend fun delete(task: Task){
        dao.deleteTask(task)
    }

}