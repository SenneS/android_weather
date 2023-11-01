package be.senne.meerweer.ui.screens.home

sealed class HomeEvent {
    data class Button1Clicked(val id : String) : HomeEvent()
    data class Button2Clicked(val id : String) : HomeEvent()
}