package com.aqif.wetography

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData

class CitiesRepository(sharedPreferences: SharedPreferences) {

    private var sharedPreferences: SharedPreferences = sharedPreferences

    init {
         updateCities(sharedPreferences.getString("cities", ""))
    }

    val cities: MutableLiveData<ArrayList<City>> = MutableLiveData()
    val cityIds: MutableLiveData<ArrayList<Int>> = MutableLiveData()
    val cityNames: MutableLiveData<ArrayList<String>> = MutableLiveData()
    val cityTemperatures: MutableLiveData<ArrayList<String>> = MutableLiveData()

    private fun updateCities(citiesString: String?) {

        if (citiesString?.isEmpty()!!)
            return

        val citiesList: ArrayList<City> = ArrayList()
        val cityIdList: ArrayList<Int> = ArrayList()
        val cityNameList: ArrayList<String> = ArrayList()

        for (city in citiesString?.split("|")!!) {
            val city: City = City.fromString(city)
            citiesList.add(city)
            cityIdList.add(city.id)
            cityNameList.add(city.name)
        }

        cities.value = citiesList
        cityIds.value = cityIdList
        cityNames.value = cityNameList
    }
}