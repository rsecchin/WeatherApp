package com.example.weatherapp.framework.di

import com.example.weatherapp.framework.useCase.GetWeatherUseCase
import com.example.weatherapp.framework.useCase.GetWeatherUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindGetWeatherUseCase(weatherUseCaseImpl: GetWeatherUseCaseImpl) : GetWeatherUseCase
}