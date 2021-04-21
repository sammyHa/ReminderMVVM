package com.samimhakimi.remindermvvm.models
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tasks")
data class Task(
        @PrimaryKey(autoGenerate = true)
        @NonNull
        var id: Int = 0,
        var taskTitle: String,
        var taskDescription: String? = null,
        var taskDueDate:String?= null,
        var completed:Boolean = false

        //var time: String = SimpleDateFormat("h:mm a", Locale.US).format(Date()),
//      var isComplete:Boolean,


)
