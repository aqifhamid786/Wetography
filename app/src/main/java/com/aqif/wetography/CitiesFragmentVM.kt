package com.aqif.wetography

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class CitiesFragmentVM(application: Application) : AndroidViewModel(application) {

    private val citiesRepository: CitiesRepository = CitiesRepository(application.getSharedPreferences("wetography", 0))

    var cities: LiveData<ArrayList<City>>  = citiesRepository.cities

}