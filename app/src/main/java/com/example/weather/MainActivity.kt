package com.example.weather

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.preference.PreferenceManager
import com.example.weather.db.WeatherModel
import com.example.weather.fragments.LonelyCloudFragment
import com.example.weather.fragments.WeatherDetailsFragment
import com.example.weather.fragments.WeatherListFragment
import com.example.weather.response.WeatherData
import com.example.weather.response.WeatherResponse
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    companion object {
        private val REQUEST_CODE = 1
    }
    private var checkedItem: WeatherData? = null
    private var listWeatherData: WeatherResponse? = null
    val locationService: LocationService = LocationService(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false)

        locationService.startLocationUpdates()

        if (listWeatherData == null)
            showWeatherData()

        checkedItem = savedInstanceState?.getParcelable("item")
    }

    fun showWeatherData() {
        if (hasNetworkConnection(this))
            updateWeatherData()
        else {
            GlobalScope.launch {
                listWeatherData = WeatherModel(this@MainActivity).getWeatherResponse()
                render(checkedItem)
            }
            Toast.makeText(this, R.string.connectivity_status_message, Toast.LENGTH_LONG).show()
        }
    }

    fun hasNetworkConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }

    fun updateWeatherData() {
        GlobalScope.launch {
            val response = WeatherRequest(this@MainActivity, locationService).getWeatherResponseData()
            val db = WeatherModel(this@MainActivity)
            db.insert(response)
            listWeatherData = response
            render(checkedItem)
        }
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

        when(resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE ->
                transaction.replace(R.id.list_fragment, WeatherListFragment.newInstance(listWeatherData))
                    .replace(R.id.details_fragment, WeatherDetailsFragment.newInstance(item))
                    .commit()
            Configuration.ORIENTATION_PORTRAIT ->
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
                    .commit()
                Configuration.ORIENTATION_LANDSCAPE -> transaction.replace(R.id.list_fragment, WeatherListFragment.newInstance(listWeatherData))
                    .replace(R.id.details_fragment, WeatherDetailsFragment.newInstance(item)).commit()
            }
        } else {
            when (orientation) {
                Configuration.ORIENTATION_PORTRAIT -> transaction.replace(R.id.fragment, WeatherListFragment.newInstance(listWeatherData))
                    .commit()
                Configuration.ORIENTATION_LANDSCAPE -> transaction.replace(R.id.list_fragment, WeatherListFragment.newInstance(listWeatherData))
                    .replace(R.id.details_fragment,
                        LonelyCloudFragment()
                    ).commitAllowingStateLoss()
            }
        }
        progress_bar.visibility = View.INVISIBLE
    }

    override fun onBackPressed() {
        checkedItem = null
        super.onBackPressed()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        locationService.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
