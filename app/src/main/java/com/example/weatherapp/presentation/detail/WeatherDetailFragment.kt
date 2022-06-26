package com.example.weatherapp.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.weatherapp.databinding.FragmentWeatherDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherDetailFragment : Fragment() {

    private var _binding: FragmentWeatherDetailBinding? = null
    private val binding: FragmentWeatherDetailBinding get() = _binding!!

    private val viewModel: WeatherDetailViewModel by viewModels()

    private val args by navArgs<WeatherDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentWeatherDetailBinding.inflate(inflater, container, false).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val detailViewArg = args.detailViewArgs

        initObservers(detailViewArg)
    }

    private fun initObservers(detailViewArgs: DetailViewArg) {

        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                WeatherDetailViewModel.UiState.Loading -> "Loading Weather..."
                is WeatherDetailViewModel.UiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.textTempValue.text = "${uiState.detailWeather.main.temp}ºC"
                    binding.textTempMaxValue.text = "${uiState.detailWeather.main.tempMax}ºC"
                    binding.textTempMinValue.text = "${uiState.detailWeather.main.tempMin}ºC"
                }
                is WeatherDetailViewModel.UiState.Error -> "Error fetch Weather"
            }
        }

        viewModel.getWeather(detailViewArgs.lat.toString(), detailViewArgs.lon.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}