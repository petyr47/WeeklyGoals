package com.inits.ng.weeklygoals.goals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class InputViewModel(private val repository: GoalRepository): ViewModel() {

    val header = MutableLiveData<String>()

    val title = MutableLiveData<String>()

    val description = MutableLiveData<String>()

    val errorMessage = MutableLiveData<String>()


    val startStamp = MutableLiveData<Long>()
    val endStamp = MutableLiveData<Long>()

    val displayDate = MutableLiveData<String>()













}