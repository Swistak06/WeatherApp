package com.swistak.weatherapp

import org.json.JSONObject

class ThreeHourWeatherForecast() {
    var city = "Gliwice"
    var temperature = "0.0"
    var sky = "Pochmurno"
    var description = "opis"
    var windSpeed = "2.5"
    var pressure = "1000"
    var humidity = "2"
    var date = "01.01.1999"
    var time = "12:00:00"


    fun createOnePeriodForecastObject(jsonString : JSONObject, periodNumber : Int){
        val forecastArrayItem = JSONObject(jsonString.getJSONArray("list").get(periodNumber).toString())
        val dateTime = forecastArrayItem.getString("dt_txt").split(" ")
        temperature = forecastArrayItem.getJSONObject("main").getString("temp")
        humidity = forecastArrayItem.getJSONObject("main").getString("humidity")
        pressure = forecastArrayItem.getJSONObject("main").getString("pressure")
        sky = forecastArrayItem.getJSONArray("weather").getJSONObject(0).getString("main")
        description = forecastArrayItem.getJSONArray("weather").getJSONObject(0).getString("description").capitalize()
        windSpeed =  forecastArrayItem.getJSONObject("wind").getString("speed")
        date = dateTime[0].substring(8,10) +"-"+dateTime[0].substring(5,7)+"-"+dateTime[0].substring(0,4)
        time = dateTime[1].substring(0,5)
    }
}