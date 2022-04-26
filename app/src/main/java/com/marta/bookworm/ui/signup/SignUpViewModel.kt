package com.marta.bookworm.ui.signup

import androidx.lifecycle.ViewModel
import com.marta.bookworm.api.NetworkService
import com.marta.bookworm.model.body.user.UserBody
import com.marta.bookworm.ui.signup.step1.SignUpStep1UIState
import com.marta.bookworm.ui.signup.step2.SignUpStep2UIState
import com.marta.bookworm.ui.signup.step3.SignUpStep3UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val networkService: NetworkService) :
    ViewModel() {
    private val _signUpStep1FUIState: MutableStateFlow<SignUpStep1UIState> =
        MutableStateFlow(SignUpStep1UIState())
    private val _signUpStep2FUIState: MutableStateFlow<SignUpStep2UIState> =
        MutableStateFlow(SignUpStep2UIState())
    private val _signUpStep3FUIState: MutableStateFlow<SignUpStep3UIState> = MutableStateFlow(
        SignUpStep3UIState()
    )
    private val _user: MutableStateFlow<UserBody> = MutableStateFlow(UserBody())
    val signUpStep1FUIState: StateFlow<SignUpStep1UIState> get() = _signUpStep1FUIState
    val signUpStep2FUIState: StateFlow<SignUpStep2UIState> get() = _signUpStep2FUIState
    val signUpStep3FUIState: StateFlow<SignUpStep3UIState> get() = _signUpStep3FUIState
    val user: StateFlow<UserBody> get() = _user
}