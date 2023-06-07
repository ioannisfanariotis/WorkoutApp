package com.example.workoutapp.views.activities

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapp.R
import com.example.workoutapp.databinding.ActivityExerciseBinding
import com.example.workoutapp.databinding.LeaveConfirmBinding
import com.example.workoutapp.utils.Constants
import com.example.workoutapp.utils.Exercises
import com.example.workoutapp.views.adapters.AdapterNumber
import java.util.*

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var binding: ActivityExerciseBinding? = null
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var restTimeDuration: Long = 1
    private var restTimer2: CountDownTimer? = null
    private var restProgress2 = 0
    private var exerciseTimeDuration: Long = 1
    private var exerciseList: ArrayList<Exercises>? = null
    private var currentExercise = -1
    private var tts: TextToSpeech? = null
    private var player: MediaPlayer?= null
    private var adapter: AdapterNumber? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbar)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        exerciseList = Constants.getExercises()

        binding?.toolbar?.setNavigationOnClickListener{
            customDialogForBackButton()
        }
        tts= TextToSpeech(this, this)
        resting()
        setUpNumberRecyclerView()
    }

    private fun setUpNumberRecyclerView(){
        binding?.exerciseNumber?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter= AdapterNumber(exerciseList!!)
        binding?.exerciseNumber?.adapter = adapter
    }

    private fun resting(){
        try{
            val sound = Uri.parse("android.resource://com.example.workoutapp/" + R.raw.press_start)
            player = MediaPlayer.create(applicationContext, sound)
            player?.isLooping = false
            player?.start()
        }catch (e: Exception){
            e.printStackTrace()
        }
        binding?.firstLayout?.visibility = View.VISIBLE
        binding?.getReady?.visibility = View.VISIBLE
        binding?.upcoming?.visibility = View.VISIBLE
        binding?.next?.visibility = View.VISIBLE
        binding?.exerciseName?.visibility = View.INVISIBLE
        binding?.secondLayout?.visibility = View.INVISIBLE
        binding?.exerciseImage?.visibility = View.INVISIBLE
        if(restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }
        binding?.next?.text = exerciseList!![currentExercise+1].getName()
        restingProgressBar()
    }

    private fun restingProgressBar(){
        binding?.firstProgressBar?.progress = restProgress
        restTimer = object: CountDownTimer(restTimeDuration*10000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.firstProgressBar?.progress = 10 - restProgress
                binding?.firstTimer?.text = (10 - restProgress).toString()
            }
            @SuppressLint("NotifyDataSetChanged")
            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "Start of Exercise", Toast.LENGTH_SHORT).show()
                currentExercise++
                exerciseList!![currentExercise].setIsSelected(true)
                adapter?.notifyDataSetChanged()
                exerciseSetUp()
            }
        }.start()
    }

    private fun exerciseSetUp(){
        binding?.firstLayout?.visibility = View.INVISIBLE
        binding?.getReady?.visibility = View.INVISIBLE
        binding?.upcoming?.visibility = View.INVISIBLE
        binding?.next?.visibility = View.INVISIBLE
        binding?.exerciseName?.visibility = View.VISIBLE
        binding?.secondLayout?.visibility = View.VISIBLE
        binding?.exerciseImage?.visibility = View.VISIBLE
        if(restTimer2 != null){
            restTimer2?.cancel()
            restProgress2 = 0
        }

        speakOut(exerciseList!![currentExercise].getName())

        binding?.exerciseImage?.setImageResource(exerciseList!![currentExercise].getImage())
        binding?.exerciseName?.text = exerciseList!![currentExercise].getName()
        exerciseProgressBar()
    }

    private fun exerciseProgressBar() {
        binding?.secondProgressBar?.progress = restProgress2
        restTimer2 = object : CountDownTimer(exerciseTimeDuration*30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress2++
                binding?.secondProgressBar?.progress = 30 - restProgress2
                binding?.secondTimer?.text = (30 - restProgress2).toString()
            }
            @SuppressLint("NotifyDataSetChanged")
            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "End of Exercise, now rest", Toast.LENGTH_SHORT).show()
                if(currentExercise < exerciseList?.size!! - 1){
                    exerciseList!![currentExercise].setIsSelected(false)
                    exerciseList!![currentExercise].setIsCompleted(true)
                    adapter?.notifyDataSetChanged()
                    resting()
                }else{
                    Toast.makeText(this@ExerciseActivity, "Congratulations, you finished it!", Toast.LENGTH_SHORT).show()
                    finish()
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                }
            }
        }.start()
    }

    override fun onInit(status: Int) {
        if(status==TextToSpeech.SUCCESS){
            val result = tts?.setLanguage(Locale.US)
            if(result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED){
                Toast.makeText(this, "Language not supported", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Initialization Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun customDialogForBackButton() {
        val customDialog = Dialog(this)
        val dialogBinding = LeaveConfirmBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.yes.setOnClickListener {
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.no.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        customDialogForBackButton()
        //super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        if (tts != null) {
            tts?.stop()
            tts?.shutdown()
        }
        if(player!=null){
            player?.stop()
        }
        binding = null
    }
}