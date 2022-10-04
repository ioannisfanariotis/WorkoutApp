package com.example.workoutapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapp.databinding.ActivityHistoryBinding
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    private var binding: ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.historyToolbar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "History"
        }
        binding?.historyToolbar?.setNavigationOnClickListener {
            onBackPressed()
        }

        val historyDao = (application as WorkoutApp).db.historyDao()
        getDates(historyDao)
    }

    private fun getDates(historyDao: HistoryDao) {
        lifecycleScope.launch {
            historyDao.fetchAllDates().collect { allDates ->
                if (allDates.isNotEmpty()) {
                    binding?.completed?.visibility = View.VISIBLE
                    binding?.rvList?.visibility = View.VISIBLE
                    binding?.noData?.visibility = View.GONE
                    binding?.rvList?.layoutManager = LinearLayoutManager(this@HistoryActivity)
                    val dates = ArrayList<String>()
                    for (i in allDates) {
                        dates.add(i.date)
                    }
                    val historyAdapter = Adapter(dates)
                    binding?.rvList?.adapter = historyAdapter
                } else {
                    binding?.completed?.visibility = View.GONE
                    binding?.rvList?.visibility = View.GONE
                    binding?.noData?.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}