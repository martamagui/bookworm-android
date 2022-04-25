package com.marta.bookworm.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainActivityViewModel: ViewModel() {
    private val _mainActivityUIState: MutableStateFlow<MainActivityUIState> = MutableStateFlow(
        MainActivityUIState()
    )
    val mainActivityUIState: StateFlow<MainActivityUIState>
    get()=_mainActivityUIState
}