package be.senne.meerweer.domain.model

data class WeatherData(
    val name : String,
    val latitude : Double,
    val longitude : Double,
    val elevation : Long,

    val weatherCode: WeatherCode,
    val temperature: Double,
    val windspeed: Double,
    val windgusts: Double,
    val windDirection: WeatherWindDirection,

    val hourlyData: List<WeatherHourData>,
    val dailyData: List<WeatherDayData>
)
