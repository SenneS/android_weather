package be.senne.meerweer.domain.model

import be.senne.meerweer.R

enum class WeekDay(val text_long: Int, val text_short: Int) {
    MONDAY(
        R.string.week_long_monday,
        R.string.week_short_monday
    ),
    TUESDAY(
        R.string.week_long_tuesday,
        R.string.week_short_tuesday
    ),
    WEDNESDAY(
        R.string.week_long_wednesday,
        R.string.week_short_wednesday
    ),
    THURSDAY(
        R.string.week_long_thursday,
        R.string.week_short_thursday
    ),
    FRIDAY(
        R.string.week_long_friday,
        R.string.week_short_friday
    ),
    SATURDAY(
        R.string.week_long_saturday,
        R.string.week_short_saturday
    ),
    SUNDAY(
        R.string.week_long_sunday,
        R.string.week_short_sunday
    )
}