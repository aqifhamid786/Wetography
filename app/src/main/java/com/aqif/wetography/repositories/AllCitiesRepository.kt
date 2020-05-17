package com.aqif.wetography.repositories

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.aqif.wetography.model.City
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException

class AllCitiesRepository(application: Application) {

    private var searchKey: String? = null
    private val allCities: MutableLiveData<List<City>> = MutableLiveData()

    val filteredCities: MutableLiveData<List<City>> = MutableLiveData()

    init {
        loadCitiesData(application)
    }

    private fun loadCitiesData(application: Application) {
        Observable.fromCallable {
            try {
                val jsonString = application.assets.open("city.list.json").bufferedReader().use { it.readText() }
                val citiesList: List<City> = Gson().fromJson(jsonString, object : TypeToken<List<City>>() {}.type)
                citiesList
            } catch (ioException: IOException) {
                ioException.printStackTrace()
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { citiesList ->
                allCities.value = citiesList as List<City>?
                if(searchKey != null) {
                    filter(searchKey)
                    searchKey = null
                }
            }
    }

    fun filter(key: String?) {
        val allCitiesList = allCities.value
        if(allCitiesList != null && allCitiesList.isNotEmpty()) {
            val filteredCitiesList: ArrayList<City> = ArrayList()
            if(key?.length!! >0)
                for (city in allCitiesList) {
                    if(city.name.contains(key.toString()))
                        filteredCitiesList.add(city)
                }
            filteredCities.value = filteredCitiesList
        } else {
            searchKey = key;
        }
    }

}