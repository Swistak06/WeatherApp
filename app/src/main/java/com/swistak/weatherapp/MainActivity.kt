package com.swistak.weatherapp

import android.Manifest

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager



class MainActivity : AppCompatActivity(), CityChooseFragment.CityChooseListener {

    override fun szukajButtonClick(forecastPeriodsArray: Array<ThreeHourWeatherForecast>) {
        val transaction = manager.beginTransaction()
        weatherForecastFragment.setForecastArray(forecastPeriodsArray)
        isVis = true
        invalidateOptionsMenu()
        transaction.replace(R.id.main_frame, weatherForecastFragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }


    val REQUEST_INTERNET = 1
    var isVis = false

    //Fragment managing variables
    private val manager: FragmentManager = supportFragmentManager
    private val cityChooseFragment = CityChooseFragment()
    private val weatherForecastFragment = WeatherForecastFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transaction = manager.beginTransaction()
        transaction.add(R.id.main_frame, cityChooseFragment)
        transaction.add(R.id.main_frame, weatherForecastFragment)
        transaction.replace(R.id.main_frame, cityChooseFragment)
        transaction.commit()

        //Checking for internet permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.INTERNET),
                REQUEST_INTERNET
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.top_bar, menu)
        val item = menu!!.findItem(R.id.arrow)
        item.isVisible = isVis
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.arrow ->{
                manager.popBackStack()
                isVis = false
                invalidateOptionsMenu()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        when {
            manager.backStackEntryCount == 0 -> super.onBackPressed()
            else -> {
                isVis = false
                invalidateOptionsMenu()
                manager.popBackStack()
            }
        }
    }
}

