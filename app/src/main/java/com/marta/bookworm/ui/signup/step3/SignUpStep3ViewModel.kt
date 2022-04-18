package com.marta.bookworm.ui.signup.step3

import androidx.lifecycle.ViewModel
import com.marta.bookworm.api.NetworkService
import com.marta.bookworm.ui.signup.step2.SignUpStep2UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class SignUpStep3ViewModel @Inject constructor(private val networkService: NetworkService) :
    ViewModel() {
    private val _signUpStep3UIState: MutableStateFlow<SignUpStep3UIState> = MutableStateFlow(
        SignUpStep3UIState()
    )
    val signUpStep3UIState: MutableStateFlow<SignUpStep3UIState> get() = _signUpStep3UIState
}