package com.example.weatherapp.framework.repository

import com.example.weatherapp.framework.model.WeatherDataResponse

interface WeatherRemoteDataSource {

    suspend fun getWeather(lat: String, lon: String) : WeatherDataResponse

}