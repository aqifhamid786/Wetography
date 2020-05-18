package com.aqif.wetography

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aqif.wetography.databinding.CityRecyclerItemBinding
import com.aqif.wetography.model.City
import kotlin.reflect.KFunction1

class CitiesFragmentAdapter : RecyclerView.Adapter<CitiesFragmentAdapter.MainFragmentVH>() {

    init {
        setHasStableIds(true)
    }

    inner class MainFragmentVH(itemBinding: CityRecyclerItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        private val binding: CityRecyclerItemBinding = itemBinding
        fun bind(city: City, onCitySelected: KFunction1<City, Unit>?, onCityLongSelected: KFunction1<City, Unit>?) {
            binding.city = city
            binding.root.setOnClickListener {
                if(onCitySelected!=null)
                    onCitySelected(city)
            }
            binding.root.setOnLongClickListener{
                if(onCityLongSelected!=null)
                    onCityLongSelected(city)
                true
            }
        }
    }

    private var cities: List<City> = ArrayList()

    private var onCitySelected: KFunction1<City, Unit>? = null
    private var onCityLongSelected: KFunction1<City, Unit>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainFragmentVH {
        val binding: CityRecyclerItemBinding = CityRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainFragmentVH(binding)
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    override fun getItemId(position: Int): Long {
        return cities[position].id.toLong()
    }

    override fun onBindViewHolder(holder: MainFragmentVH, position: Int) {
        holder.bind(cities.get(position), onCitySelected, onCityLongSelected)
    }

    fun setCities(cities: List<City>) {
        this.cities = cities
        notifyDataSetChanged()
    }

    fun onItemClicked(onCitySelected: KFunction1<City, Unit>?) {
        this.onCitySelected = onCitySelected
    }
    fun onItemLongClicked(onCityLongSelected: KFunction1<City, Unit>?) {
        this.onCityLongSelected = onCityLongSelected
    }

}
