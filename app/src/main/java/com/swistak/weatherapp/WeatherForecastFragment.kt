package com.swistak.weatherapp

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import kotlinx.android.synthetic.main.fragment_weather_forecast.*
import kotlinx.android.synthetic.main.fragment_weather_forecast.view.*


class WeatherForecastFragment : Fragment() {

    lateinit var forecastPeriodsArray : Array<ThreeHourWeatherForecast>


    fun setForecastArray(forecastPeriodsArray : Array<ThreeHourWeatherForecast>){
        this.forecastPeriodsArray = forecastPeriodsArray
    }

    val forecastIcons = arrayOf(R.drawable.sun96, R.drawable.moon96,R.drawable.rain96, R.drawable.clouds96, R.drawable.cloudday96, R.drawable.cloudnight96,  R.drawable.storm96,
        R.drawable.snow96,R.drawable.haze96, R.drawable.fog96)



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weather_forecast, container, false)

        updateView(view)

        return view
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    @SuppressLint("SetTextI18n")
    public fun updateView(view: View) {
        view.CityTV.text = forecastPeriodsArray[0].city
        //view.dateTV.text = forecastPeriodsArray[0].date
        view.currentHourTV.text = forecastPeriodsArray[0].time
        view.currentTemperatureTV.text = forecastPeriodsArray[0].temperature+0x00B0.toChar()+"C"
        view.currentWindspeedTV.text = forecastPeriodsArray[0].windSpeed +"m/s"
        view.currentHumidityTv.text = forecastPeriodsArray[0].humidity + "%"
        view.currentPressureTV.text = forecastPeriodsArray[0].pressure +"hPa"
        view.descriptionTV.text = forecastPeriodsArray[0].description

        view.SecondHourPeriodTV.text = forecastPeriodsArray[1].time
        view.ThirdHourPeriodTV.text = forecastPeriodsArray[2].time
        view.FourthHourPeriodTV.text = forecastPeriodsArray[3].time

        view.SecondTemperatureTV.text = forecastPeriodsArray[1].temperature+0x00B0.toChar()+"C"
        view.ThirdTemperatureTV.text = forecastPeriodsArray[2].temperature+0x00B0.toChar()+"C"
        view.FourthTemperatureTV.text = forecastPeriodsArray[3].temperature+0x00B0.toChar()+"C"

        updateIcons(forecastPeriodsArray[0].icon, view.mainWeatherIcon,forecastPeriodsArray[0].time.substring(0,2).toInt(),forecastIcons)
        updateIcons(forecastPeriodsArray[1].icon, view.SecondWeatherIconIV,forecastPeriodsArray[1].time.substring(0,2).toInt(),forecastIcons)
        updateIcons(forecastPeriodsArray[2].icon, view.ThirdWeatherIconIV,forecastPeriodsArray[2].time.substring(0,2).toInt(),forecastIcons)
        updateIcons(forecastPeriodsArray[3].icon, view.FourthWeatherIconIV,forecastPeriodsArray[3].time.substring(0,2).toInt(),forecastIcons)
    }


    public fun updateIcons(icon : String, iconReplaced : ImageView, hour : Int, forecastIcons : Array<Int>) {
        when (icon.toLowerCase()) {
            //Clear sky
            "01d" -> iconReplaced.setImageResource(forecastIcons[0])
            "01n" -> iconReplaced.setImageResource(forecastIcons[1])

            //Partially clouded
            "02d" -> iconReplaced.setImageResource(forecastIcons[4])
            "02n" -> iconReplaced.setImageResource(forecastIcons[5])

            //Cloudy
            "03d" -> iconReplaced.setImageResource(forecastIcons[3])
            "04d" -> iconReplaced.setImageResource(forecastIcons[3])
            "03n" -> iconReplaced.setImageResource(forecastIcons[3])
            "04n" -> iconReplaced.setImageResource(forecastIcons[3])

            //Rain
            "09d" -> iconReplaced.setImageResource(forecastIcons[2])
            "09n" -> iconReplaced.setImageResource(forecastIcons[2])
            "10d" -> iconReplaced.setImageResource(forecastIcons[2])
            "10n" -> iconReplaced.setImageResource(forecastIcons[2])

            //Thunderstorm
            "11d" -> iconReplaced.setImageResource(forecastIcons[6])
            "11n" -> iconReplaced.setImageResource(forecastIcons[6])

            //Snow
            "13d" -> iconReplaced.setImageResource(forecastIcons[7])
            "13n" -> iconReplaced.setImageResource(forecastIcons[7])

            //Mist
            "50d" -> iconReplaced.setImageResource(forecastIcons[8])
            "50n" -> iconReplaced.setImageResource(forecastIcons[9])
        }
    }


}
