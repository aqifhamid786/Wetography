package com.aqif.wetography.restapi

data class Temperature(val temp: Double)
data class CityTempData(val main: Temperature)
data class TemperatureData(val cnt: Int, val list: List<CityTempData>)