package com.example.weather.settings

import android.app.Activity
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.*
import com.example.weather.LocationProvider
import com.example.weather.R


class SettingsActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {
    val locationService = LocationProvider(this)

    private val settingsFragment = SettingsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, settingsFragment)
            .commit()
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this)

        actionBar
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        setResult(Activity.RESULT_OK)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        locationService.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty())
            settingsFragment.toggleSwitch(grantResults[0] == PackageManager.PERMISSION_GRANTED)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            val locationPreference = preferenceScreen.findPreference<SwitchPreferenceCompat>("coordinates")
            val locationService = (activity as SettingsActivity).locationService
            toggleSwitch(locationService.hasLocationPermission())

            locationPreference?.setOnPreferenceClickListener {
                if(locationService.isPermissionDenied())
                    locationService.requestPermission()
                toggleSwitch(locationService.hasLocationPermission())
                return@setOnPreferenceClickListener true
            }
        }

        fun toggleSwitch(hasLocationPermission: Boolean) {
            val locationPreference = preferenceScreen.findPreference<SwitchPreferenceCompat>("coordinates")
            locationPreference?.isChecked = if (!hasLocationPermission) false
            else SharedPreferenceHolder(requireContext()).isGeolocationEnabled
        }
    }
}
