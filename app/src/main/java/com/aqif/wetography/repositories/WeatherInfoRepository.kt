package com.aqif.wetography.repositories

import androidx.lifecycle.MutableLiveData
import com.aqif.wetography.restapi.CityTempData
import com.aqif.wetography.restapi.CityWeatherData
import com.aqif.wetography.restapi.WeatherAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class WeatherInfoRepository {

    private val weatherApi by lazy {
        WeatherAPI.create()
    }

    private var weatherDisposable: Disposable? = null
    private var temperatureDisposable: Disposable? = null

    val weatherData: MutableLiveData<CityWeatherData> = MutableLiveData()
    val temperatureData: MutableLiveData<List<CityTempData>> = MutableLiveData()

    fun getWeather(userId: String?) {

        if(userId.toString().isEmpty())
            return

        weatherDisposable = weatherApi.getWeather(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( { data -> weatherData.value = data }, { error -> println(error.message) })
    }

    fun getTemperature(userIds: String) {

        if(userIds.isEmpty())
            return
        temperatureDisposable = weatherApi.getTemperature(userIds)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( { data -> temperatureData.value = data.list  }, { error -> println(error.message) })
    }
}