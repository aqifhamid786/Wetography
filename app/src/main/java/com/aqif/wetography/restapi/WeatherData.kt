package com.aqif.wetography.restapi

data class Wind(val deg: Double, val speed: Double)
data class Main(val humidity: Double, val pressure: Double, val temp: String, val temp_max: String, val temp_min: String)
data class Clouds(val all: Double)
data class CityWeatherData(val clouds: Clouds, val main: Main, val name: String, val wind: Wind)
