package be.senne.meerweer.ui.components2

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.senne.meerweer.ui.theme.HetWeerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen2() {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text="Settings",
                    style=MaterialTheme.typography.titleMedium
                )
            })
        }) {
        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(it)
            .padding(16.dp)) {

            val state = SettingToggleGroupState(0)
            val items = listOf(
                SettingToggleGroupEntry<Int>(
                    "kmh",
                    0
                ),
                SettingToggleGroupEntry<Int>(
                    "mph",
                    1
                )
            )

            SettingGroup("Units of Measurement") {
                SettingToggle("Setting 1", state, items)
                SettingToggle("Setting 2", state, items)
                SettingToggle("Setting 3", state, items)
            }
        }
    }
}

@Composable
fun SettingGroup(name: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = name)
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


data class SettingToggleGroupState(
    val currentIndex : Int
)
data class SettingToggleGroupEntry<T>(
    val name : String,
    val value: T
)

data class ToggleData<T>(
    val onToggle: (T) -> Unit
)
@Composable
fun <T> SettingToggle(name: String, state : SettingToggleGroupState, items : List<SettingToggleGroupEntry<T>>) {
    Surface {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = name, )
                Spacer(modifier = Modifier.weight(1f))

                Row {
                    items.forEachIndexed { i, it ->
                        val selected = state.currentIndex == i
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

                        OutlinedButton(
                            onClick = {},
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

    val state = SettingToggleGroupState(0)
    val items = listOf(
        SettingToggleGroupEntry<Int>(
            "kmh",
            0
        ),
        SettingToggleGroupEntry<Int>(
            "mph",
            1
        )
    )

    HetWeerTheme {
        SettingToggle("Setting 1", state, items)
    }
}