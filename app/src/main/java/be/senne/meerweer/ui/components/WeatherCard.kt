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
import be.senne.meerweer.utils.CustomNestedScrollConnection
import be.senne.meerweer.utils.formatToHHmm
import java.time.ZonedDateTime
import kotlin.math.roundToInt
import kotlin.random.Random

@Composable
fun WeatherCard(weatherData: WeatherData) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {WeatherHeaderSection(weatherData) }
            item {Divider(modifier = Modifier.padding(horizontal = 20.dp, vertical = 15.dp)) }
            item {WeatherMainDataSection(weatherData) }
            item {Divider(modifier = Modifier.padding(horizontal = 20.dp, vertical = 15.dp)) }
            item {WeatherHourSection(weatherData) }
            item {Divider(modifier = Modifier.padding(horizontal = 20.dp, vertical = 15.dp)) }
            item {WeatherDaySection(weatherData) }
            item {Divider(modifier = Modifier.padding(horizontal = 20.dp, vertical = 15.dp)) }
            item {Spacer(modifier = Modifier.height(40.dp))}
        }
    }
}

@Composable
fun WeatherHeaderSection(weatherData: WeatherData) {
    Box(modifier = Modifier.scale(1f)) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Icon(imageVector = Icons.Default.Cloud, contentDescription = "Cloud", modifier = Modifier.size(80.dp))
            Column {
                Text(text = weatherData.name)
                Text(text = "${weatherData.temperature}°C")
            }
        }
    }

}

@Composable
fun WeatherMainDataSection(weatherData: WeatherData) {
    //Find Today

    val hourNow = weatherData.hourlyData[0]
    val dayNow = weatherData.dailyData[0]

    Row {
        Column(modifier= Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(painter = painterResource(R.drawable.wind_icon), contentDescription = "", modifier=Modifier.size(48.dp))
            Text(text = "Wind: ${weatherData.windspeed}", modifier = Modifier.padding(top= 10.dp), style=MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
        }
        Column(modifier= Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(painter = painterResource(R.drawable.compass), contentDescription = "", modifier=Modifier.size(48.dp))
            Text(text = "Wind Direction: ${weatherData.windDirection.display()}", modifier = Modifier.padding(top= 10.dp), style=MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
        }
        Column(modifier= Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(painter = painterResource(R.drawable.drop_icon), contentDescription = "", modifier=Modifier.size(48.dp))
            Text(text = "Precipitation: ${weatherData.precipitation}mm", modifier = Modifier.padding(top= 10.dp), style=MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
        }
        Column(modifier= Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(painter = painterResource(R.drawable.day_sunny_icon), contentDescription = "", modifier=Modifier.size(48.dp))
            Text(text = "Sunrise: ${dayNow.sunrise.formatToHHmm()}", modifier = Modifier.padding(top= 10.dp), style=MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
        }
        Column(modifier= Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(painter = painterResource(R.drawable.moon_line_icon), contentDescription = "", modifier=Modifier.size(48.dp))
            Text(text = "Sunset: ${dayNow.sunset.formatToHHmm()}", modifier = Modifier.padding(top= 10.dp), style=MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherHourSection(weatherData: WeatherData) {
    val hourData = weatherData.hourlyData
    LazyRow(contentPadding = PaddingValues(16.dp), modifier = Modifier.nestedScroll(CustomNestedScrollConnection())) {
        items(hourData) {
            Card(modifier = Modifier
                .padding(horizontal = 15.dp)
                .widthIn(max = 50.dp)) {
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "${it.time.hour}h")
                    Text(text = "${it.temperature}°C")
                    Text(text = "${it.precipitation}%")
                    Icon(imageVector = Icons.Default.Cloud, contentDescription = "Cloudy")
                }
            }
        }
    }
}

@Composable
fun WeatherDaySection(weatherData: WeatherData) {
    Column(modifier= Modifier.fillMaxSize()) {
        val days = listOf<String>("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

        days.forEach {
            var expanded by remember { mutableStateOf(false) }
            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp).clickable {
                    expanded = !expanded
                }) {

                Column(modifier = Modifier.animateContentSize()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore, contentDescription = "")
                        Text(text = it, modifier=Modifier.weight(1f))
                        Text(text = "12°c / 14°c", textAlign = TextAlign.End)
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
fun fakeWeatherData() : WeatherData {
    val location = "Brussels"
    val latitude = 0.0
    val longitude = 0.0
    val elevation = 0L
    val weatherCode = WeatherCode.CLEAR_SKY
    val temperature = 100.0
    val precipitation = 0.0
    val windspeed = 100.0
    val windgusts = 100.0
    val windDirection = WeatherWindDirection.SOUTH


    val weathercodes = WeatherCode.values().toList()
    val winddirections = WeatherWindDirection.values().toList()

    val hourlyData = List(24) {
        val weatherCode = weathercodes.shuffled().first()
        val windDirection = winddirections.shuffled().first()

        val temp = Random.nextDouble(-10.0, 46.0)
        val precipitation = Random.nextDouble(0.0, 100.0)
        val windSpeed = Random.nextDouble(0.0, 130.0)
        val windGusts = Random.nextDouble(0.0, 150.0)

        WeatherHourData(ZonedDateTime.now().plusHours(it.toLong()), weatherCode, temp,  precipitation, windSpeed, windGusts, windDirection)
    }

    val dailyData = List(7) {
        val weatherCode = weathercodes.shuffled().first()
        val windDirection = winddirections.shuffled().first()
        val minTemp = Random.nextDouble(-10.0, 46.0)
        val maxTemp = Random.nextDouble(minTemp, 46.0)
        val precipitation = Random.nextDouble(0.0, 100.0)
        val windSpeed = Random.nextDouble(0.0, 130.0)
        val windGusts = Random.nextDouble(0.0, 150.0)

        WeatherDayData(ZonedDateTime.now().plusDays(1), weatherCode, minTemp, maxTemp, ZonedDateTime.now(), ZonedDateTime.now(), precipitation, windSpeed, windGusts, windDirection)
    }


    val weatherData = WeatherData(
        location,
        latitude,
        longitude,
        elevation,
        weatherCode,
        temperature,
        precipitation,
        windspeed,
        windgusts,
        windDirection,
        hourlyData,
        dailyData
    )

    return weatherData
}

@Composable
@Preview
fun WeatherCardPreview() {

    val weatherData = fakeWeatherData()

    WeatherCard(weatherData = weatherData)
}