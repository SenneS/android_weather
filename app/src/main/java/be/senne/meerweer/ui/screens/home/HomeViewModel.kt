package be.senne.meerweer.ui.screens.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    var state by mutableStateOf(HomeState(""))
        private set

    fun onEvent(event : HomeEvent) {
        when(event) {
            is HomeEvent.Button1Clicked -> onButton1Clicked(event.id)
            is HomeEvent.Button2Clicked -> onButton2Clicked(event.id)
        }
    }

    private fun onButton1Clicked(id : String) {
        Log.wtf("", "Button 1 clicked: $id")
        viewModelScope.launch {
        }
    }

    private fun onButton2Clicked(id : String) {
        Log.wtf("", "Button 2 clicked: $id")
    }
}