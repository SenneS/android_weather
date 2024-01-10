package be.senne.meerweer.domain.model

import be.senne.meerweer.R

enum class WeatherCode {
    CLEAR_SKY,
    CLOUDY,
    RAIN,
    SNOW,
    THUNDER,
    UNKNOWN;

    fun to_icon() : Int {
        return when(this) {
            CLEAR_SKY -> R.drawable.day_sunny_icon
            CLOUDY -> R.drawable.cloudy_icon
            RAIN -> R.drawable.cloud_rain_icon
            SNOW -> R.drawable.cloud_snow_icon
            THUNDER -> R.drawable.cloud_lightning_icon
            UNKNOWN -> R.drawable.hurricane_icon
        }
    }

    fun to_str() : Int {
        return when(this) {
            CLEAR_SKY -> R.string.weather_clear_sky
            CLOUDY -> R.string.weather_cloudy
            RAIN -> R.string.weather_rain
            SNOW -> R.string.weather_snow
            THUNDER -> R.string.weather_thunder
            UNKNOWN -> R.string.weather_unknown
        }
    }

    companion object {
        fun from_ww(code : Int) : WeatherCode {
            return when(code) {
                in 0..1 -> CLEAR_SKY
                in 2..3 -> CLOUDY
                in 20..21, 25, in 50..69, in 80..82, in 91..92 -> RAIN
                in 22..24, in 26..28, in 70..79, in 83..90, in 93..94 -> SNOW
                13, 17, 29, in 95..99 -> THUNDER
                else -> UNKNOWN
            }
        }
    }

}