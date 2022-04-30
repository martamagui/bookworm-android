package com.marta.bookworm.ui.signup.step2

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class SignUpStep2ViewModel : ViewModel() {
    private val _signUpStep2FUIState: MutableStateFlow<SignUpStep2UIState> =
        MutableStateFlow(SignUpStep2UIState())
    val signUpStep2FUIState: StateFlow<SignUpStep2UIState> get() = _signUpStep2FUIState

    fun validateAll(dob: String, pass: String, repPass: String) {
        val validatePassword = validatePassword(pass, repPass)
        if (validatePassword) {
            _signUpStep2FUIState.update { SignUpStep2UIState(isSuccess = true) }
        }
    }

    private fun validatePassword(pass: String, repPass: String): Boolean {
        if (pass.length > 4 && pass == repPass) {
            val regexNum = Regex("[0123456789]")
            val regexAbc = Regex("[a-z]")
            val regexCaps = Regex("[A-B]")
            val regexWSpaces = Regex("[ \\s\\n]")
            if (!regexNum.containsMatchIn(pass) || !regexAbc.containsMatchIn(pass) ||
                regexWSpaces.containsMatchIn(pass) || !regexCaps.containsMatchIn(pass)
            ) {
                setErrorMsg("Password must contain a character (a-z),(A-Z) and (0-9)")
                return false
            }
        }
        Log.d("pass", "Todo OK")
        return true
    }

    private fun setErrorMsg(msg: String) {
        _signUpStep2FUIState.update { SignUpStep2UIState(isError = true, errorMssg = msg) }
    }

    fun resetError2() {
        _signUpStep2FUIState.update { SignUpStep2UIState(isError = false) }
    }
}