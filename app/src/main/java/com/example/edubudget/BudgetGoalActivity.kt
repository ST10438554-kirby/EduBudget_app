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

            val goal = BudgetGoal(
                minAmount = minValue,
                maxAmount = maxValue
            )

            lifecycleScope.launch(Dispatchers.IO) {
                db.budgetGoalDao().insert(goal)
            }

            Toast.makeText(this, "Budget Goal Saved", Toast.LENGTH_SHORT).show()
        }
    }
}