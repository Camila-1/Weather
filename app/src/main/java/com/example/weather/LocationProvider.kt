package com.example.weather

import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Looper
import android.util.Log
import android.widget.Toast


class LocationService(val activity: Activity) {
    private val REQUEST_CODE = 1

    fun startLocationUpdates() {
        if (hasLocationPermission())
            requestLocation()
    }

    fun requestLocation() {
        val fusedLocationProviderClient = FusedLocationProviderClient(activity)
        val request = createLocationRequest()
        val callback: LocationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult?) {
                result ?: return
                result.lastLocation ?: return
                val location = result.lastLocation
                SharedPreferenceHolder(activity).coordinates = mapOf("lon" to location.longitude.toString(), "lat" to location.latitude.toString())
                fusedLocationProviderClient.removeLocationUpdates(this)
            }
        }
        fusedLocationProviderClient.requestLocationUpdates(request, callback, Looper.getMainLooper())
    }

    fun createLocationRequest():LocationRequest {
        return LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    fun requestPermission() {
        ActivityCompat.requestPermissions(activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
            REQUEST_CODE)
    }

    fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    fun isPermissionDenied(): Boolean {
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED ||
                ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        Log.i("permissions", grantResults.toString())
        if (requestCode != REQUEST_CODE || permissions.isEmpty()) return
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            SharedPreferenceHolder(activity).isGeolocationEnabled = true
            requestLocation()
        }
        else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION) ||
                ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                val listener = DialogInterface.OnClickListener {dialog, _ ->
                    dialog.cancel()
                    requestPermission()
                }
                AlertDialog.Builder(activity)
                    .setTitle("title")
                    .setMessage("message")
                    .setNegativeButton("No", null)
                    .setPositiveButton("Ok", listener)
            }
            else {
                Toast.makeText(activity, R.string.location_permission_denied_message, Toast.LENGTH_LONG).show()
            }
        }
    }
}
