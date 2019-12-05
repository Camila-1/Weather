package com.example.weather

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.preference.PreferenceManager
import com.example.weather.fragments.WeatherDetailsFragment
import com.example.weather.fragments.WeatherListFragment
import com.example.weather.response.WeatherData
import com.example.weather.response.WeatherResponse
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    companion object {
        private val REQUEST_CODE = 1
    }
    private var checkedItem: WeatherData? = null
    private var sharedPreferences = lazy { PreferenceManager.getDefaultSharedPreferences(this) }

    private var listWeatherData: List<WeatherData> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false)

        if (listWeatherData.isEmpty())
            updateWeatherData()

        checkedItem = savedInstanceState?.getParcelable("item")
    }

    fun updateWeatherData() {
        val serviceBuilder = ServiceBuilder(this)
        val weatherService = serviceBuilder.weatherService()

        val response = getWeatherResponse(weatherService)

        response.enqueue(object: Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                listWeatherData = response.body()?.list ?: emptyList()
                render(checkedItem)
                progress_bar.visibility = View.INVISIBLE
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.e("WeatherListFragment", t.toString(), t)
                Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        R.id.settings -> {
            startActivityForResult(Intent(this, SettingsActivity::class.java), REQUEST_CODE)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if( resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE ) {
            updateWeatherData()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("item", checkedItem)
    }

    fun itemClicked(item: WeatherData) {
        checkedItem = item
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        val orientation: Int = resources.configuration.orientation

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            transaction.replace(R.id.list_fragment, WeatherListFragment.newInstance(listWeatherData))
                .replace(R.id.details_fragment, WeatherDetailsFragment.newInstance(item))
                .commit()
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            transaction.replace(R.id.fragment, WeatherDetailsFragment.newInstance(item))
                .addToBackStack(null)
                .commit()
        }
    }

    private fun render(item: WeatherData?) {
        val transaction = supportFragmentManager.beginTransaction()
        val orientation: Int = resources.configuration.orientation
        if( checkedItem != null) {
            when (orientation) {
                Configuration.ORIENTATION_PORTRAIT -> transaction.replace(R.id.fragment, WeatherDetailsFragment.newInstance(item))
                    .addToBackStack(null)
                    .commit()
                Configuration.ORIENTATION_LANDSCAPE -> transaction.replace(R.id.list_fragment, WeatherListFragment.newInstance(listWeatherData))
                    .replace(R.id.details_fragment, WeatherDetailsFragment.newInstance(item)).commit()
            }
        } else {
            when (orientation) {
                Configuration.ORIENTATION_PORTRAIT -> transaction.replace(R.id.fragment, WeatherListFragment.newInstance(listWeatherData))
                    .addToBackStack(null)
                    .commit()
                Configuration.ORIENTATION_LANDSCAPE -> transaction.replace(R.id.list_fragment, WeatherListFragment.newInstance(listWeatherData))
                    .replace(R.id.details_fragment, LonelyCloudFragment()).commitAllowingStateLoss()
                    }
            }
    }

    fun getWeatherResponse(service: WeatherService): Call<WeatherResponse> {
        if (!sharedPreferences.value.getBoolean("coordinates", true))
            return service.getCurrentWeatherDataByCityName(sharedPreferences.value.getString("city", "")!!,
                sharedPreferences.value.getString("units", "").toString(),
                sharedPreferences.value.getString("lang", "").toString())
        else {
            val location = getCurrentLocation()
            return service.getCurrentWeatherDataByCoordinates(location?.latitude.toString(),
                location?.longitude.toString(),
                sharedPreferences.value.getString("units", "").toString(),
                sharedPreferences.value.getString("lang", "").toString())
        }
    }

    fun getCurrentLocation(): Location? {
        var location: Location? = null

        if (checkLocationPermission()) {
            val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        }
        return location
    }

    fun checkLocationPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    1
                )
            }
            else {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    1)
            }
            return false
        }
        else return true
    }

    override fun onBackPressed() {
        checkedItem = null
        super.onBackPressed()
    }

}
