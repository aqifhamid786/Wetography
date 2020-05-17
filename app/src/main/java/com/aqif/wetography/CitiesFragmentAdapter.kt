package com.aqif.wetography

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aqif.wetography.databinding.CityRecyclerItemBinding
import com.aqif.wetography.model.City
import kotlin.reflect.KFunction1

class CitiesFragmentAdapter : RecyclerView.Adapter<CitiesFragmentAdapter.MainFragmentVH>() {

    inner class MainFragmentVH(itemBinding: CityRecyclerItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        private val binding: CityRecyclerItemBinding = itemBinding
        fun bind(city: City, onCitySelected: KFunction1<City, Unit>?) {
            binding.city = city
            binding.root.setOnClickListener {
                if(onCitySelected!=null)
                    onCitySelected(city)
            }
        }
    }

    private var onCitySelected: KFunction1<City, Unit>? = null
    private var cities: List<City> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainFragmentVH {
        val binding: CityRecyclerItemBinding = CityRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainFragmentVH(binding)
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    override fun onBindViewHolder(holder: MainFragmentVH, position: Int) {
        holder.bind(cities.get(position), onCitySelected)
    }

    fun setCities(cities: List<City>) {
        this.cities = cities
        notifyDataSetChanged()
    }

    fun onItemClicked(onCitySelected: KFunction1<City, Unit>?) {
        this.onCitySelected = onCitySelected
    }

}
