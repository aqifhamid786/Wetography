package com.aqif.wetography.addcity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.aqif.wetography.model.City
import com.aqif.wetography.repositories.AddedCitiesRepository
import com.aqif.wetography.repositories.AllCitiesRepository

class AddCityFragmentVM(application: Application) : AndroidViewModel(application) {

    private val addedCitiesRepository: AddedCitiesRepository =
        AddedCitiesRepository(
            application.getSharedPreferences(
                "wetography",
                0
            )
        )
    private val allCitiesRepository: AllCitiesRepository =
        AllCitiesRepository(application)

    var filteredCities: LiveData<List<City>> = allCitiesRepository.filteredCities

    fun addCity(city: City?) {
        addedCitiesRepository.addCity(city)
    }

    fun filterCities(key: String?) {
        allCitiesRepository.filter(key)
    }

}