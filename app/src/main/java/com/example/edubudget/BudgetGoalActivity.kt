package com.example.edubudget

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.edubudget.data.AppDatabase
import com.example.edubudget.data.BudgetGoal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class BudgetGoalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget_goal)

        // UX: title + back button
        title = "Budget Goal"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val minSeek = findViewById<SeekBar>(R.id.minSeek)
        val maxSeek = findViewById<SeekBar>(R.id.maxSeek)

        val minText = findViewById<TextView>(R.id.minText)
        val maxText = findViewById<TextView>(R.id.maxText)

        val saveBtn = findViewById<Button>(R.id.saveGoalBtn)

        val db = AppDatabase.getDatabase(this)

        val format = NumberFormat.getCurrencyInstance(Locale("en", "ZA"))

        var minValue = 0.0
        var maxValue = 0.0

        minSeek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                minValue = progress.toDouble()
                minText.text = "Min: ${format.format(minValue)}"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        maxSeek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                maxValue = progress.toDouble()
                maxText.text = "Max: ${format.format(maxValue)}"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        saveBtn.setOnClickListener {

            // UX validation
            if (minValue >= maxValue) {
                Toast.makeText(this, "Min must be less than Max", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            saveBtn.isEnabled = false

            val goal = BudgetGoal(
                minAmount = minValue,
                maxAmount = maxValue
            )

            lifecycleScope.launch(Dispatchers.IO) {
                db.budgetGoalDao().insert(goal)

                runOnUiThread {
                    saveBtn.isEnabled = true
                    Toast.makeText(this@BudgetGoalActivity, "Budget Goal Saved", Toast.LENGTH_SHORT).show()
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