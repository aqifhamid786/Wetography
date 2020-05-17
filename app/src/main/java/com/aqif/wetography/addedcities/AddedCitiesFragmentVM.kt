package com.aqif.wetography.addedcities

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.aqif.wetography.model.City
import com.aqif.wetography.repositories.AddedCitiesRepository

class AddedCitiesFragmentVM(application: Application) : AndroidViewModel(application) {

    private val addedCitiesRepository: AddedCitiesRepository =
        AddedCitiesRepository(
            application.getSharedPreferences(
                "wetography",
                0
            )
        )

    var addedCities: LiveData<ArrayList<City>>  = addedCitiesRepository.addedCities

    fun update() {
        addedCitiesRepository.update()
    }


}