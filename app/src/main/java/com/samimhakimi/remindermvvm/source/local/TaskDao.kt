package com.samimhakimi.remindermvvm.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.samimhakimi.remindermvvm.models.Task

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM tasks ORDER BY id DESC")
    fun getTask():LiveData<List<Task>>
}