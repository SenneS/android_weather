package be.senne.meerweer.ui.state

data class SettingsState(
    val isLoading : Boolean = true,
    val currentSpeedUnit : Int = 0,
    val currentTemperatureUnit : Int = 0,
    val currentPrecipitationUnit : Int = 0,
)