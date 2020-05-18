package com.aqif.wetography.weatherinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aqif.wetography.repositories.WeatherInfoRepository
import com.aqif.wetography.restapi.CityWeatherData

class WeatherInfoFragmentVM : ViewModel() {

    private val weatherRepository: WeatherInfoRepository = WeatherInfoRepository()
    val weatherData: LiveData<CityWeatherData> = weatherRepository.weatherData

    fun fetchWeatherData(userId: String?) {
        weatherRepository.getWeather(userId)
    }

}