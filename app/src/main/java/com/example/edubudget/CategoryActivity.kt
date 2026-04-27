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

        val categoryName = findViewById<EditText>(R.id.categoryName)
        val saveBtn = findViewById<Button>(R.id.saveCategoryBtn)

        val db = AppDatabase.getDatabase(this)

        saveBtn.setOnClickListener {

            val name = categoryName.text.toString()

            if (name.isEmpty()) {
                Toast.makeText(this, "Enter category name", Toast.LENGTH_SHORT).show()
            } else {

                val category = Category(name = name)

                CoroutineScope(Dispatchers.IO).launch {
                    db.categoryDao().insert(category)
                }

                Toast.makeText(this, "Category Saved", Toast.LENGTH_SHORT).show()
                categoryName.text.clear()
            }
        }
    }
}