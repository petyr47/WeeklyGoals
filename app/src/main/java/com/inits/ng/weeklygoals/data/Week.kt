package com.inits.ng.weeklygoals.data

data class Week(

    val startDate : String,

    val endDate: String,

    val startStamp: Long,

    val endStamp: Long,

    val dateString: String,

    var index: Int = 1
)