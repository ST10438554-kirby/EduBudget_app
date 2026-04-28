package com.example.edubudget

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // If already logged in → skip login screen
        val prefs = getSharedPreferences("USER_DATA", MODE_PRIVATE)
        val isLoggedIn = prefs.getBoolean("isLoggedIn", false)

        if (isLoggedIn) {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_login)

        val email = findViewById<EditText>(R.id.emailLogin)
        val password = findViewById<EditText>(R.id.passwordLogin)

        val loginBtn = findViewById<Button>(R.id.loginBtn)
        val goToCreate = findViewById<Button>(R.id.goToCreate)

        loginBtn.setOnClickListener {

            val savedEmail = prefs.getString("email", null)
            val savedPassword = prefs.getString("password", null)

            val inputEmail = email.text.toString().trim()
            val inputPassword = password.text.toString().trim()

            // No profile exists
            if (savedEmail == null || savedPassword == null) {
                Toast.makeText(
                    this,
                    "Please create profile first",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            // Empty fields
            if (inputEmail.isEmpty() || inputPassword.isEmpty()) {
                Toast.makeText(
                    this,
                    "Enter email and password",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Correct login
            if (inputEmail == savedEmail && inputPassword == savedPassword) {

                prefs.edit()
                    .putBoolean("isLoggedIn", true)
                    .apply()

                Toast.makeText(
                    this,
                    "Login Successful",
                    Toast.LENGTH_SHORT
                ).show()

                startActivity(Intent(this, DashboardActivity::class.java))
                finish()

            } else {

                Toast.makeText(
                    this,
                    "Incorrect email or password",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        goToCreate.setOnClickListener {
            startActivity(Intent(this, CreateProfileActivity::class.java))
        }
    }
}