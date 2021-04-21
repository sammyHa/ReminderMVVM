package com.samimhakimi.remindermvvm.repositories

import com.samimhakimi.remindermvvm.models.Task
import com.samimhakimi.remindermvvm.source.local.TaskDao

class TaskRepository(private val dao: TaskDao) {
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