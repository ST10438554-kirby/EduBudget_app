package com.example.edubudget

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.edubudget.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardActivity : AppCompatActivity() {

    private lateinit var totalText: TextView
    private lateinit var countText: TextView
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        db = AppDatabase.getDatabase(this)

        // ✅ ADD THIS LINE HERE (TEST DATA SEED)
        DatabaseSeeder.seed(this)

        totalText = findViewById(R.id.totalExpensesText)
        countText = findViewById(R.id.expenseCountText)

        findViewById<Button>(R.id.btnExpense).setOnClickListener {
            startActivity(Intent(this, ExpenseActivity::class.java))
        }

        findViewById<Button>(R.id.btnCategory).setOnClickListener {
            startActivity(Intent(this, CategoryActivity::class.java))
        }

        findViewById<Button>(R.id.btnGoal).setOnClickListener {
            startActivity(Intent(this, BudgetGoalActivity::class.java))
        }

        findViewById<Button>(R.id.btnViewExpenses).setOnClickListener {
            startActivity(Intent(this, ExpenseListActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        loadDashboard()
    }

    private fun loadDashboard() {

        lifecycleScope.launch(Dispatchers.IO) {

            val expenses = db.expenseDao().getAllExpenses()

            val count = expenses.size
            val total = expenses.sumOf { it.amount }

            withContext(Dispatchers.Main) {
                totalText.text = "Total Expenses: R%.2f".format(total)
                countText.text = "Number of Expenses: $count"
            }
        }
    }
}