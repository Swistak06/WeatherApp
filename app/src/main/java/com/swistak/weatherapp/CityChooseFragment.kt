package com.swistak.weatherapp

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_city_choose.*
import kotlinx.android.synthetic.main.fragment_city_choose.view.*
import org.json.JSONObject
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener




class CityChooseFragment : Fragment() {

    private var listener: CityChooseListener? = null

    var urlBegin = "http://api.openweathermap.org/data/2.5/forecast?q="
    var urlEnd = "&appid=fc0d19e416fe1e275a8b17aaab35071d&units=metric&lang=pl"

    lateinit var forecastJSON : JSONObject

    val forecastPeriodsArray = Array(4) { ThreeHourWeatherForecast() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_city_choose, container, false)
        view.szukajBtn.setOnClickListener {
            AsyncTaskHandleJson().execute(urlBegin + cityInput.text + urlEnd)
        }

        view.cityInput.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                AsyncTaskHandleJson().execute(urlBegin + cityInput.text + urlEnd)
                return@OnKeyListener true
            }
            false
        })
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CityChooseListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface CityChooseListener {
        fun szukajButtonClick(forecastPeriodsArray : Array<ThreeHourWeatherForecast>)
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
            try{
                forecastJSON = JSONObject(result)
                for (i in 0..3) {
                    forecastPeriodsArray[i].createOnePeriodForecastObject(forecastJSON, i)
                }
                listener?.szukajButtonClick(forecastPeriodsArray)
            }
            catch (e : Exception){
                e.printStackTrace()
                //send warning
            }
            //updateView()
        }
    }
}
