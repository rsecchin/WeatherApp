package com.example.weatherapp.framework.repository

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.framework.model.WeatherDataResponse
import com.example.weatherapp.framework.network.WeatherApi
import javax.inject.Inject

class WeatherRetrofitRemoteDataSource @Inject constructor (
    private val weatherApi: WeatherApi
) : WeatherRemoteDataSource {

    override suspend fun getWeather(lat: String, lon: String): WeatherDataResponse {
        return weatherApi.getWeather(lat, lon, "metric", BuildConfig.PUBLIC_KEY)
    }
}