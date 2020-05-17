package com.aqif.wetography

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aqif.wetography.databinding.CityRecyclerItemBinding

class CititesFragmentAdapter : RecyclerView.Adapter<CititesFragmentAdapter.MainFragmentVH>() {

    inner class MainFragmentVH(itemBinding: CityRecyclerItemBinding) : RecyclerView.ViewHolder(itemBinding.root){
        private val binding: CityRecyclerItemBinding = itemBinding
        fun bind(city: City){
            binding.city = city
        }
    }

    private var cities: ArrayList<City> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainFragmentVH {
        val binding: CityRecyclerItemBinding = CityRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainFragmentVH(binding)
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    override fun onBindViewHolder(holder: MainFragmentVH, position: Int) {
        holder.bind(cities.get(position))
    }

    fun setCities(cities: ArrayList<City>) {
        this.cities = cities
        notifyDataSetChanged()
    }

}