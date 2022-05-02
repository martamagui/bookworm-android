package com.marta.bookworm.ui.signup.step3

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update


class SignUpStep3ViewModel:  ViewModel() {
    private val _signUpStep3FUIState: MutableStateFlow<SignUpStep3UIState> = MutableStateFlow(
        SignUpStep3UIState()
    )
    val signUpStep3FUIState: StateFlow<SignUpStep3UIState> get() = _signUpStep3FUIState

    fun validateAll(country: String, city: String, address: String, agreement: Boolean) {
        val valCountry = validateTextFields(country)
        val valCity = validateTextFields(city)
        val valAddress = validateTextFields(address)
        if(agreement){
            if (valAddress && valCity && valCountry) {
                _signUpStep3FUIState.update { SignUpStep3UIState(isSuccess = true) }
            } else {
                setErrorMsg("Please fill all fields")
            }
        }else{
            setErrorMsg("Please accept terms and conditions")
        }
    }

    private fun setErrorMsg(msg: String) {
        _signUpStep3FUIState.update { SignUpStep3UIState(isError = true, errorMsg = msg) }
    }

    fun resetError() {
        _signUpStep3FUIState.update { SignUpStep3UIState(isError = false) }
    }

    private fun validateTextFields(text: String): Boolean {
        if (text != null && text.length > 2) {
            return true
        }
        return false
    }
}