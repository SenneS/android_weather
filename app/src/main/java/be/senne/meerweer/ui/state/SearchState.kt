package be.senne.meerweer.ui.state

import be.senne.meerweer.domain.model.CountryCode
import be.senne.meerweer.domain.model.WeatherLocation
import be.senne.meerweer.ui.component.fakeWeatherData
import be.senne.meerweer.ui.model.WeatherDataUI

data class SearchState(
    val searchTerm : String = "",
    val isSearching : Boolean = false,
    val searchResults: List<WeatherLocation> = ArrayList(),

    val isDialog: Boolean = false,
    val searchResultData: Result<WeatherDataUI> = Result.success(fakeWeatherData()),

    val openLocation : WeatherLocation = WeatherLocation(0, "", "", CountryCode.AD, 0.0, 0.0),
    val locationSaved: Boolean = false
)