package com.aqif.wetography.restapi

data class Wind(val deg: Double, val speed: Double)
data class Main(val humidity: Double, val pressure: Double, val temp: Double, val temp_max: Double, val temp_min: Double)
data class Clouds(val all: Double)
data class CityWeatherData(val clouds: Clouds, val main: Main, val name: String, val wind: Wind)
data class WeatherData(val cnt: Int, val list: List<CityWeatherData>)