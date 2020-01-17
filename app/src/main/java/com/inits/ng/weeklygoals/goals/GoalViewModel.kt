package com.inits.ng.weeklygoals.goals

import androidx.lifecycle.ViewModel

class GoalViewModel(private val repository: GoalRepository) : ViewModel() {

    val goals = repository.observeGoals()



}