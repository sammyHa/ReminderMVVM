package com.example.remindermvvm.models
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int,
    var taskTitle:String,
    var taskDescription: String?=null,
//     var taskDueDate:Date,
//     var isComplete:Boolean,


)
