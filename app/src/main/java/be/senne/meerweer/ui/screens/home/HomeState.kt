package be.senne.meerweer.ui.screens.home

import be.senne.meerweer.domain.model.WeatherData
import be.senne.meerweer.domain.model.WeatherLocation
import be.senne.meerweer.ui.model.WeatherDataUI

data class HomeState(
    val locationsAreLoading : Boolean = true,
    val dataIsLoading : Boolean = true,
    val weatherData: List<WeatherDataUI> = emptyList(),
)