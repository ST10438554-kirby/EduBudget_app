package com.example.edubudget

import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.edubudget.data.AppDatabase
import com.example.edubudget.data.Expense
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExpenseActivity : AppCompatActivity() {

    var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense)

        val desc = findViewById<EditText>(R.id.desc)
        val date = findViewById<EditText>(R.id.date)
        val startTime = findViewById<EditText>(R.id.startTime)
        val endTime = findViewById<EditText>(R.id.endTime)
        val category = findViewById<EditText>(R.id.category)

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

            val expense = Expense(
                description = desc.text.toString(),
                date = date.text.toString(),
                startTime = startTime.text.toString(),
                endTime = endTime.text.toString(),
                category = category.text.toString(),
                imageUri = imageUri?.toString()
            )

            CoroutineScope(Dispatchers.IO).launch {
                db.expenseDao().insert(expense)
            }

            Toast.makeText(this, "Expense Saved", Toast.LENGTH_SHORT).show()
        }
    }
}