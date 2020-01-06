package com.inits.ng.weeklygoals.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "goal_table")
data class Goal(

    @Embedded
    val week: Week,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    var isCompleted: Boolean = false,

    var title: String,

    var message: String
) {
    fun isActiveGoal(): Boolean {
        val time = Date(Calendar.getInstance().timeInMillis)
        return (time.after(Date(week.endStamp)) || time.before(Date(week.startStamp)))
    }
}