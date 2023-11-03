package be.senne.meerweer.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.senne.meerweer.domain.model.WeatherCode
import be.senne.meerweer.domain.model.WeatherData
import be.senne.meerweer.domain.model.WeatherDayData
import be.senne.meerweer.domain.model.WeatherHourData
import be.senne.meerweer.domain.model.WeatherWindDirection
import java.time.ZonedDateTime
import kotlin.math.roundToInt

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
            Icon(imageVector = Icons.Default.KeyboardDoubleArrowRight, contentDescription = "")
            Text(text = "Wind ${weatherData.windspeed}", modifier = Modifier.padding(top= 10.dp), style=MaterialTheme.typography.bodySmall)
        }
        Column(modifier= Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = Icons.Default.KeyboardDoubleArrowRight, contentDescription = "")
            Text(text = "Wind Direction", modifier = Modifier.padding(top= 10.dp), style=MaterialTheme.typography.bodySmall)
        }
        Column(modifier= Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = Icons.Default.KeyboardDoubleArrowRight, contentDescription = "")
            Text(text = "Precipitation", modifier = Modifier.padding(top= 10.dp), style=MaterialTheme.typography.bodySmall)
        }
        Column(modifier= Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = Icons.Default.KeyboardDoubleArrowRight, contentDescription = "")
            Text(text = "Sunrise ${dayNow.sunrise}", modifier = Modifier.padding(top= 10.dp), style=MaterialTheme.typography.bodySmall)
        }
        Column(modifier= Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = Icons.Default.KeyboardDoubleArrowRight, contentDescription = "")
            Text(text = "Sunset ${dayNow.sunset}", modifier = Modifier.padding(top= 10.dp), style=MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun WeatherHourSection(weatherData: WeatherData) {
    LazyRow() {
        items(24) {
            Text(text = "Hour $it", modifier=Modifier.padding(16.dp))
        }
    }
}

@Composable
fun WeatherDaySection(weatherData: WeatherData) {
    Column(modifier= Modifier.fillMaxSize()) {
        val days = listOf<String>("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

        days.forEach {
            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)) {
                var expanded by remember { mutableStateOf(false) }
                Row(modifier = Modifier.animateContentSize()) {
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
            }
        }
    }
}

fun fakeWeatherData() : WeatherData {
    val location = "Brussels"
    val latitude = 0.0
    val longitude = 0.0
    val elevation = 0L
    val weatherCode = WeatherCode.CLEAR_SKY
    val temperature = 0.0
    val windspeed = 0.0
    val windgusts = 0.0
    val windDirection = WeatherWindDirection.SOUTH

    val hourlyData = listOf<WeatherHourData>(
        WeatherHourData(ZonedDateTime.now(), weatherCode, temperature, 50.0, windspeed, windgusts, windDirection)
    )
    val dailyData = listOf<WeatherDayData>(
        WeatherDayData(ZonedDateTime.now(), weatherCode, temperature + 10, temperature + 5, ZonedDateTime.now(), ZonedDateTime.now(), 50.0, windspeed, windgusts, windDirection)
    )


    val weatherData = WeatherData(
        location,
        latitude,
        longitude,
        elevation,
        weatherCode,
        temperature,
        windspeed,
        windgusts,
        windDirection,
        hourlyData,
        dailyData
    )

    return weatherData
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview
fun WeatherCardPreview() {

    val weatherData = fakeWeatherData()

    WeatherCard(weatherData = weatherData)
}