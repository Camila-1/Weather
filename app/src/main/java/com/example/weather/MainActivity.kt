package com.example.weather

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast

import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import androidx.work.*
import com.example.weather.db.WeatherModel
import com.example.weather.fragments.WeatherDetailsFragment
import com.example.weather.fragments.WeatherListFragment
import com.example.weather.response.WeatherData
import com.example.weather.response.WeatherResponse
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        private val REQUEST_CODE = 1
    }
    private var checkedItem: WeatherData? = null
    private var listWeatherData: WeatherResponse? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false)

        if (listWeatherData == null)
            showWeatherData()

        checkedItem = savedInstanceState?.getParcelable("item")
    }

    fun showWeatherData() {
        if (hasNetworkConnection(this))
            updateWeatherData()
        else {
            listWeatherData = WeatherModel(this).getWeatherResponse()
            render(checkedItem)
            Toast.makeText(this, R.string.connectivity_status_message, Toast.LENGTH_LONG).show()
        }
    }

    fun hasNetworkConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }

    fun updateWeatherData() {
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = OneTimeWorkRequest.Builder(WeatherWorker::class.java)
            .setInputData(Data.Builder()
                .putString("unit", SharedPreferenceHolder.getUnit(this))
                .putString("lang", SharedPreferenceHolder.getLang(this))
                .putBoolean("isGeolocationEnabled", SharedPreferenceHolder.isGeolocationEnabled(this) && checkLocationPermission())
                .build())
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance().enqueue(request)
        WorkManager.getInstance().getWorkInfoByIdLiveData(request.id)
            .observe(this, Observer {
                if(it.state.isFinished) {
                    listWeatherData = WeatherModel(this).getWeatherResponse()
                    render(checkedItem)
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
                    .commit()
                Configuration.ORIENTATION_LANDSCAPE -> transaction.replace(R.id.list_fragment, WeatherListFragment.newInstance(listWeatherData))
                    .replace(R.id.details_fragment, WeatherDetailsFragment.newInstance(item)).commit()
            }
        } else {
            when (orientation) {
                Configuration.ORIENTATION_PORTRAIT -> transaction.replace(R.id.fragment, WeatherListFragment.newInstance(listWeatherData))
                    .commit()
                Configuration.ORIENTATION_LANDSCAPE -> transaction.replace(R.id.list_fragment, WeatherListFragment.newInstance(listWeatherData))
                    .replace(R.id.details_fragment, LonelyCloudFragment()).commitAllowingStateLoss()
            }
        }
        progress_bar.visibility = View.INVISIBLE
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
