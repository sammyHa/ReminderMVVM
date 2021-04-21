package com.samimhakimi.remindermvvm.source.local

import com.samimhakimi.remindermvvm.models.Task

class DataGenerator {
    companion object{
        fun getTasks():List<Task>{
            return listOf(
                Task(0,"Follow Up with Cris",
                    "Email Cris to find out the shippping me time",
                    "01,12,2021",true)
            )
        }
    }
}