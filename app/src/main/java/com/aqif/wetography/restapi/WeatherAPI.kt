package com.aqif.wetography.restapi

import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherAPI {

    @GET("group")
    fun getWeather(@Query("id") ids: String?,
                   @Query("units") units: String="metric",
                   @Query("appid") appid: String = "a5312d81433c7df9f30b89211c8c0753"):
            Observable<WeatherData>

    @GET("group")
    fun getTemperature(@Query("id") ids: String?,
                   @Query("units") units: String="metric",
                   @Query("appid") appid: String = "a5312d81433c7df9f30b89211c8c0753"):
            Observable<TemperatureData>

    companion object {

        fun create(): WeatherAPI {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit = Retrofit.Builder().client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://samples.openweathermap.org/data/2.5/")
                .build()

            return retrofit.create(WeatherAPI::class.java)
        }
    }

}