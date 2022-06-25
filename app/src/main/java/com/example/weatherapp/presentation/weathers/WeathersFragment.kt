package com.example.weatherapp.presentation.weathers

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.databinding.FragmentWeathersBinding
import com.example.weatherapp.presentation.detail.DetailViewArg
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class WeathersFragment : Fragment() {

    private var _binding: FragmentWeathersBinding? = null
    private val binding: FragmentWeathersBinding get() = _binding!!

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentWeathersBinding.inflate(inflater, container, false).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        initButtons()
    }

    private fun initButtons() {

        binding.londonWeather.setOnClickListener {
            initNavigation("London Weather", 51.51, -0.13)
        }
        binding.berlinWeather.setOnClickListener {
            initNavigation("Berlin Weather", 52.52, 13.39)
        }
        binding.copenhagenWeather.setOnClickListener {
            initNavigation("Copenhagen Weather", 55.649, 12.57)
        }
        binding.dublinWeather.setOnClickListener {
            initNavigation("Dublin Weather", 53.35, -6.26)
        }
        binding.lisbonWeather.setOnClickListener {
            initNavigation("Lisbon Weather", 38.99, -9.14)
        }
        binding.madridWeather.setOnClickListener {
            initNavigation("Madrid Weather", 40.48, -3.68)
        }
        binding.parisWeather.setOnClickListener {
            initNavigation("Paris Weather", 48.86, 2.32)
        }
        binding.romeWeather.setOnClickListener {
            initNavigation("Rome Weather", 41.89, 12.48)
        }
        binding.pragueWeather.setOnClickListener {
            initNavigation("Prague Weather", 50.09, 14.42)
        }
        binding.viennaWeather.setOnClickListener {
            initNavigation("Vienna Weather", 48.22, 16.37)
        }
        binding.youLocationWeather.setOnClickListener {
            getCurrentLocation()
        }
    }

    private fun initNavigation(screenTitle: String, lat: Double, lon: Double) {
        val directions = WeathersFragmentDirections
            .actionWeathersFragmentToWeatherDetailFragment(
                screenTitle,
                DetailViewArg(lat = lat, lon = lon)
            )

        findNavController().navigate(directions)
    }

    private fun getCurrentLocation() {

        if (checkPermission()) {

            if (isLocationEnable()) {

                //receive lat and long here

                if (ActivityCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(
                        requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED) {
                    requestPermission()
                    return
                }
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->

                    val location: Location? = task.result
                    if (location== null) {
                        Toast.makeText(context, "Null received", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Get Success", Toast.LENGTH_SHORT).show()
                        initNavigation("Your Location", location.latitude, location.longitude)
                    }

                }

            } else {
                Toast.makeText(context, "Turn On Location", Toast.LENGTH_SHORT).show()
                val intent =  Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }

        } else {
            // requests permission
            requestPermission()
        }
    }

    private fun isLocationEnable(): Boolean {
        val locationManager: LocationManager = requireContext()
            .getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), PERMISSION_REQUEST_ACCESS_LOCATION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_ACCESS_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(context, "Granted", Toast.LENGTH_SHORT).show()
                getCurrentLocation()
            } else {
                Toast.makeText(context, "Denied", Toast.LENGTH_SHORT).show()
            }

            //Tell to user the need of grant permission
        }
    }

    private fun checkPermission(): Boolean {
        return (ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val PERMISSION_REQUEST_ACCESS_LOCATION = 100
    }

}