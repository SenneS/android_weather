package be.senne.meerweer.utils

import be.senne.meerweer.domain.model.MeasurementUnit

fun formatTemperature(temp: Double, unit : MeasurementUnit) : String {
    return when(unit) {
        MeasurementUnit.METRIC -> String.format("%.1f°C", temp)
        MeasurementUnit.IMPERIAL -> String.format("%.1f°F", (temp * (9.0 / 5.0) + 32.0))
    }
}

fun formatVelocity(velocity: Double, unit : MeasurementUnit) : String {
    return when(unit) {
        MeasurementUnit.METRIC -> String.format("%.1f km/h", velocity)
        MeasurementUnit.IMPERIAL -> String.format("%.1f mph", velocity*0.6213711922)
    }
}

fun formatPrecipitation(precipitation: Double, unit : MeasurementUnit) : String {
    return when(unit) {
        MeasurementUnit.METRIC -> String.format("%.1fmm", precipitation)
        MeasurementUnit.IMPERIAL -> String.format("%.2fin", (precipitation * 0.039))
    }
}