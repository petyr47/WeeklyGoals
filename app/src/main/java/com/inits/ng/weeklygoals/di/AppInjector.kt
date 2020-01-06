package com.inits.ng.weeklygoals.di

import com.inits.ng.weeklygoals.data.AppDatabase
import com.inits.ng.weeklygoals.goals.GoalRepository
import com.inits.ng.weeklygoals.goals.GoalViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val repositoryModule = module {
    single {  AppDatabase.getInstance(get()) }
    single { GoalRepository(get()) }
}

val viewModelModule = module {
    viewModel { GoalViewModel(get()) }
}