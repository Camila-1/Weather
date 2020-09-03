package com.example.weather.network

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

class NetworkProvider @Inject constructor(private val context: Context) {

    fun hasNetworkConnection(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }
}