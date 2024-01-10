package be.senne.meerweer.domain.model

import be.senne.meerweer.R

enum class WeatherWindDirection(val long_text : Int, val short_text : Int) {
    NORTH(
        R.string.wind_direction_long_north,
        R.string.wind_direction_short_north
    ),
    SOUTH(
        R.string.wind_direction_long_south,
        R.string.wind_direction_short_south
    ),
    EAST(
        R.string.wind_direction_long_east,
        R.string.wind_direction_short_east
    ),
    WEST(
        R.string.wind_direction_long_west,
        R.string.wind_direction_short_west
    ),
    NORTHEAST(
        R.string.wind_direction_long_northeast,
        R.string.wind_direction_short_northeast
    ),
    SOUTHEAST(
        R.string.wind_direction_long_southeast,
        R.string.wind_direction_short_southeast
    ),
    SOUTHWEST(
        R.string.wind_direction_long_southwest,
        R.string.wind_direction_short_southwest
    ),
    NORTHWEST(
        R.string.wind_direction_long_northwest,
        R.string.wind_direction_short_northwest
    );

    companion object {
        fun fromDegrees(degrees : Double) : WeatherWindDirection {
            if(degrees <= 22.5 || degrees >= 337.5) {
                return NORTH;
            }
            if(degrees < 67.5) {
                return NORTHEAST;
            }
            if(degrees <= 112.5) {
                return EAST;
            }
            if(degrees < 157.5) {
                return SOUTHEAST
            }

            if(degrees <= 202.5) {
                return SOUTH;
            }

            if(degrees < 247.5) {
                return SOUTHWEST;
            }

            if(degrees <= 292.5) {
                return WEST;
            }

            if(degrees < 337.5) {
                return NORTHWEST;
            }

            return NORTH;
        }
    }
}