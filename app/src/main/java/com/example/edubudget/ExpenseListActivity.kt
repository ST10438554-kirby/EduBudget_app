package com.example.edubudget

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.edubudget.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExpenseListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_list)

        title = "All Expenses"

        val listView = findViewById<ListView>(R.id.expenseListView)
        val db = AppDatabase.getDatabase(this)

        lifecycleScope.launch(Dispatchers.IO) {

            val expenses = db.expenseDao().getAllExpenses()

            val displayList = expenses.map {
                "${it.description} - R${it.amount} - ${it.date}"
            }

            runOnUiThread {

                val adapter = ArrayAdapter(
                    this@ExpenseListActivity,
                    android.R.layout.simple_list_item_1,
                    displayList
                )

                listView.adapter = adapter
            }
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            Toast.makeText(this, "Clicked item ${position + 1}", Toast.LENGTH_SHORT).show()
        }
    }
}