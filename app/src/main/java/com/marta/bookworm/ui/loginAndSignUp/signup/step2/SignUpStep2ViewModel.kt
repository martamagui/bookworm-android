package com.marta.bookworm.ui.loginAndSignUp.signup.step2

import androidx.lifecycle.ViewModel
import com.marta.bookworm.ui.common.validatePassword
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.sql.Date
import java.text.SimpleDateFormat

class SignUpStep2ViewModel : ViewModel() {
    private val _signUpStep2FUIState: MutableStateFlow<SignUpStep2UIState> =
        MutableStateFlow(SignUpStep2UIState())
    val signUpStep2FUIState: StateFlow<SignUpStep2UIState> get() = _signUpStep2FUIState

    fun validateAll(dob: String, pass: String, repPass: String) {
        val validatePassword = validatePassword(pass, repPass)
        if (validatePassword) {
            _signUpStep2FUIState.update { SignUpStep2UIState(isSuccess = true) }
        }else{
            setErrorMsg("Password must contain a character (a-z),(A-Z) and (0-9)")
        }
    }

    private fun setErrorMsg(msg: String) {
        _signUpStep2FUIState.update { SignUpStep2UIState(isError = true, errorMssg = msg) }
    }

    fun resetError2() {
        _signUpStep2FUIState.update { SignUpStep2UIState(isError = false) }
    }

    fun setDob(date: Long) {
        val pattern = "yyyy-MM-dd"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val formattedDate = simpleDateFormat.format(Date(date))
        _signUpStep2FUIState.update { SignUpStep2UIState(dob = formattedDate) }
    }

    fun resetSuccess() {
        _signUpStep2FUIState.update { SignUpStep2UIState(isSuccess = false) }
    }
}