package com.example.edubudget

import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.edubudget.data.AppDatabase
import com.example.edubudget.data.Expense
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExpenseActivity : AppCompatActivity() {

    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense)

        val desc = findViewById<EditText>(R.id.desc)
        val date = findViewById<EditText>(R.id.date)
        val startTime = findViewById<EditText>(R.id.startTime)
        val endTime = findViewById<EditText>(R.id.endTime)
        val categorySpinner = findViewById<Spinner>(R.id.categorySpinner)
        val amount = findViewById<EditText>(R.id.amount)

        val imageView = findViewById<ImageView>(R.id.imageView)
        val pickBtn = findViewById<Button>(R.id.pickImageBtn)
        val saveBtn = findViewById<Button>(R.id.saveExpenseBtn)

        val db = AppDatabase.getDatabase(this)

        val pickImage = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri ->
            imageUri = uri
            imageView.setImageURI(uri)
        }

        pickBtn.setOnClickListener {
            pickImage.launch("image/*")
        }

        saveBtn.setOnClickListener {

            val selectedCategory = categorySpinner.selectedItem?.toString()

            if (selectedCategory.isNullOrEmpty()) {
                Toast.makeText(this, "Select category", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val expense = Expense(
                description = desc.text.toString(),
                date = date.text.toString(),
                startTime = startTime.text.toString(),
                endTime = endTime.text.toString(),
                category = selectedCategory,
                amount = amount.text.toString().toDoubleOrNull() ?: 0.0,
                imageUri = imageUri?.toString()
            )

            lifecycleScope.launch {

                try {
                    // ✔ run DB work on background thread
                    withContext(Dispatchers.IO) {
                        db.expenseDao().insert(expense)
                    }

                    // ✔ UI update on main thread automatically
                    Toast.makeText(
                        this@ExpenseActivity,
                        "Expense Saved Successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                    // ✔ clear fields
                    desc.text.clear()
                    date.text.clear()
                    startTime.text.clear()
                    endTime.text.clear()
                    amount.text.clear()
                    imageView.setImageResource(0)
                    imageUri = null

                } catch (e: Exception) {

                    Toast.makeText(
                        this@ExpenseActivity,
                        "Save Failed: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}