package com.example.remindermvvm

interface TaskListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message:String)
}