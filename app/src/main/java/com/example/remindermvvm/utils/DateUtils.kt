package com.example.remindermvvm.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    @SuppressLint("SimpleDateFormat")
    fun Date.toSimpleString():String{
        val format = SimpleDateFormat("dd/MM/yyy")
        return format.format(this)
    }
}