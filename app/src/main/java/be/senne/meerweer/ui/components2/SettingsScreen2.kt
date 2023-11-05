package be.senne.meerweer.ui.components2

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.senne.meerweer.domain.model.MeasurementUnit
import be.senne.meerweer.ui.screens.settings.SettingsEvent
import be.senne.meerweer.ui.screens.settings.SettingsState
import be.senne.meerweer.ui.theme.HetWeerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen2(state: State<SettingsState>, onEvent: (SettingsEvent) -> Unit) {
    val ui = state.value

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text="Settings",
                    style=MaterialTheme.typography.titleLarge
                )
            })
        }) {
        if(ui.isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        else {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(it)
                    .padding(16.dp)
            ) {


                val temp_items = listOf(
                    SettingToggleGroupEntry<MeasurementUnit>(
                        "°C",
                        MeasurementUnit.METRIC
                    ),
                    SettingToggleGroupEntry<MeasurementUnit>(
                        "°F",
                        MeasurementUnit.IMPERIAL
                    )
                )
                val wind_items = listOf(
                    SettingToggleGroupEntry<MeasurementUnit>(
                        "km/h",
                        MeasurementUnit.METRIC
                    ),
                    SettingToggleGroupEntry<MeasurementUnit>(
                        "mph",
                        MeasurementUnit.IMPERIAL
                    )
                )
                val precipitation_items = listOf(
                    SettingToggleGroupEntry<MeasurementUnit>(
                        "mm",
                        MeasurementUnit.METRIC
                    ),
                    SettingToggleGroupEntry<MeasurementUnit>(
                        "in",
                        MeasurementUnit.IMPERIAL
                    )
                )

                SettingGroup("Units of Measurement") {
                    SettingToggle("Temperature Unit", ui.currentTemperatureUnit, temp_items, {
                        onEvent(SettingsEvent.SetTemperatureUnit(it))
                    })
                    SettingToggle("Speed Unit", ui.currentSpeedUnit, wind_items, {
                        onEvent(SettingsEvent.SetSpeedUnit(it))
                    })
                    SettingToggle(
                        "Precipitation Unit",
                        ui.currentPrecipitationUnit,
                        precipitation_items,
                        {
                            onEvent(SettingsEvent.SetPrecipitationUnit(it))
                        })
                }
            }
        }
    }
}

@Composable
fun SettingGroup(name: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = name, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Surface(
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4)
        ) {
            Column {
                content()
            }
        }
    }
}

data class SettingToggleGroupEntry<T>(
    val name : String,
    val value: T
)
@Composable
fun <T> SettingToggle(name: String, selectedIndex : Int, items : List<SettingToggleGroupEntry<T>>, onToggle: (T) -> Unit) {
    Surface {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = name)
                Spacer(modifier = Modifier.weight(1f))

                Row(modifier = Modifier.width(200.dp)) {
                    items.forEachIndexed { i, it ->
                        val selected = selectedIndex == i
                        val topStartP : Int
                        val topEndP : Int
                        val bottomEndP : Int
                        val bottomStartP : Int
                        val buttonColors : ButtonColors

                        if(selected) {
                            buttonColors = ButtonDefaults.outlinedButtonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                        }
                        else {
                            buttonColors = ButtonDefaults.outlinedButtonColors()
                        }

                        when(i) {
                            0 -> {
                                topStartP = 50
                                topEndP = 0
                                bottomEndP = 0
                                bottomStartP = 50
                            }
                            (items.size-1) -> {
                                topStartP = 0
                                topEndP = 50
                                bottomEndP = 50
                                bottomStartP = 0
                            }
                            else -> {
                                topStartP = 0
                                topEndP = 0
                                bottomEndP = 0
                                bottomStartP = 0
                            }
                        }

                        OutlinedButton(modifier = Modifier.weight(1f),
                            onClick = {
                                onToggle(it.value)
                            },
                            shape = RoundedCornerShape(topStartPercent = topStartP, topEndPercent = topEndP, bottomEndPercent = bottomEndP, bottomStartPercent = bottomStartP),
                            colors = buttonColors
                        ) {
                            Text(it.name)
                        }
                    }
                }
            }
            Divider()
        }
    }
}

@Preview
@Composable
fun GroupButton() {

    val state = 0
    val items = listOf(
        SettingToggleGroupEntry<Int>(
            "km/h",
            0
        ),
        SettingToggleGroupEntry<Int>(
            "mph",
            1
        )
    )

    HetWeerTheme {
        SettingToggle("Setting 1", state, items, {})
    }
}