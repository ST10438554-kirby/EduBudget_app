package com.example.edubudget

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.edubudget.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // UX title
        title = "EduBudget Dashboard"

        // ---------------- UI BUTTONS ----------------
        val btnCategory = findViewById<Button>(R.id.btnCategory)
        val btnExpense = findViewById<Button>(R.id.btnExpense)
        val btnGoal = findViewById<Button>(R.id.btnGoal)
        val btnViewExpenses = findViewById<Button>(R.id.btnViewExpenses)

        btnViewExpenses.setOnClickListener {
            startActivity(Intent(this, ExpenseListActivity::class.java))
        }


        // ---------------- SUMMARY TEXT ----------------
        val totalExpensesText = findViewById<TextView>(R.id.totalExpensesText)
        val expenseCountText = findViewById<TextView>(R.id.expenseCountText)

        val db = AppDatabase.getDatabase(this)

        // ---------------- LOAD DASHBOARD DATA ----------------
        lifecycleScope.launch(Dispatchers.IO) {

            val expenses = db.expenseDao().getAllExpenses()

            var total = 0.0

            for (e in expenses) {
                total += 0.0
            }

            runOnUiThread {

                totalExpensesText.text = "Total Expenses: R${total}"
                expenseCountText.text = "Number of Expenses: ${expenses.size}"
            }
        }

        // ---------------- NAVIGATION ----------------
        btnCategory.setOnClickListener {
            startActivity(Intent(this, CategoryActivity::class.java))
            Toast.makeText(this, "Opening Categories", Toast.LENGTH_SHORT).show()
        }

        btnExpense.setOnClickListener {
            startActivity(Intent(this, ExpenseActivity::class.java))
            Toast.makeText(this, "Opening Expenses", Toast.LENGTH_SHORT).show()
        }

        btnGoal.setOnClickListener {
            startActivity(Intent(this, BudgetGoalActivity::class.java))
            Toast.makeText(this, "Opening Budget Goals", Toast.LENGTH_SHORT).show()
        }
    }
}