package com.aqif.wetography.repositories

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.aqif.wetography.model.City

class AddedCitiesRepository(sharedPreferences: SharedPreferences) {

    private var sharedPreferences: SharedPreferences = sharedPreferences
    private var CITIES_KEY: String = "cities"

    val addedCities: MutableLiveData<ArrayList<City>> = MutableLiveData()
    val addedCityIds: MutableLiveData<ArrayList<Int>> = MutableLiveData()
    val addedCityNames: MutableLiveData<ArrayList<String>> = MutableLiveData()
    val addedCityTemperatures: MutableLiveData<ArrayList<String>> = MutableLiveData()

    init {
        updateCities(sharedPreferences.getString(CITIES_KEY, ""))
    }

    private fun updateCities(citiesString: String?) {

        if (citiesString.toString().isEmpty())
            return

        val citiesList: ArrayList<City> = ArrayList()
        val cityIdList: ArrayList<Int> = ArrayList()
        val cityNameList: ArrayList<String> = ArrayList()

        for (city in citiesString?.split("|")!!) {
            val city: City =
                City.fromString(city)
            citiesList.add(city)
            cityIdList.add(city.id)
            cityNameList.add(city.name)
        }

        addedCities.value = citiesList
        addedCityIds.value = cityIdList
        addedCityNames.value = cityNameList
    }

    fun update(){
        updateCities(sharedPreferences.getString(CITIES_KEY, ""))
    }

    fun addCity(city: City?) {
        val cities: String = sharedPreferences.getString(CITIES_KEY, "").toString()

        if(cities.isNotEmpty()) {
            updateCities(cities)
            val cityNames: ArrayList<String> = addedCityNames.value!!
            val name = city?.name
            var found = false
            for (cityName in cityNames) {
                found = cityName == name
                if(found) break
            }
            if(found) return
        }

        val citiesBuffer: StringBuffer = StringBuffer().append(city?.id).append(",").append(city?.name)
        if(cities.isNotEmpty())
            citiesBuffer.append("|")

        sharedPreferences.edit().putString(CITIES_KEY, citiesBuffer.append(cities).toString()).apply()
        updateCities(cities)
    }
}