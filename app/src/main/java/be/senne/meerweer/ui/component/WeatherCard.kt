package be.senne.meerweer.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.senne.meerweer.R
import be.senne.meerweer.domain.model.WeatherWindDirection
import be.senne.meerweer.ui.model.WeatherCurrentDataUI
import be.senne.meerweer.ui.model.WeatherDataUI
import be.senne.meerweer.ui.model.WeatherDayDataUI
import be.senne.meerweer.ui.model.WeatherHourDataUI
import be.senne.meerweer.utils.CustomNestedScrollConnection
import be.senne.meerweer.utils.formatToHHmm
import java.time.Instant
import java.time.ZonedDateTime
import java.util.UUID
import kotlin.random.Random

@Composable
fun WeatherCard(uiData: WeatherDataUI) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {WeatherHeaderSection(uiData) }
            item {Divider(modifier = Modifier.padding(horizontal = 20.dp, vertical = 15.dp)) }
            item {WeatherMainDataSection(uiData.now) }
            item {Divider(modifier = Modifier.padding(horizontal = 20.dp, vertical = 15.dp)) }
            item {WeatherHourSection(uiData.hourly) }
            item {Divider(modifier = Modifier.padding(horizontal = 20.dp, vertical = 15.dp)) }
            item {WeatherDaySection(uiData.daily) }
            item {Divider(modifier = Modifier.padding(horizontal = 20.dp, vertical = 15.dp)) }
            item {Spacer(modifier = Modifier.height(40.dp))}
    }
}

