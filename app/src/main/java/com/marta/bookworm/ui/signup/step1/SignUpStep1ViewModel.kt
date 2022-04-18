package com.marta.bookworm.ui.signup.step1

import androidx.lifecycle.ViewModel
import com.marta.bookworm.api.NetworkService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class SignUpStep1ViewModel @Inject constructor(private val networkService: NetworkService) :
    ViewModel() {
        private val _signUpStep1UIState: MutableStateFlow<SignUpStep1UIState> = MutableStateFlow(
            SignUpStep1UIState()
        )
        val signUpStep1UIState: StateFlow<SignUpStep1UIState> get() = _signUpStep1UIState
}