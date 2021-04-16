package com.example.remindermvvm.models
import android.annotation.SuppressLint
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tasks")
data class Task(
        @PrimaryKey(autoGenerate = true)
        @NonNull
        var id: Int,
        var taskTitle: String,
        var taskDescription: String? = null,
        var taskDueDate:String?=null,

        @SuppressLint("SimpleDateFormat")
        var date: String? = null,

        //var time: String = SimpleDateFormat("h:mm a", Locale.US).format(Date()),
//      var isComplete:Boolean,


)
