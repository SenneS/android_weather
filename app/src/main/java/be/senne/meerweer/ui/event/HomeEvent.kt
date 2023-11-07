package be.senne.meerweer.ui.event

sealed class HomeEvent {
    object RefreshAllWeatherData : HomeEvent()
}