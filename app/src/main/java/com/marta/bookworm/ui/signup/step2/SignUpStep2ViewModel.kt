package com.marta.bookworm.ui.signup.step2

import androidx.lifecycle.ViewModel
import com.marta.bookworm.api.NetworkService
import com.marta.bookworm.ui.signup.step1.SignUpStep1UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class SignUpStep2ViewModel @Inject constructor(private val networkService: NetworkService) :
    ViewModel() {
    private val _signUpStep2UIState: MutableStateFlow<SignUpStep2UIState> = MutableStateFlow(
        SignUpStep2UIState()
    )
    val signUpStep2UIState: MutableStateFlow<SignUpStep2UIState> get() =  _signUpStep2UIState
}