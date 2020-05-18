package com.aqif.wetography.weatherinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aqif.wetography.databinding.WeatherInfoFragmentBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class WeatherInfoFragment : Fragment() {

    private lateinit var binding: WeatherInfoFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = WeatherInfoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewmodel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(activity!!.application)).get(WeatherInfoFragmentVM::class.java)
        viewmodel.fetchWeatherData(arguments?.getString("userId", ""))
        binding.lifecycleOwner = this
        binding.viewmodel = viewmodel
    }
}


