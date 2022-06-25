package com.example.weatherapp.framework.useCase

import com.example.weatherapp.framework.model.WeatherDataResponse
import com.example.weatherapp.framework.repository.WeatherRepository
import com.example.weatherapp.framework.useCase.base.ResultStatus
import com.example.weatherapp.framework.useCase.base.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetWeatherUseCase {

    operator fun invoke(params: GetWeatherParams): Flow<ResultStatus<WeatherDataResponse>>

    data class GetWeatherParams(val lat: String, val lon: String)
}

class GetWeatherUseCaseImpl @Inject constructor(
    private val repository: WeatherRepository
) : GetWeatherUseCase, UseCase<GetWeatherUseCase.GetWeatherParams, WeatherDataResponse>() {

    override suspend fun doWork(
        params: GetWeatherUseCase.GetWeatherParams
    ): ResultStatus<WeatherDataResponse> {
        val weather = repository.getWeather(params.lat, params.lon)
        return ResultStatus.Success(weather)
    }

}