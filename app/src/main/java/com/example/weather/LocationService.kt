package com.example.weather

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat

class LocationService(val context: Context) {

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(): Location? {
        val location: Location?

        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        return location
    }

    fun checkLocationPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(
                    context,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    1
                )
            }
            else {
                ActivityCompat.requestPermissions(context, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    1)
            }
            return false
        }
        else return true
    }
}