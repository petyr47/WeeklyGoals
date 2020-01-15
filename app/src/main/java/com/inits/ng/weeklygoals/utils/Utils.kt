package com.inits.ng.weeklygoals.utils

import java.text.SimpleDateFormat
import java.util.*


fun makeDateFromEpoch(epoch : Long): String{
    val dateTime = Date(epoch * 1000)
    val sdf = SimpleDateFormat("EEEE,MMMM d", Locale.ENGLISH)
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"))
    return sdf.format(dateTime)
}