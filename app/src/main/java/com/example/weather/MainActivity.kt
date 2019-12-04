package com.example.weather

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import androidx.preference.PreferenceManager
import com.example.weather.fragments.WeatherDetailsFragment
import com.example.weather.fragments.WeatherListFragment
import com.example.weather.response.WeatherData
import kotlinx.android.synthetic.main.settings_activity.*

class MainActivity : AppCompatActivity() {
    private val RESULT_CODE = 1
    private var checkedItem: WeatherData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false)

        checkedItem = savedInstanceState?.getParcelable("item")
        render(checkedItem)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        R.id.settings -> {
            startActivityForResult(Intent(this, SettingsActivity::class.java), RESULT_CODE)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if( data == null ) return
        if( requestCode == Activity.RESULT_OK && resultCode == RESULT_CODE ) {
            render(checkedItem)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putParcelable("item", checkedItem)
    }

    fun itemClicked(item: WeatherData) {
        checkedItem = item
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        val orientation: Int = resources.configuration.orientation

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            transaction.replace(R.id.list_fragment, WeatherListFragment())
                .replace(R.id.details_fragment, WeatherDetailsFragment.newInstance(item))
                .commit()
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            transaction.replace(R.id.fragment, WeatherDetailsFragment.newInstance(item))
                .commit()
        }
    }

    private fun render(item: WeatherData?) {
        val transaction = supportFragmentManager.beginTransaction()
        val orientation: Int = resources.configuration.orientation
        if( checkedItem != null) {
            when (orientation) {
                Configuration.ORIENTATION_PORTRAIT -> transaction.replace(R.id.fragment, WeatherDetailsFragment.newInstance(item)).commit()
                Configuration.ORIENTATION_LANDSCAPE -> transaction.replace(R.id.list_fragment, WeatherListFragment())
                    .replace(R.id.details_fragment, WeatherDetailsFragment.newInstance(item)).commit()
            }
        } else {
            when (orientation) {
                Configuration.ORIENTATION_PORTRAIT -> transaction.replace(R.id.fragment, WeatherListFragment()).commit()
                Configuration.ORIENTATION_LANDSCAPE -> transaction.replace(R.id.list_fragment, WeatherListFragment())
                    .replace(R.id.details_fragment, WeatherDetailsFragment.newInstance(item)).commit()
            }
        }

    }
}
