package com.example.workoutapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.workoutapp.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {

    private var binding: ActivityFinishBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbar?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.finish?.setOnClickListener {
            finish()
        }

        val historyDao = (application as WorkoutApp).db.historyDao()
        addToDatabase(historyDao)
    }

    private fun addToDatabase(historyDao: HistoryDao) {

        val c = Calendar.getInstance()
        val dateTime = c.time
        Log.e("Date: ", "" + dateTime)
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dateTime)
        Log.e("Formatted Date: ", "" + date)

        lifecycleScope.launch {
            historyDao.insert((HistoryEntity(date)))
            Log.e("Date: ", "Added...")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}