package com.inits.ng.weeklygoals

import android.app.Application
import com.inits.ng.weeklygoals.di.repositoryModule
import com.inits.ng.weeklygoals.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin(){
        startKoin {
            androidContext(this@App)
            modules(listOf(repositoryModule, viewModelModule))
        }
    }
}