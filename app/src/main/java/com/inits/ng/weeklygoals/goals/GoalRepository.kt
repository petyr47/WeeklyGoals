package com.inits.ng.weeklygoals.goals

import androidx.lifecycle.LiveData
import com.inits.ng.weeklygoals.data.AppDatabase
import com.inits.ng.weeklygoals.data.Goal
import kotlin.Exception

class GoalRepository(private val db: AppDatabase) {

    private val dao = db.dao()


    suspend fun fetchAllGoals(): List<Goal> =
        try {
            dao.fetchAllGoals()
        } catch (e: Exception) {
            emptyList()
        }


    suspend fun insertGoal(goal: Goal): Boolean =
        try {
            dao.saveGoal(goal)
            true
        } catch (e: Exception) {
            false
        }


    fun observeGoals(): LiveData<List<Goal>>? {
        try {
            return dao.observeAllGoals()
        } catch (e: Exception) {
            return null
        }
    }

    suspend fun fetchGoal(id: Int): Goal? {
        return try {
            dao.fetchGoal(id)
        } catch (e: Exception) {
            null
        }
    }


    suspend fun updateGoal(goal: Goal): Boolean =
        try {
            dao.updateGoal(goal)
            true
        } catch (e: Exception) {
            false
        }


    suspend fun deleteGoal(goal: Goal): Boolean =
        try {
            dao.removeGoal(goal)
            true
        } catch (e: Exception) {
            false
        }

}