@Composable
fun WeatherHeaderSection(uiData: WeatherDataUI) {
    Box(modifier = Modifier.scale(1f)) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Spacer(modifier = Modifier.weight(1f))
            Icon(painter = painterResource(id = uiData.now.weatherIcon), contentDescription = stringResource(
                id = uiData.now.weatherText
            ), modifier = Modifier.size(120.dp), tint = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.weight(1f))
            Column {
                Text(
                    text = uiData.location,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = uiData.now.temperature,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleSmall
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun WeatherMainDataSection(uiData: WeatherCurrentDataUI) {

    Row {
        Column(modifier= Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(painter = painterResource(R.drawable.wind_icon), contentDescription = stringResource(
                R.string.current_wind_speed
            ), modifier=Modifier.size(48.dp), tint = MaterialTheme.colorScheme.onSurface)
            Text(
                text = "${stringResource(R.string.wind)}: ${uiData.wind}",
                modifier = Modifier.padding(top= 10.dp),
                style=MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Column(modifier= Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(painter = painterResource(R.drawable.compass), contentDescription = stringResource(
                R.string.current_wind_direction
            ), modifier=Modifier.size(48.dp), tint = MaterialTheme.colorScheme.onSurface)
            Text(text = "${stringResource(R.string.wind_direction)}: ${stringResource(id = uiData.windDirectionLongText)}", modifier = Modifier.padding(top= 10.dp), style=MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurface)
        }
        Column(modifier= Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(painter = painterResource(R.drawable.drop_icon), contentDescription = stringResource(
                R.string.current_precipitation
            ), modifier=Modifier.size(48.dp), tint = MaterialTheme.colorScheme.onSurface)
            Text(text = "${stringResource(R.string.precipitation)}: ${uiData.precipitation}", modifier = Modifier.padding(top= 10.dp), style=MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurface)
        }
        Column(modifier= Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(painter = painterResource(R.drawable.day_sunny_icon), contentDescription = stringResource(
                R.string.today_s_sunrise
            ), modifier=Modifier.size(48.dp), tint = MaterialTheme.colorScheme.onSurface)
            Text(text = "${stringResource(R.string.sunrise)}: ${uiData.sunrise}", modifier = Modifier.padding(top= 10.dp), style=MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurface)
        }
        Column(modifier= Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(painter = painterResource(R.drawable.moon_line_icon), contentDescription = stringResource(
                R.string.today_s_sunset
            ), modifier=Modifier.size(48.dp), tint = MaterialTheme.colorScheme.onSurface)
            Text(text = "${stringResource(R.string.sunset)}: ${uiData.sunset}", modifier = Modifier.padding(top= 10.dp), style=MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Composable
fun WeatherHourSection(uiData: List<WeatherHourDataUI>) {
    LazyRow(contentPadding = PaddingValues(16.dp), modifier = Modifier.nestedScroll(CustomNestedScrollConnection())) {
        items(uiData) {
            Card(modifier = Modifier
                .padding(horizontal = 15.dp)
                .size(width = 150.dp, height = 100.dp)) {
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = it.hour)
                    Text(text = it.temperature)
                    Text(text = it.precipitation)
                    Icon(painter = painterResource(id = it.weatherIcon), contentDescription = stringResource(id = it.weatherText), modifier= Modifier.size(35.dp))
                }
            }
        }
    }
}

@Composable
fun WeatherDaySection(uiData: List<WeatherDayDataUI>) {
    Column(modifier= Modifier.fillMaxSize()) {

        uiData.forEach {
            var expanded by remember { mutableStateOf(false) }
            Card(modifier = Modifier
                .fillMaxWidth()
                .heightIn(50.dp)
                .padding(vertical = 5.dp, horizontal = 10.dp)
                .clickable {
                    expanded = !expanded
                }) {

                Column(modifier = Modifier.animateContentSize()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore, contentDescription = stringResource(
                            R.string.weather_day_expand_button
                        )
                        )
                        Text(text = it.day, modifier=Modifier.weight(1f))
                        Text(text = "${it.minTemperature} / ${it.maxTemperature}", textAlign = TextAlign.End, modifier = Modifier.padding(horizontal=5.dp))
                    }
                    if(expanded) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(painter= painterResource(id = it.weatherIcon), contentDescription = stringResource(
                                    id = it.weatherText
                                ), modifier=Modifier.size(20.dp))
                                Spacer(modifier=Modifier.weight(1f))
                                Text(text = stringResource(id = it.weatherText), modifier = Modifier.padding(end= 10.dp), style=MaterialTheme.typography.bodySmall)
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(painter= painterResource(id = R.drawable.compass), contentDescription = stringResource(
                                    id = it.windDirectionLongText
                                ), modifier=Modifier.size(20.dp))
                                Spacer(modifier=Modifier.weight(1f))
                                Text(text = stringResource(id = it.windDirectionLongText), modifier = Modifier.padding(end= 10.dp), style=MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
        }
    }
}

fun fakeWeatherData() : WeatherDataUI {
    val location = "Brussels"

    val now = ZonedDateTime.now()
    val currentWeatherData = WeatherCurrentDataUI(
        "39°C",
        "130 km/h",
        "260 km/h",
        R.string.wind_direction_long_north,
        R.string.wind_direction_short_north,
        "800mm",
        "12:00",
        "18:00",
        R.drawable.cloud_wind_icon,
        R.string.weather_cloudy
    )

    val hourlyWeatherData = List(24) {
        val hour = "${now.plusHours(it.toLong()).formatToHHmm()}h"
        val precipitation = "${Random.nextInt(0, 100)}%"
        val temperature = "${Random.nextInt(-15, 46)}°C"
        val icon = ""
        WeatherHourDataUI(hour, precipitation, temperature, R.string.weather_clear_sky, R.drawable.day_sunny_icon)
    }

    val days = listOf<String>("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    val dailyWeatherData = List(7) {
        val _min = Random.nextInt(-15, 46)
        val minTemperature = "${_min}°C"
        val maxTemperature = "${Random.nextInt(_min, 46)}°C"
        val precipitation = "${Random.nextInt(0, 835)}mm"
        val wind = "km/h"
        val gusts = "km/h"
        val direction = WeatherWindDirection.SOUTH
        val icon = ""
        WeatherDayDataUI(days[it], minTemperature, maxTemperature, precipitation, wind, gusts, direction.long_text, direction.short_text, R.string.weather_clear_sky, R.drawable.day_sunny_icon)
    }


    val weatherData = WeatherDataUI(
        timestamp = 0,
        location,
        currentWeatherData,
        hourlyWeatherData,
        dailyWeatherData
    )

    return weatherData
}

@Composable
@Preview
fun WeatherCardPreview() {

    val weatherData = fakeWeatherData()

    WeatherCard(uiData = weatherData)
}