package com.example.edubudget

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prefs = getSharedPreferences("loginPrefs", MODE_PRIVATE)

        // ✅ If already logged in, go straight to Dashboard
        if (prefs.getBoolean("isLoggedIn", false)) {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_main)

        val loginBtn = findViewById<Button>(R.id.loginBtn)

        loginBtn.setOnClickListener {

            // Save login state
            prefs.edit().putBoolean("isLoggedIn", true).apply()

            //  Open Dashboard ONCE
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }
    }
}