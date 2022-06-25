package com.example.weatherapp.framework.di

import com.example.weatherapp.framework.WeatherRepositoryImpl
import com.example.weatherapp.framework.repository.WeatherRemoteDataSource
import com.example.weatherapp.framework.repository.WeatherRepository
import com.example.weatherapp.framework.repository.WeatherRetrofitRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindWeatherRepository(repository: WeatherRepositoryImpl): WeatherRepository

    @Binds
    fun bindRemoteDataSource(
        dataSource: WeatherRetrofitRemoteDataSource
    ): WeatherRemoteDataSource
}