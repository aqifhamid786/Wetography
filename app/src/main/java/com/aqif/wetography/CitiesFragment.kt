package com.aqif.wetography

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aqif.wetography.databinding.CitiesFragmentBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class CitiesFragment : Fragment() {

    private lateinit var binding: CitiesFragmentBinding
    private val adapter: CititesFragmentAdapter = CititesFragmentAdapter()


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View?
    {
        binding = CitiesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        binding.citiesRecyclerview.adapter = adapter

        val viewmodel: CitiesFragmentVM =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(activity!!.application))
                .get(CitiesFragmentVM::class.java)
        binding.viewmodel = viewmodel

        viewmodel.cities.observe(this, Observer { cities -> adapter.setCities(cities) })
    }

    override fun onResume() {
        super.onResume()
    }
}
