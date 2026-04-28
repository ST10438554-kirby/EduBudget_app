package com.example.edubudget

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class CreateProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_profile)

        val prefs = getSharedPreferences("USER_DATA", MODE_PRIVATE)

        val name = findViewById<EditText>(R.id.name)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val createBtn = findViewById<Button>(R.id.createBtn)

        createBtn.setOnClickListener {

            if (email.text.isEmpty() || password.text.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            prefs.edit().apply {
                putString("name", name.text.toString())
                putString("email", email.text.toString())
                putString("password", password.text.toString())
                apply()
            }

            Toast.makeText(this, "Profile Created", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}