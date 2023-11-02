package be.senne.meerweer.data.remote.dto

data class ForecastResponse(
    val daily: ForecastDaily?,
    val daily_units: ForecastDailyUnits?,
    val elevation: Double?,
    val generationtime_ms: Double?,
    val hourly: ForecastHourly?,
    val hourly_units: ForecastHourlyUnits?,
    val latitude: Double?,
    val longitude: Double?,
    val timezone: String?,
    val timezone_abbreviation: String?,
    val utc_offset_seconds: Int?,

    val reason: String?,
    val error: Boolean?
)
