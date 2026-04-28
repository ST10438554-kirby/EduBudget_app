package com.example.edubudget

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val prefs = getSharedPreferences("USER_DATA", MODE_PRIVATE)

        val email = findViewById<EditText>(R.id.emailLogin)
        val password = findViewById<EditText>(R.id.passwordLogin)

        val loginBtn = findViewById<Button>(R.id.loginBtn)
        val createBtn = findViewById<Button>(R.id.goToCreate)

        loginBtn.setOnClickListener {

            val savedEmail = prefs.getString("email", null)
            val savedPassword = prefs.getString("password", null)

            if (savedEmail == null || savedPassword == null) {
                Toast.makeText(this, "Create profile first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (email.text.toString() == savedEmail &&
                password.text.toString() == savedPassword
            ) {

                startActivity(Intent(this, DashboardActivity::class.java))
                finish()

            } else {
                Toast.makeText(this, "Invalid login", Toast.LENGTH_SHORT).show()
            }
        }

        createBtn.setOnClickListener {
            startActivity(Intent(this, CreateProfileActivity::class.java))
        }
    }
}