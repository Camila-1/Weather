package com.example.weather.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class WeatherDBHelper(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(Queries.CREATE_WEATHER_DATA_TABLE)
        db?.execSQL(Queries.CREATE_CITY_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(Queries.DELETE_TABLES)
        onCreate(db)
    }

    companion object {
        private val DB_NAME = "weather.db"
        private val DB_VERSION = 1


    }


}