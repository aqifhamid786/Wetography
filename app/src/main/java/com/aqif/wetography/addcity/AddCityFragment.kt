package com.aqif.wetography.addcity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import android.widget.SearchView.OnQueryTextListener
import com.aqif.wetography.CitiesFragmentAdapter
import com.aqif.wetography.model.City
import com.aqif.wetography.R
import com.aqif.wetography.databinding.AddCityFragmentBinding
import com.aqif.wetography.hideKeyboard
import com.aqif.wetography.restapi.WeatherAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class AddCityFragment : Fragment() {

    private lateinit var binding: AddCityFragmentBinding
    private var selectedCity: City? = null
    private val adapter = CitiesFragmentAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = AddCityFragmentBinding.inflate(inflater, container, false)
        binding.citiesRecyclerview.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.onItemClicked(::onCitySelected)

        val viewmodel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(activity!!.application)).get(
            AddCityFragmentVM::class.java)

        viewmodel.filteredCities.observe(this, Observer { cities -> adapter.setCities(cities) })
        binding.viewmodel = viewmodel

        binding.citySearchView.setOnQueryTextListener(object : OnQueryTextListener, SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                processQuery(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                processQuery(newText)
                return true
            }

            fun processQuery(query: String?) {
                viewmodel.filterCities(query)
                if(selectedCity!=null && !selectedCity?.name.equals(query)) {
                    binding.addButton.visibility = View.GONE
                    selectedCity = null
                }
            }
        })

        binding.addButton.setOnClickListener {
            viewmodel.addCity(selectedCity)
            context?.hideKeyboard(view)
            findNavController().popBackStack()
        }

        binding.cancelButton.setOnClickListener {
            context?.hideKeyboard(view)
            findNavController().popBackStack()
        }
    }

    fun onCitySelected(city: City) {
        selectedCity = city
        binding.citySearchView.setQuery(city.name, false)
        binding.addButton.visibility = View.VISIBLE
    }
}


