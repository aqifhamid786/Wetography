package com.aqif.wetography.repositories

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.aqif.wetography.model.City
import com.aqif.wetography.restapi.CityTempData

class AddedCitiesRepository(sharedPreferences: SharedPreferences)  {

    private val CITIES_KEY: String = "cities"

    private val addedCities: ArrayList<City> = ArrayList()
    private val weatherInfoRepository: WeatherInfoRepository = WeatherInfoRepository()
    private val sharedPreferences: SharedPreferences = sharedPreferences
    private val temperatureDataObserver = Observer<List<CityTempData>> { data ->
        for (index in data.indices) { addedCities[index].temperature = "%s\u00B0C".format(data[index].main.temp) }
        addedCitiesLiveData.value = addedCities
    }

    val addedCitiesLiveData: MutableLiveData<ArrayList<City>> = MutableLiveData()

    init {
        weatherInfoRepository.temperatureData.observeForever(temperatureDataObserver)
    }

    private fun updateCities(citiesString: String?) {
        if (citiesString.toString().isEmpty()) {
            addedCitiesLiveData.value = addedCities
            return
        }
        addedCities.clear()
        for (city in citiesString?.split("|")!!) {
            addedCities.add(City.fromString(city))
        }
        addedCitiesLiveData.value = ArrayList(addedCities)
        updateTemperature()
    }

    private fun updateTemperature() {
        val buffer = StringBuffer()
        for (city in addedCities) {
            if(buffer.isNotEmpty())
                buffer.append(',')
            buffer.append(city.id)
        }
        weatherInfoRepository.getTemperature(buffer.toString())
    }

    fun update(){
        updateCities(sharedPreferences.getString(CITIES_KEY, ""))
    }

    fun addCity(city: City?) {
        val cities: String = sharedPreferences.getString(CITIES_KEY, "").toString()

        if(cities.isNotEmpty()) {
            val name = city?.name
            var found = false
            for (city in addedCities) {
                found = city.name == name
                if(found) break
            }
            if(found) return // return if city is already in the list.
        }

        val citiesBuffer: StringBuffer = StringBuffer().append(city?.id).append(",").append(city?.name)
        if(cities.isNotEmpty())
            citiesBuffer.append("|").append(cities)

        sharedPreferences.edit().putString(CITIES_KEY, citiesBuffer.toString()).apply()
        updateCities(cities)
    }

    fun deleteCity(city: City) {
        val citiesBuffer = StringBuffer()
        val citiesList: ArrayList<City> = ArrayList()

        for (addedCity in addedCities) {
            if(addedCity.name != city.name) {
                if(citiesBuffer.isNotEmpty()) citiesBuffer.append("|")
                citiesBuffer.append(addedCity?.id).append(",").append(addedCity?.name)
                citiesList.add(addedCity)
            }
        }
        sharedPreferences.edit().putString(CITIES_KEY, citiesBuffer.toString()).apply()
        addedCities.clear()
        addedCities.addAll(citiesList)
        addedCitiesLiveData.value = addedCities
    }

    fun clear() {
        weatherInfoRepository.temperatureData.removeObserver(temperatureDataObserver)
        addedCities.clear()
    }

}