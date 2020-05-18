package com.aqif.wetography.model

data class City(val id:Int, val name:String, var temperature:String) {

    companion object {
        fun fromString(city: String): City {
            val cityParts = city.split(",")
            return City(cityParts[0].toInt(), cityParts[1], "")
        }
    }
}