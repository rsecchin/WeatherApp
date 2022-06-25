package com.example.weatherapp.framework

import com.example.weatherapp.framework.model.WeatherDataResponse
import com.example.weatherapp.framework.repository.WeatherRemoteDataSource
import com.example.weatherapp.framework.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val remoteDataSource: WeatherRemoteDataSource
): WeatherRepository {

    override suspend fun getWeather(lat: String, lon: String): WeatherDataResponse {
        return remoteDataSource.getWeather(lat, lon)
    }
}