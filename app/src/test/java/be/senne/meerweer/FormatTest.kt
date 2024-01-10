package be.senne.meerweer

import be.senne.meerweer.domain.model.MeasurementUnit
import be.senne.meerweer.utils.formatPrecipitation
import be.senne.meerweer.utils.formatTemperature
import be.senne.meerweer.utils.formatVelocity
import org.junit.Assert.assertEquals
import org.junit.Test

class FormatTest {
    // Define the units
    private val metric = MeasurementUnit.METRIC
    private val imperial = MeasurementUnit.IMPERIAL

    @Test
    fun testFormatTemperature() {
        assertEquals("25.5°C", formatTemperature(25.5, metric))
        assertEquals("77.9°F", formatTemperature(25.5, imperial))
    }

    @Test
    fun testFormatVelocity() {
        assertEquals("10.0 km/h", formatVelocity(10.0, metric))
        assertEquals("6.2 mph", formatVelocity(10.0, imperial))
    }

    @Test
    fun testFormatPrecipitation() {
        assertEquals("10.0mm", formatPrecipitation(10.0, metric))
        assertEquals("0.39in", formatPrecipitation(10.0, imperial))
    }
}