package com.inits.ng.weeklygoals.workmanager

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.inits.ng.weeklygoals.goals.GoalRepository
import com.inits.ng.weeklygoals.utils.sendNotification
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*

class GoalNotificationWorker(private val context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters), KoinComponent {

    private val goalRepository: GoalRepository by inject()

    override suspend fun doWork(): Result = coroutineScope {
        try {
            val goals = goalRepository.fetchAllGoals()
            if (goals.isEmpty()) {
                Log.e("Goal Notif worker", "Goal worker failed because List of goals returned was empty")
                return@coroutineScope Result.failure()
            }
            goals.filter {
                it.isActiveGoal()
            }.forEach {
                val notificationManager = ContextCompat.getSystemService(
                    context,
                    NotificationManager::class.java
                ) as NotificationManager
                Log.e("Goal Notif worker", "Goal worker retrieved list of goals: and is going to send a notification for goal ::${it.title}")

                val message = "Hello, How's progress on ${it.title}. You must not let this goal exceed the alloted time"
                notificationManager.sendNotification(message, context)
                delay(300000)
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

}