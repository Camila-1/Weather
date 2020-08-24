package com.example.weather

import android.app.Application
import android.content.Context
import ua.com.cuteteam.cutetaxi.R

class AppClass: Application() {

    init {
        instance = this
    }

    companion object {

        private var instance: AppClass? = null

        fun appContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        this.setTheme(R.style.AppTheme)
    }
}
