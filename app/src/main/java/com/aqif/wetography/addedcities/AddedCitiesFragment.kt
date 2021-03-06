package com.aqif.wetography.addedcities

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.aqif.wetography.CitiesFragmentAdapter
import com.aqif.wetography.R
import com.aqif.wetography.databinding.AddedCitiesFragmentBinding
import com.aqif.wetography.model.City

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class AddedCitiesFragment : Fragment() {

    private lateinit var binding: AddedCitiesFragmentBinding
    private val adapter: CitiesFragmentAdapter = CitiesFragmentAdapter()
    private var viewmodel: AddedCitiesFragmentVM? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View?
    {
        binding = AddedCitiesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        binding.citiesRecyclerview.adapter = adapter
        binding.lifecycleOwner = this
        adapter.onItemClicked(::onCitySelected)
        adapter.onItemLongClicked(::onCityDeleteSelected)

        viewmodel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(activity!!.application)).get(
            AddedCitiesFragmentVM::class.java)
        viewmodel!!.addedCities.observe(this, Observer {
            cities -> adapter.setCities(cities)
            binding.swipeRefreshLayout.isRefreshing = false
        })

        binding.viewmodel = viewmodel
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_AddedCitiesFragment_to_AddCityFragment)
        }

        binding.swipeRefreshLayout.setOnRefreshListener { viewmodel?.update() }
    }

    override fun onResume() {
        super.onResume()
        viewmodel?.update()
    }

    fun onCitySelected(city: City) {
        val bundle = bundleOf("userId" to city.id.toString())
        findNavController().navigate(R.id.action_AddedCitiesFragment_to_WeatherInfoFragment, bundle)
    }

    fun onCityDeleteSelected(city: City) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(resources.getString(R.string.delete_city_dialog_title).format(city.name))
        builder.setMessage(resources.getString(R.string.delete_city_dialog_message).format(city.name))
        builder.setPositiveButton(R.string.confirm) { _, _ -> viewmodel?.deleteCity(city) }
        builder.setNegativeButton(R.string.cancel) { _, _ -> }
        builder.create().show()
    }
}
