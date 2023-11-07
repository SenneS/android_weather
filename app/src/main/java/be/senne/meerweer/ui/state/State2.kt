package be.senne.meerweer.ui.state

import be.senne.meerweer.ui.model.WeatherDataUI

data class State2(
    val locationsLoading : Boolean = true,
    val locationLoading : Boolean = true,
    val locationCount : Int = 0,
    val locationData : Map<Int, WeatherDataUI> = emptyMap()
)