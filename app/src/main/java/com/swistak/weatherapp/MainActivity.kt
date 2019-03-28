package com.swistak.weatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Bundle
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity() {


    lateinit var forecastJSON : JSONObject
    val REQUEST_INTERNET = 1
    val forecastPeriodsArray = Array(4) { ThreeHourWeatherForecast() }
    val forecastIcons = arrayOf(R.drawable.sun96, R.drawable.moon96, R.drawable.rain96,R.drawable.clouds96, R.drawable.storm96,
        R.drawable.cloud_day96, R.drawable.cloud_night96,R.drawable.storm96, R.drawable.snow96)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.INTERNET),
                REQUEST_INTERNET
            )
        }
            val url = "http://api.openweathermap.org/data/2.5/forecast?q=Gliwice&appid=fc0d19e416fe1e275a8b17aaab35071d&units=metric&lang=pl"
        AsyncTaskHandleJson().execute(url)
    }

    inner class AsyncTaskHandleJson : AsyncTask<String, String, String>() {
        override fun doInBackground(vararg url: String?): String {
            var text: String = ""
            val connection = URL(url[0]).openConnection() as HttpURLConnection
            try {
                connection.connect()
                text = connection.inputStream.use { it.reader().use{reader -> reader.readText()}}
            }catch (e :Exception){
                print(e.message)
                e.printStackTrace()
            }
            finally{
                connection.disconnect()
            }
            return  text;
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            forecastJSON = JSONObject(result)
            for(i in 0..3){
                forecastPeriodsArray[i].createOnePeriodForecastObject(forecastJSON,i)
            }
            updateView()
    }
    }

    @SuppressLint("SetTextI18n")
    fun updateView(){
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

        updateIcons(forecastPeriodsArray[0].sky, mainWeatherIcon,forecastPeriodsArray[0].time.substring(0,2).toInt(),forecastIcons)
        updateIcons(forecastPeriodsArray[1].sky, SecondWeatherIconIV,forecastPeriodsArray[1].time.substring(0,2).toInt(),forecastIcons)
        updateIcons(forecastPeriodsArray[2].sky, ThirdWeatherIconIV,forecastPeriodsArray[2].time.substring(0,2).toInt(),forecastIcons)
        updateIcons(forecastPeriodsArray[3].sky, FourthWeatherIconIV,forecastPeriodsArray[3].time.substring(0,2).toInt(),forecastIcons)
    }

    fun updateIcons(main : String, iconReplaced : ImageView, hour : Int, forecastIcons : Array<Int>){
        when(main.toLowerCase()){
            "rain" -> iconReplaced.setImageResource(forecastIcons[2])
            "shower rain" -> iconReplaced.setImageResource(forecastIcons[2])
            "scattered clouds" -> iconReplaced.setImageResource(forecastIcons[3])
            "broken clouds" -> iconReplaced.setImageResource(forecastIcons[3])
            "thunderstorm" -> iconReplaced.setImageResource(forecastIcons[6])
            "snow" -> iconReplaced.setImageResource(forecastIcons[7])
            "clear sky" ->
                if(hour >= 21 || hour < 6 )
                    iconReplaced.setImageResource(forecastIcons[1])
                else iconReplaced.setImageResource(forecastIcons[0])
            "few clouds" ->
                if(hour >= 21 || hour < 6 )
                iconReplaced.setImageResource(forecastIcons[5])
            else iconReplaced.setImageResource(forecastIcons[4])
        }


    }
}
