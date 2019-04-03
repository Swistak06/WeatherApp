package com.swistak.weatherapp

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import kotlinx.android.synthetic.main.fragment_weather_forecast.*
import org.json.JSONObject
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL


class WeatherForecastFragment : Fragment() {

    lateinit var forecastJSON : JSONObject
    var url = "http://api.openweathermap.org/data/2.5/forecast?q=Gliwice&appid=fc0d19e416fe1e275a8b17aaab35071d&units=metric&lang=pl"



    val forecastIcons = arrayOf(R.drawable.sun96, R.drawable.moon96,R.drawable.rain96, R.drawable.clouds96, R.drawable.cloudday96, R.drawable.cloudnight96,  R.drawable.storm96,
        R.drawable.snow96,R.drawable.haze96, R.drawable.fog96)

    val forecastPeriodsArray = Array(4) { ThreeHourWeatherForecast() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        AsyncTaskHandleJson().execute(url)
        return inflater.inflate(R.layout.fragment_weather_forecast, container, false)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    @SuppressLint("SetTextI18n")
    public fun updateView(){
        CityTV.text = forecastPeriodsArray[0].city
        dateTV.text = forecastPeriodsArray[0].date
        currentHourTV.text = forecastPeriodsArray[0].time
        currentTemperatureTV.text = forecastPeriodsArray[0].temperature+0x00B0.toChar()+"C"
        currentWindspeedTV.text = forecastPeriodsArray[0].windSpeed +"m/s"
        currentHumidityTv.text = forecastPeriodsArray[0].humidity + "%"
        currentPressureTV.text = forecastPeriodsArray[0].pressure +"hPa"
        descriptionTV.text = forecastPeriodsArray[0].description

        SecondHourPeriodTV.text = forecastPeriodsArray[1].time
        ThirdHourPeriodTV.text = forecastPeriodsArray[2].time
        FourthHourPeriodTV.text = forecastPeriodsArray[3].time

        SecondTemperatureTV.text = forecastPeriodsArray[1].temperature+0x00B0.toChar()+"C"
        ThirdTemperatureTV.text = forecastPeriodsArray[2].temperature+0x00B0.toChar()+"C"
        FourthTemperatureTV.text = forecastPeriodsArray[3].temperature+0x00B0.toChar()+"C"

        updateIcons(forecastPeriodsArray[0].icon, mainWeatherIcon,forecastPeriodsArray[0].time.substring(0,2).toInt(),forecastIcons)
        updateIcons(forecastPeriodsArray[1].icon, SecondWeatherIconIV,forecastPeriodsArray[1].time.substring(0,2).toInt(),forecastIcons)
        updateIcons(forecastPeriodsArray[2].icon, ThirdWeatherIconIV,forecastPeriodsArray[2].time.substring(0,2).toInt(),forecastIcons)
        updateIcons(forecastPeriodsArray[3].icon, FourthWeatherIconIV,forecastPeriodsArray[3].time.substring(0,2).toInt(),forecastIcons)
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

    inner class AsyncTaskHandleJson : AsyncTask<String, String, String>() {
        override fun doInBackground(vararg url: String?): String {
            var text: String = ""
            val connection = URL(url[0]).openConnection() as HttpURLConnection
            try {
                connection.connect()
                text = connection.inputStream.use { it.reader().use { reader -> reader.readText() } }
            } catch (e: Exception) {
                print(e.message)
                e.printStackTrace()
            } finally {
                connection.disconnect()
            }
            return text;
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            forecastJSON = JSONObject(result)
            for (i in 0..3) {
                forecastPeriodsArray[i].createOnePeriodForecastObject(forecastJSON, i)
            }
            updateView()
        }
    }
}
