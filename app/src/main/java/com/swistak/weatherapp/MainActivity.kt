package com.swistak.weatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager


class MainActivity : AppCompatActivity(), CityChooseFragment.CityChooseListener {


    override fun szukajButtonClick(forecastPeriodsArray: Array<ThreeHourWeatherForecast>) {
        val transaction = manager.beginTransaction()
        weatherForecastFragment.setForecastArray(forecastPeriodsArray)
        transaction.replace(R.id.main_frame, weatherForecastFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    val REQUEST_INTERNET = 1

    //Fragment managing variables
    private val manager: FragmentManager = supportFragmentManager
    private val cityChooseFragment = CityChooseFragment()
    private val weatherForecastFragment = WeatherForecastFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val transaction = manager.beginTransaction()
        transaction.add(R.id.main_frame,cityChooseFragment )
        transaction.add(R.id.main_frame, weatherForecastFragment)
        transaction.replace(R.id.main_frame, cityChooseFragment)
        transaction.commit()

        //Checking for internet permissions
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.INTERNET),
                REQUEST_INTERNET
            )
        }
    }
}
