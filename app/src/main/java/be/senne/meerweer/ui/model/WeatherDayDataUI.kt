package be.senne.meerweer.ui.model

import be.senne.meerweer.domain.model.WeatherWindDirection

data class WeatherDayDataUI(
    val day: String,
    val minTemperature : String,
    val maxTemperature: String,
    val precipitation: String,
    val maxWind: String,
    val maxGusts: String,
    val windDirection: String,
    val icon: String
)