package com.inits.ng.weeklygoals

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.inits.ng.weeklygoals.di.repositoryModule
import com.inits.ng.weeklygoals.di.viewModelModule
import com.inits.ng.weeklygoals.workmanager.GoalNotificationWorker
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.concurrent.TimeUnit

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
        registerWorker()
    }

    private fun initKoin(){
        startKoin {
            androidContext(this@App)
            modules(listOf(repositoryModule, viewModelModule))
        }
    }

    private fun registerWorker(){
        val work = PeriodicWorkRequestBuilder<GoalNotificationWorker>(16, TimeUnit.MINUTES)
            .addTag("goal-notif-work")
            .build()


        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "goal-notif-work",
            ExistingPeriodicWorkPolicy.REPLACE,
            work
        )


    }
}