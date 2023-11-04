package be.senne.meerweer.domain.model

enum class WeatherWindDirection {
    NORTH,
    SOUTH,
    EAST,
    WEST,
    NORTHEAST,
    SOUTHEAST,
    SOUTHWEST,
    NORTHWEST;

    fun display() : String {
        val displ : String = when(this) {
            NORTH -> "North"
            SOUTH -> "South"
            EAST -> "East"
            WEST -> "West"
            NORTHEAST -> "Northeast"
            SOUTHEAST -> "Southeast"
            SOUTHWEST -> "Southwest"
            NORTHWEST -> "Northwest"
        }
        return displ
    }
}