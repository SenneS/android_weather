package be.senne.meerweer.ui.screens.home

sealed class HomeEvent {
    object RefreshWeatherLocations : HomeEvent()
}