package com.example.weatherapp.presentation.detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailViewArg(
    val lat: Double,
    val lon: Double
): Parcelable
