package com.inits.ng.weeklygoals.data

data class Week(

    val startDate : String,

    val endDate: String,

    val startStamp: Long,

    val endStamp: Long,

    var index: Int = 1
)