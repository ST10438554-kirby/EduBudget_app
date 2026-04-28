package com.example.edubudget

import android.content.Context
import com.example.edubudget.data.AppDatabase
import com.example.edubudget.data.Category
import com.example.edubudget.data.Expense
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DatabaseSeeder {

    fun seed(context: Context) {

        val db = AppDatabase.getDatabase(context)

        CoroutineScope(Dispatchers.IO).launch {

            // Only seed once (prevents duplicates)
            if (db.categoryDao().getAllCategories().isNotEmpty()) return@launch

            // ---------- SAMPLE CATEGORIES ----------
            val categories = listOf(
                Category(name = "Food"),
                Category(name = "Transport"),
                Category(name = "School"),
                Category(name = "Entertainment")
            )

            categories.forEach {
                db.categoryDao().insert(it)
            }

            // ---------- SAMPLE EXPENSES ----------
            val expenses = listOf(
                Expense(
                    description = "Lunch",
                    date = "28/04/2026",
                    startTime = "12:00",
                    endTime = "12:30",
                    category = "Food",
                    amount = 50.0,
                    imageUri = null
                ),
                Expense(
                    description = "Taxi",
                    date = "28/04/2026",
                    startTime = "08:00",
                    endTime = "08:30",
                    category = "Transport",
                    amount = 30.0,
                    imageUri = null
                )
            )

            expenses.forEach {
                db.expenseDao().insert(it)
            }
        }
    }
}