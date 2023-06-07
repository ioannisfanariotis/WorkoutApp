package com.example.workoutapp.views.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.workoutapp.R
import com.example.workoutapp.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    companion object {
        private const val METRIC_VIEW = "METRIC_VIEW"
        private const val US_VIEW = "US_VIEW"
    }

    private var currentView: String = "METRIC_VIEW"
    private var binding: ActivityBmiBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.bmitoolbar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Calculate BMI"
        }
        binding?.bmitoolbar?.setNavigationOnClickListener {
            onBackPressed()
        }

        metricVisibility()

        binding?.units?.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.metricUnit)
                metricVisibility()
            else
                usVisibility()
        }

        binding?.calculate?.setOnClickListener {
            if (checkValidation()) {
                if (currentView == "METRIC_VIEW") {
                    val bmiWeight: Float = binding?.weight?.text.toString().toFloat()
                    val bmiHeight: Float = binding?.height?.text.toString().toFloat() / 100
                    val bmi = bmiWeight / (bmiHeight * bmiHeight)
                    allResults(bmi)
                } else {
                    val bmiWeight: Float = binding?.usWeight?.text.toString().toFloat()
                    val bmiFeet: Float = binding?.feet?.text.toString().toFloat()
                    val bmiInch: Float = binding?.inch?.text.toString().toFloat()
                    val bmiHeight: Float = bmiInch + bmiFeet * 12
                    val bmi = 703 * (bmiWeight / bmiHeight * bmiHeight)
                    allResults(bmi)
                }
            }
        }
    }

    private fun metricVisibility() {
        currentView = METRIC_VIEW
        binding?.MetricUnitWeight?.visibility = View.VISIBLE
        binding?.MetricUnitHeight?.visibility = View.VISIBLE
        binding?.UsUnitWeight?.visibility = View.GONE
        binding?.usMBI?.visibility = View.GONE
        binding?.weight?.text?.clear()
        binding?.height?.text?.clear()
        binding?.allResults?.visibility = View.GONE
    }

    private fun usVisibility() {
        currentView = US_VIEW
        binding?.MetricUnitWeight?.visibility = View.INVISIBLE
        binding?.MetricUnitHeight?.visibility = View.INVISIBLE
        binding?.UsUnitWeight?.visibility = View.VISIBLE
        binding?.usMBI?.visibility = View.VISIBLE
        binding?.usWeight?.text?.clear()
        binding?.feet?.text?.clear()
        binding?.inch?.text?.clear()
        binding?.allResults?.visibility = View.GONE
    }

    private fun checkValidation(): Boolean {
        var flag = true
        if (currentView == "METRIC_VIEW") {
            if (binding?.weight?.text.toString().isEmpty()) {
                flag = false
                Toast.makeText(this, "Please insert your weight", Toast.LENGTH_SHORT).show()
            } else if (binding?.height?.text.toString().isEmpty()) {
                flag = false
                Toast.makeText(this, "Please insert your height", Toast.LENGTH_SHORT).show()
            }
        } else {
            if (binding?.usWeight?.text.toString().isEmpty()) {
                flag = false
                Toast.makeText(this, "Please insert your weight", Toast.LENGTH_SHORT).show()
            } else if (binding?.feet?.text.toString().isEmpty()) {
                flag = false
                Toast.makeText(this, "Please insert your feet", Toast.LENGTH_SHORT).show()
            } else if (binding?.inch?.text.toString().isEmpty()) {
                flag = false
                Toast.makeText(this, "Please insert your inch", Toast.LENGTH_SHORT).show()
            }
        }
        return flag
    }

    private fun allResults(bmi: Float) {
        val result2: String
        val result3: String
        if (bmi.compareTo(15f) <= 0) {
            result2 = "Very underweight!"
            result3 = "You should eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(19f) <= 0) {
            result2 = "Underweight!"
            result3 = "You should eat more!"
        } else if (bmi.compareTo(19f) > 0 && bmi.compareTo(25f) <= 0) {
            result2 = "Normal!"
            result3 = "You are in a good shape!"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0) {
            result2 = "Overweight!"
            result3 = "You should workout more!"
        } else {
            result2 = "Very overweight!"
            result3 = "You should workout more!"
        }
        binding?.allResults?.visibility = View.VISIBLE
        val mbiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
        binding?.result1?.text = mbiValue
        binding?.result2?.text = result2
        binding?.result3?.text = result3
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}