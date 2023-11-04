package be.senne.meerweer.ui.screens.home

import java.util.UUID

sealed class HomeEvent {
    data class RefreshWeatherData(val uuid : UUID) : HomeEvent()
    object RefreshWeatherLocations : HomeEvent()
}