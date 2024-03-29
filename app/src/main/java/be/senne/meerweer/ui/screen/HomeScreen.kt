package be.senne.meerweer.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pullrefresh.PullRefreshIndicator
import androidx.compose.material3.pullrefresh.pullRefresh
import androidx.compose.material3.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import be.senne.meerweer.R
import be.senne.meerweer.ui.component.WeatherCard
import be.senne.meerweer.ui.component.fakeWeatherData
import be.senne.meerweer.ui.event.HomeEvent
import be.senne.meerweer.ui.state.HomeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(state : State<HomeState>, onEvent: (HomeEvent) -> Unit) {
    val ui = state.value
    Box(modifier = Modifier.fillMaxSize()) {

        if(ui.locationsLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        else {

            if(ui.locationCount == 0) {
                Card(modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        onEvent(HomeEvent.RefreshAllWeatherData)
                    }) {
                    Text(text = stringResource(R.string.no_locations_click_refresh))
                }

            }
            
            else {

                val pagerState = rememberPagerState(pageCount = { ui.locationCount })
                HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) {

                    val pullRefreshState = rememberPullRefreshState(
                        refreshing = ui.locationLoading,
                        onRefresh = {
                            onEvent(HomeEvent.RefreshAllWeatherData)
                        }
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .pullRefresh(pullRefreshState)
                    ) {

                        if (!ui.locationLoading) {
                            ui.locationData[it]?.let {
                                WeatherCard(uiData = it)
                            } ?: run {
                                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                            }
                        }
                        PullRefreshIndicator(
                            refreshing = ui.locationLoading,
                            state = pullRefreshState,
                            modifier = Modifier.align(Alignment.Center)
                        )

                    }
                }
                Row(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(pagerState.pageCount) {
                        val color =
                            if (pagerState.currentPage == it) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .clip(CircleShape)
                                .background(color)
                                .size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreen2Prev() {
    val uiData = HomeState(
        locationsLoading = false,
        locationCount = 5,
        locationData = mapOf(
            Pair(0, fakeWeatherData()),
            Pair(1, fakeWeatherData()),
            Pair(2, fakeWeatherData()),
            Pair(3, fakeWeatherData()),
        )
    )


    val __state = MutableStateFlow(uiData)
    val _state = __state.asStateFlow()
    val state = _state.collectAsStateWithLifecycle()

    HomeScreen(state, {})
}