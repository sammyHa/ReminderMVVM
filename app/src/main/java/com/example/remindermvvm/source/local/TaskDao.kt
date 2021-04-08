package com.example.remindermvvm.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.remindermvvm.models.Task

@Dao
interface TaskDao {

    @Insert
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM tasks")
    fun getTask():LiveData<List<Task>>
}