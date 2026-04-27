package com.example.edubudget

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val btnCategory = findViewById<Button>(R.id.btnCategory)
        val btnExpense = findViewById<Button>(R.id.btnExpense)
        val btnGoal = findViewById<Button>(R.id.btnGoal)

        btnCategory.setOnClickListener {
            startActivity(Intent(this, CategoryActivity::class.java))
        }

        btnExpense.setOnClickListener {
            startActivity(Intent(this, ExpenseActivity::class.java))
        }

        btnGoal.setOnClickListener {
            startActivity(Intent(this, BudgetGoalActivity::class.java))
        }
    }
}