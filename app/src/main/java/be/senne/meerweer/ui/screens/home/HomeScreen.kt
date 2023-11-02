package be.senne.meerweer.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(state: State<HomeState>, onEvent: (HomeEvent) -> Unit) {
    val ui = state.value;
    /*
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = ui.test)

        Button(onClick = {onEvent(HomeEvent.Button1Clicked("1"))}) {
            Text(ui.test)
        }
        Button(onClick = {onEvent(HomeEvent.Button2Clicked("1"))}) {
            Text(text = "Button 2")
        }
    }
     */

    Box(modifier = Modifier.fillMaxSize()) {
        if(ui.areLocationsLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        else {
            val pagerState = rememberPagerState(pageCount = { 10 })
            HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) {
                Text(text = "Page $it")
            }
            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp), horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) {
                    val color = if (pagerState.currentPage == it) Color.DarkGray else Color.LightGray
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

@Preview
@Composable
fun HomeScreenPreview() {
    val __state = MutableStateFlow(HomeState("ABC"))
    val _state = __state.asStateFlow()
    val state = _state.collectAsStateWithLifecycle()
    HomeScreen(state = state) {}
}