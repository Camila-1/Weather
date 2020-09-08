package com.example.weather

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_weather.*

class WeatherFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        ViewCompat.setOnApplyWindowInsetsListener(content) { _, insets ->
//            Log.d("topInset", insets.systemWindowInsetTop.toString())
//            Log.d("bottomInset", insets.systemWindowInsetBottom.toString())
//            //and so on for left and right insets
//            insets.consumeSystemWindowInsets()
//        }
//
//        ViewCompat.setOnApplyWindowInsetsListener(content) { _, insets ->
//            val layoutParams = content.layoutParams as ViewGroup.MarginLayoutParams
//            layoutParams.setMargins(0, insets.systemWindowInsetTop, 0, 0)
//            insets.consumeSystemWindowInsets()
//        }
    }

}