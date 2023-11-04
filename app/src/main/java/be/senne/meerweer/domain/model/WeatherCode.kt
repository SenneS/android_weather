package be.senne.meerweer.domain.model

enum class WeatherCode {
    CLEAR_SKY,
    CLOUDY,
    RAIN,
    SNOW,
    THUNDER;

    fun display() : String {
        val displ : String = when(this) {
            CLEAR_SKY -> "Clear Sky"
            CLOUDY -> "Cloudy"
            RAIN -> "Rainy"
            SNOW -> "Snowy"
            THUNDER -> "Thunder"
        }
        return displ
    }

}