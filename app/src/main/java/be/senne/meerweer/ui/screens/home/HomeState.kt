package be.senne.meerweer.ui.screens.home

import be.senne.meerweer.domain.model.WeatherLocation

data class HomeState(
    val test : String = "",
    val areLocationsLoading : Boolean = true,
    val weatherLocations: List<WeatherLocation> = ArrayList()
)