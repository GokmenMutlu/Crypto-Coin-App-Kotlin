package com.gokmenmutlu.example4ccyptoapp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {

    fun setTimeText(time : Long) : String {
        val date = Date(time+10800000) // 3 saati milisaniye cinsinden ekliyoruz Türkiye saatini elde edebilmek icin UTC+3
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        val formatTime = formatter.format(date)
       return formatTime
    }

}