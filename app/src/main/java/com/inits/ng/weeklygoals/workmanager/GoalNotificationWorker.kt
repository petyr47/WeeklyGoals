package com.inits.ng.weeklygoals.workmanager

import android.app.NotificationManager
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.inits.ng.weeklygoals.goals.GoalRepository
import com.inits.ng.weeklygoals.utils.sendNotification
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import org.koin.core.KoinComponent
import org.koin.core.inject

class GoalNotificationWorker(private val context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters), KoinComponent {

    private val goalRepository: GoalRepository by inject()

    override suspend fun doWork(): Result = coroutineScope {
        try {
            val goals = goalRepository.fetchAllGoals()
            if (goals.isEmpty()) {
                return@coroutineScope Result.failure()
            }
            goals.filter {
                it.isActiveGoal()
            }.forEach {
                val notificationManager = ContextCompat.getSystemService(
                    context,
                    NotificationManager::class.java
                ) as NotificationManager

                notificationManager.sendNotification(it.title, context)
                delay(300000)
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

}