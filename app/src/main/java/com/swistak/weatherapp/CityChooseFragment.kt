package com.swistak.weatherapp

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_city_choose.*
import kotlinx.android.synthetic.main.fragment_city_choose.view.*


class CityChooseFragment : Fragment() {

    private var listener: CityChooseListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_city_choose, container, false)

        view.setOnClickListener{
            listener?.szukajButtonClick()
        }

        view.button2.setOnClickListener{
            listener?.szukajButtonClick()
        }
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
        fun szukajButtonClick()
    }
}
