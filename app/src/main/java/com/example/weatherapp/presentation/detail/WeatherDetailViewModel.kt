package com.example.weatherapp.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.framework.model.WeatherDataResponse
import com.example.weatherapp.framework.useCase.GetWeatherUseCase
import com.example.weatherapp.framework.useCase.base.ResultStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherDetailViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    fun getWeather(lat: String, lon: String) = viewModelScope.launch {
        getWeatherUseCase.invoke(GetWeatherUseCase.GetWeatherParams(lat, lon)).watchStatus()
    }

    private fun Flow<ResultStatus<WeatherDataResponse>>.watchStatus() = viewModelScope.launch {
        collect { status ->
            _uiState.value = when(status) {
                ResultStatus.Loading -> UiState.Loading
                is ResultStatus.Success -> {

                    UiState.Success(status.data)
                }
                is ResultStatus.Error -> UiState.Error
            }
        }
    }

    sealed class UiState {
        object Loading : UiState()
        data class Success(val detailWeather: WeatherDataResponse) : UiState()
        object Error: UiState()
    }

}