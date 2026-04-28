package com.example.edubudget.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExpenseDao {

    @Insert
    suspend fun insert(expense: Expense)

    // ✅ FIX: renamed to match Dashboard usage
    @Query("SELECT * FROM expense")
    suspend fun getAllExpenses(): List<Expense>
}