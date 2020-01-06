package com.inits.ng.weeklygoals.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GoalDao {

    @Insert
    suspend fun saveGoal(goal : Goal)

    @Query("DELETE FROM goal_table WHERE id == :id")
    suspend fun deleteGoal(id: Int)

    @Update
    suspend fun updateGoal(goal: Goal): Int

    @Query("SELECT * FROM goal_table")
    suspend fun observeAllGoals(): LiveData<List<Goal>>

    @Query("SELECT * FROM goal_table")
    suspend fun fetchAllGoals(): List<Goal>

    @Query("SELECT * FROM goal_table WHERE id = :id")
    suspend fun fetchGoal(id: Int): Goal

    @Query("SELECT * FROM goal_table WHERE isCompleted = :value")
    suspend fun fetchCompletedGoals(value : Boolean = true) :List<Goal>

}