package com.example.weatherapp.framework.network

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.framework.model.WeatherDataResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getWeather(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("units") units: String = "metric",
        @Query("appid") appid: String = BuildConfig.PUBLIC_KEY
    ): WeatherDataResponse

}
