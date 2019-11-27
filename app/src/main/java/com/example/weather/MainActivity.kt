package com.example.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import com.example.weather.fragments.WeatherListFragment
import androidx.work.WorkManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment, WeatherListFragment())

        val workManager = WorkManager.getInstance()
        val periodicWork = OneTimeWorkRequest.Builder(WeatherWorker::class.java)
    }
}
