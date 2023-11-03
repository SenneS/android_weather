package be.senne.meerweer.domain.model

data class WeatherData(
    val name : String ="",
    val latitude : Double = 0.0,
    val longitude : Double = 0.0,
    val elevation : Long = 0,

    val weatherCode: WeatherCode = WeatherCode.CLEAR_SKY,
    val temperature: Double = 0.0,
    val windspeed: Double = 0.0,
    val windgusts: Double = 0.0,
    val windDirection: WeatherWindDirection = WeatherWindDirection.SOUTH,

    val hourlyData: List<WeatherHourData> = emptyList(),
    val dailyData: List<WeatherDayData> = emptyList()
)
