package com.example.weather.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [City::class, Weather::class], version = 2)
abstract class WeatherDataBase : RoomDatabase() {

    abstract fun cityDao(): CitiesDao

    abstract fun weatherDao(): WeatherDao


    companion object {
        private lateinit var INSTANCE: WeatherDataBase

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("BEGIN TRANSACTION;")
                database.execSQL("ALTER TABLE cities RENAME TO cities_old;")
                database.execSQL(
                    "CREATE TABLE cities" +
                            "(id TEXT PRIMARY KEY NOT NULL, name TEXT NOT NULL);".trimIndent()
                )
                database.execSQL("INSERT INTO cities (id, name) SELECT id, name FROM cities_old;")
                database.execSQL("DROP TABLE cities_old;")
                database.execSQL("COMMIT;")
            }
        }

        fun database(context: Context): WeatherDataBase {

            if (::INSTANCE.isInitialized) return INSTANCE

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    WeatherDataBase::class.java,
                    "weather_database"
                ).addMigrations(MIGRATION_1_2).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}