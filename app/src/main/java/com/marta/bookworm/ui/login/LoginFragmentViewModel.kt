package com.marta.bookworm.ui.login

import androidx.lifecycle.ViewModel
import com.marta.bookworm.api.NetworkService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModel  @Inject constructor(private val networkService: NetworkService) :ViewModel() {
    private val _loginUIState: MutableStateFlow<LoginUIState> = MutableStateFlow(LoginUIState())
    val loginUIState: StateFlow<LoginUIState>
    get()= _loginUIState
}