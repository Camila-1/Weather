package com.example.weather

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.weather.db.WeatherModel
import com.example.weather.fragments.LonelyCloudFragment
import com.example.weather.fragments.WeatherDetailsFragment
import com.example.weather.fragments.WeatherListFragment
import com.example.weather.network.WeatherRequest
import com.example.weather.network.response.WeatherData
import com.example.weather.network.response.WeatherResponse
import com.example.weather.settings.SettingsActivity
import com.example.weather.settings.SharedPreferenceHolder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {
    companion object {
        private val REQUEST_CODE = 1
    }
    private var checkedItem: WeatherData? = null
    private var listWeatherData: WeatherResponse? = null
    private val locationService: LocationProvider = LocationProvider(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false)

        locationService.startLocationUpdates()
        showWeatherData()

        checkedItem = savedInstanceState?.getParcelable("item")
        supportActionBar?.title = null
    }

    fun showWeatherData() {
        when {
            listWeatherData != null -> {
                return
            }
            hasNetworkConnection(this) -> updateWeatherData()
            else -> {
                GlobalScope.launch(Dispatchers.Main){
                    listWeatherData = WeatherModel(this@MainActivity).getWeatherResponse()
                    render(checkedItem)
                }
                Toast.makeText(this, R.string.connectivity_status_message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setTitleName() {
        supportActionBar?.title = listWeatherData?.city?.name ?: SharedPreferenceHolder(this).getCity
    }

    fun hasNetworkConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }

    fun updateWeatherData() {
        GlobalScope.launch(Dispatchers.Main) {
            val response = WeatherRequest(this@MainActivity, locationService).getWeatherResponseData()
            WeatherModel(this@MainActivity).insert(response)
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

    private fun popBackStack() {
        if (supportFragmentManager.backStackEntryCount > 0)
            supportFragmentManager.popBackStack()
    }

    private fun render(item: WeatherData?) {
        progress_bar.visibility = View.INVISIBLE
        setTitleName()

        val transaction = supportFragmentManager.beginTransaction()
        val orientation: Int = resources.configuration.orientation
        if( checkedItem != null) {
            when (orientation) {
                Configuration.ORIENTATION_PORTRAIT -> transaction.replace(R.id.fragment, WeatherDetailsFragment.newInstance(item))
                    .addToBackStack(null)
                    .commit()
                Configuration.ORIENTATION_LANDSCAPE -> {
                    transaction.replace(R.id.list_fragment, WeatherListFragment.newInstance(listWeatherData))
                        .replace(R.id.details_fragment, WeatherDetailsFragment.newInstance(item)).commit()
                    popBackStack()
                }
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
