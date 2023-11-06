package be.senne.meerweer.ui.components2

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import be.senne.meerweer.ui.screens.home.Event2
import be.senne.meerweer.ui.screens.home.State2

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen2(state : State<State2>, onEvent: (Event2) -> Unit) {
    val ui = state.value
    Box(modifier = Modifier.fillMaxSize()) {
        val pagerState = rememberPagerState(pageCount = { ui.locationCount })
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) {
            Card {
                ui.locationData[it]?.let {
                    Text("Item ${it.location}")

                } ?: kotlin.run {
                    CircularProgressIndicator()
                }
            }
        }
    }
}