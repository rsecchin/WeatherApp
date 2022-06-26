package com.example.weatherapp.presentation.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.weatherapp.framework.model.WeatherDataResponse
import com.example.weatherapp.framework.useCase.GetWeatherUseCase
import com.example.weatherapp.framework.useCase.base.ResultStatus
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.isA
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class WeatherDetailViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutine = MainCoroutineRule()

    @Mock
    lateinit var getWeatherUseCase: GetWeatherUseCase

    @Mock
    private lateinit var uiStateObserver: Observer<WeatherDetailViewModel.UiState>

    private val weather = WeatherDataResponse("stations", WeatherDataResponse.Main(2.2,2.3,2.0),
        listOf(WeatherDataResponse.Weather("","",2,"")))

    private lateinit var weatherDetailViewModel: WeatherDetailViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        weatherDetailViewModel = WeatherDetailViewModel(getWeatherUseCase)
        weatherDetailViewModel.uiState.observeForever(uiStateObserver)
    }

    @Test
    fun `should notify uiState with Success from UiState when get weather returns success`() =
        runTest {
            // Arrange
            whenever(getWeatherUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Success(
                            weather
                        )
                    )
                )

            // Act
            weatherDetailViewModel.getWeather("1.50", "-0.12,")

            // Assert
            verify(uiStateObserver).onChanged(isA<WeatherDetailViewModel.UiState.Success>())

            val uiStateSuccess = weatherDetailViewModel.uiState.value as WeatherDetailViewModel.UiState.Success
            val weatherTest = uiStateSuccess.detailWeather

            assertEquals(
                "stations",
                weatherTest.base
            )
        }

    @Test
    fun `should notify uiState with Error from UiState when get weather returns an exception`() =
        runTest {
            // Arrange
            whenever(getWeatherUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Error(Throwable())
                    )
                )

            // Act
            weatherDetailViewModel.getWeather("2.2", "2.5")

            // Assert
            verify(uiStateObserver).onChanged(isA<WeatherDetailViewModel.UiState.Error>())
        }

}

@ExperimentalCoroutinesApi
class MainCoroutineRule(private val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()):
    TestWatcher(),
    TestCoroutineScope by TestCoroutineScope(dispatcher) {
    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        cleanupTestCoroutines()
        Dispatchers.resetMain()
    }
}