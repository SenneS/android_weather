package be.senne.meerweer

import be.senne.meerweer.domain.model.WeatherWindDirection
import org.junit.Assert.assertEquals
import org.junit.Test

class WindDirectionTest {
    @Test
    fun testFromDegrees_NORTH() {
        assertEquals(WeatherWindDirection.NORTH, WeatherWindDirection.fromDegrees(0.0))
        assertEquals(WeatherWindDirection.NORTH, WeatherWindDirection.fromDegrees(22.4))
        assertEquals(WeatherWindDirection.NORTH, WeatherWindDirection.fromDegrees(22.5))
        assertEquals(WeatherWindDirection.NORTH, WeatherWindDirection.fromDegrees(337.5))
        assertEquals(WeatherWindDirection.NORTH, WeatherWindDirection.fromDegrees(360.0))
    }

    @Test
    fun testFromDegrees_EAST() {
        assertEquals(WeatherWindDirection.EAST, WeatherWindDirection.fromDegrees(67.5))
        assertEquals(WeatherWindDirection.EAST, WeatherWindDirection.fromDegrees(112.5))
    }

    @Test
    fun testFromDegrees_SOUTH() {
        assertEquals(WeatherWindDirection.SOUTH, WeatherWindDirection.fromDegrees(157.5))
        assertEquals(WeatherWindDirection.SOUTH, WeatherWindDirection.fromDegrees(202.5))
    }

    @Test
    fun testFromDegrees_WEST() {
        assertEquals(WeatherWindDirection.WEST, WeatherWindDirection.fromDegrees(247.5))
        assertEquals(WeatherWindDirection.WEST, WeatherWindDirection.fromDegrees(292.5))
    }

    @Test
    fun testFromDegrees_NORTHEAST() {
        assertEquals(WeatherWindDirection.NORTHEAST, WeatherWindDirection.fromDegrees(22.5001))
        assertEquals(WeatherWindDirection.NORTHEAST, WeatherWindDirection.fromDegrees(67.4999))
    }

    @Test
    fun testFromDegrees_SOUTHEAST() {
        assertEquals(WeatherWindDirection.SOUTHEAST, WeatherWindDirection.fromDegrees(112.5001))
        assertEquals(WeatherWindDirection.SOUTHEAST, WeatherWindDirection.fromDegrees(157.4999))
    }

    @Test
    fun testFromDegrees_SOUTHWEST() {
        assertEquals(WeatherWindDirection.SOUTHWEST, WeatherWindDirection.fromDegrees(202.5001))
        assertEquals(WeatherWindDirection.SOUTHWEST, WeatherWindDirection.fromDegrees(247.4999))
    }

    @Test
    fun testFromDegrees_NORTHWEST() {
        assertEquals(WeatherWindDirection.NORTHWEST, WeatherWindDirection.fromDegrees(292.5001))
        assertEquals(WeatherWindDirection.NORTHWEST, WeatherWindDirection.fromDegrees(337.4999))
    }
}