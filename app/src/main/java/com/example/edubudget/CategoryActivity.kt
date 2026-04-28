package com.example.edubudget

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.edubudget.data.AppDatabase
import com.example.edubudget.data.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoryActivity : AppCompatActivity() {

    private lateinit var categoryName: EditText
    private lateinit var saveBtn: Button
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        title = "Add Category"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        categoryName = findViewById(R.id.categoryName)
        saveBtn = findViewById(R.id.saveCategoryBtn)

        db = AppDatabase.getDatabase(this)

        saveBtn.setOnClickListener {
            saveCategory()
        }
    }

    private fun saveCategory() {

        val name = categoryName.text.toString().trim()

        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter category name", Toast.LENGTH_SHORT).show()
            return
        }

        saveBtn.isEnabled = false

        lifecycleScope.launch(Dispatchers.IO) {

            try {
                db.categoryDao().insert(Category(name = name))

                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@CategoryActivity,
                        "Category Saved",
                        Toast.LENGTH_SHORT
                    ).show()

                    categoryName.text.clear()
                }

            } catch (e: Exception) {

                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@CategoryActivity,
                        "Category already exists",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } finally {

                withContext(Dispatchers.Main) {
                    saveBtn.isEnabled = true
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}