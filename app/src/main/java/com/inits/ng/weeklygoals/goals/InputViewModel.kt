package com.inits.ng.weeklygoals.goals

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inits.ng.weeklygoals.data.Goal
import com.inits.ng.weeklygoals.data.Week
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.inits.ng.weeklygoals.utils.*

class InputViewModel(private val repository: GoalRepository) : ViewModel() {

    val header = MutableLiveData<String>()

    val title = MutableLiveData<String>()

    val description = MutableLiveData<String>()

    val errorMessage = MutableLiveData<String>()

    var id = 0
    private val goalToBeEditted = MutableLiveData<Goal>()


    val startStamp = MutableLiveData<Long>()
    val endStamp = MutableLiveData<Long>()

    val displayDate = MutableLiveData<String>()

    val success = MutableLiveData<Boolean>()

    init {
        success.value = false
        header.value = "Add Goal"
    }


    fun makeEditMode() {
        header.value = "Edit Goal"

        viewModelScope.launch(Dispatchers.IO) {
            val goal = repository.fetchGoal(id)
            goalToBeEditted.postValue(goal)
            title.postValue(goal?.title)
            description.postValue(goal?.message)
            displayDate.postValue(goal?.week?.dateString)
            startStamp.postValue(goal?.week?.startStamp)
            endStamp.postValue(goal?.week?.endStamp)
        }
    }

    private fun validateFields(): Boolean {
        if (title.value.isNullOrBlank()) {
            errorMessage.value = "Title cannot be blank"
            return false
        }
        if (description.value.isNullOrBlank()) {
            errorMessage.value = "Description cannot be blank"
            return false
        }
        if (displayDate.value.isNullOrBlank()) {
            errorMessage.value = "Please select date range"
            return false
        }
        return true
    }


    fun saveGoalClicked() {
        if (validateFields()) {
            val week = Week(
                startDate = makeDateFromEpoch(startStamp.value!!),
                endDate = makeDateFromEpoch(endStamp.value!!),
                endStamp = endStamp.value!!,
                startStamp = startStamp.value!!,
                dateString = displayDate.value!!
            )
            if (id == 0) {

                val goal = Goal(
                    week = week,
                    title = title.value!!,
                    message = description.value!!
                )
                viewModelScope.launch(Dispatchers.IO) {
                    val result = repository.insertGoal(goal)
                    success.postValue(result)
                    if (result) {
                        errorMessage.postValue("Goal saved!")
                    } else {
                        errorMessage.postValue("Goal could not be saved!")
                    }
                }
            } else {
                val edittedGoal = goalToBeEditted.value!!.copy(
                    week = week,
                    title = title.value!!,
                    message = description.value!!
                )
                viewModelScope.launch(Dispatchers.IO) {

                    val result = repository.updateGoal(goal = edittedGoal)
                    success.postValue(result)
                    if (result) {
                        errorMessage.postValue("Goal updated!")
                    } else {
                        errorMessage.postValue("Goal could not be updated!")
                    }
                }
            }
        }
    }


}