package be.senne.meerweer.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()


    fun onEvent(event : HomeEvent) {
        when(event) {
            is HomeEvent.Button1Clicked -> onButton1Clicked(event.id)
            is HomeEvent.Button2Clicked -> onButton2Clicked(event.id)
        }
    }

    private fun onButton1Clicked(id : String) {
        Log.wtf("", "Button 1 clicked: $id")
        viewModelScope.launch {
            _state.value = _state.value.copy(test = "Hello World")
        }
    }

    private fun onButton2Clicked(id : String) {
        Log.wtf("", "Button 2 clicked: $id")
    }
}