package com.example.edubudget

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.edubudget.data.AppDatabase
import com.example.edubudget.data.Category
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        // UX: title + back button
        title = "Add Category"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val categoryName = findViewById<EditText>(R.id.categoryName)
        val saveBtn = findViewById<Button>(R.id.saveCategoryBtn)

        val db = AppDatabase.getDatabase(this)

        saveBtn.setOnClickListener {

            val name = categoryName.text.toString().trim()

            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter category name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            saveBtn.isEnabled = false

            val category = Category(name = name)

            CoroutineScope(Dispatchers.IO).launch {
                db.categoryDao().insert(category)

                runOnUiThread {
                    saveBtn.isEnabled = true
                    Toast.makeText(this@CategoryActivity, "Category Saved", Toast.LENGTH_SHORT).show()
                    categoryName.text.clear()
                }
            }
        }
    }

    // UX: back button support
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}