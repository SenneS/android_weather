package be.senne.meerweer.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.KeyboardDoubleArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.senne.meerweer.R
import be.senne.meerweer.domain.model.WeatherCode
import be.senne.meerweer.domain.model.WeatherData
import be.senne.meerweer.domain.model.WeatherDayData
import be.senne.meerweer.domain.model.WeatherHourData
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
import kotlin.math.roundToInt
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
            Icon(imageVector = Icons.Default.Cloud, contentDescription = "Cloud", modifier = Modifier.size(80.dp))
            Column {
                Text(text = uiData.location)
                Text(text = "${uiData.now.temperature}°C")
            }
        }
    }
}

@Composable
fun WeatherMainDataSection(uiData: WeatherCurrentDataUI) {

    Row {
        Column(modifier= Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(painter = painterResource(R.drawable.wind_icon), contentDescription = "", modifier=Modifier.size(48.dp))
            Text(text = "Wind: ${uiData.wind}", modifier = Modifier.padding(top= 10.dp), style=MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
        }
        Column(modifier= Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(painter = painterResource(R.drawable.compass), contentDescription = "", modifier=Modifier.size(48.dp))
            Text(text = "Wind Direction: ${uiData.windDirection}", modifier = Modifier.padding(top= 10.dp), style=MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
        }
        Column(modifier= Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(painter = painterResource(R.drawable.drop_icon), contentDescription = "", modifier=Modifier.size(48.dp))
            Text(text = "Precipitation: ${uiData.precipitation}", modifier = Modifier.padding(top= 10.dp), style=MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
        }
        Column(modifier= Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(painter = painterResource(R.drawable.day_sunny_icon), contentDescription = "", modifier=Modifier.size(48.dp))
            Text(text = "Sunrise: ${uiData.sunrise}", modifier = Modifier.padding(top= 10.dp), style=MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
        }
        Column(modifier= Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(painter = painterResource(R.drawable.moon_line_icon), contentDescription = "", modifier=Modifier.size(48.dp))
            Text(text = "Sunset: ${uiData.sunset}", modifier = Modifier.padding(top= 10.dp), style=MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherHourSection(uiData: List<WeatherHourDataUI>) {
    LazyRow(contentPadding = PaddingValues(16.dp), modifier = Modifier.nestedScroll(CustomNestedScrollConnection())) {
        items(uiData) {
            Card(modifier = Modifier
                .padding(horizontal = 15.dp)
                .widthIn(max = 50.dp)) {
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = it.hour)
                    Text(text = it.temperature)
                    Text(text = it.precipitation)
                    Icon(imageVector = Icons.Default.Cloud, contentDescription = "Cloudy")
                }
            }
        }
    }
}

@Composable
fun WeatherDaySection(uiData: List<WeatherDayDataUI>) {
    Column(modifier= Modifier.fillMaxSize()) {
        val days = listOf<String>("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

        uiData.forEach {
            var expanded by remember { mutableStateOf(false) }
            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp).clickable {
                    expanded = !expanded
                }) {

                Column(modifier = Modifier.animateContentSize()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore, contentDescription = "")
                        Text(text = it.day, modifier=Modifier.weight(1f))
                        Text(text = "${it.minTemperature} / ${it.maxTemperature}", textAlign = TextAlign.End)
                    }
                    if(expanded) {
                        Text(text = "Expanded Content. ".repeat(5))
                    }
                }

/*
                Row(modifier = Modifier.animateContentSize(), verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore, contentDescription = "")
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = it)

                        if(expanded) {
                            Text(text = "Expanded Content. ".repeat(5))
                        }
                    }
                }
 */
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun fakeWeatherData() : WeatherDataUI {
    val location = "Brussels"

    val now = ZonedDateTime.now()
    val currentWeatherData = WeatherCurrentDataUI(
        "39°C",
        "130 km/h",
        "260 km/h",
        "South",
        "800mm",
        "12:00",
        "18:00"
    )

    val hourlyWeatherData = List(24) {
        val hour = "${now.plusHours(it.toLong()).formatToHHmm()}h"
        val precipitation = "${Random.nextInt(0, 835)}mm"
        val temperature = "${Random.nextInt(-15, 46)}°C"
        val icon = ""
        WeatherHourDataUI(hour, precipitation, temperature, icon)
    }

    val days = listOf<String>("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    val dailyWeatherData = List(7) {
        val _min = Random.nextInt(-15, 46)
        val minTemperature = "${_min}°C"
        val maxTemperature = "${Random.nextInt(_min, 46)}°C"
        val precipitation = "${Random.nextInt(0, 835)}mm"
        val wind = "km/h"
        val gusts = "km/h"
        val direction = "South"
        val icon = ""
        WeatherDayDataUI(days[it], minTemperature, maxTemperature, precipitation, wind, gusts, direction, icon)
    }


    val weatherData = WeatherDataUI(
        UUID.randomUUID(),
        timestamp = Instant.now(),
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