package com.example.workoutapp

import android.app.Application
import com.example.workoutapp.database.AppDatabase

class App : Application() {
    val db by lazy {
        AppDatabase.getInstance(this)
    }
}