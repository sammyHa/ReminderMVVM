package com.example.remindermvvm.models
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int,
     var taskTitle:String,
     var taskDescription:String,
//     var taskDueDate:Date,
//     var isComplete:Boolean,


)
