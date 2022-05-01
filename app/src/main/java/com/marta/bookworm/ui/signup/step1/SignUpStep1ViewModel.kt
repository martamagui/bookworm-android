package com.marta.bookworm.ui.signup.step1

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marta.bookworm.api.NetworkService
import com.marta.bookworm.model.body.user.UserBody
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpStep1ViewModel @Inject constructor(private val networkService: NetworkService) :
    ViewModel() {
    private val _signUpStep1FUIState: MutableStateFlow<SignUpStep1UIState> =
        MutableStateFlow(SignUpStep1UIState())
    val signUpStep1FUIState: StateFlow<SignUpStep1UIState> get() = _signUpStep1FUIState

    fun validateEmail(email: String): Boolean {
        var availableMail = true
        viewModelScope.launch(Dispatchers.IO) {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                val isTaken = networkService.isEmailTaken(email)
                if (isTaken.message == "available") {
                    availableMail = true
                }else{
                    setErrorMsg("Plase introduce a valid email")
                    availableMail = false
                }
            }
        }
        return availableMail
    }

    fun validateUserName(userName: String): Boolean {
        var availableUserName = false
        viewModelScope.launch(Dispatchers.IO) {
            val isTaken = networkService.isUserNameTaken(userName)
            if (isTaken.message == "available") {
                availableUserName = true
            }else {
                setErrorMsg("Please introduce a valid userName")
            }
        }
        return availableUserName
    }

    private fun setErrorMsg(msg: String) {
        _signUpStep1FUIState.update { SignUpStep1UIState(isError = true, errorMsg = msg) }
    }

    fun resetError() {
        _signUpStep1FUIState.update { SignUpStep1UIState(isError = false) }
    }

    private fun validateTextFields(text: String): Boolean {
        if (text != null && text.length > 2) {
            return true
        }
        return false
    }

    fun validateAll(email: String, userName: String, fullName: String) {
        Log.d("Aaa", "$email $userName $fullName")
        val emailValidation = validateEmail(email)
        val userNameValidation = validateTextFields(userName)
        val validateFullName = validateTextFields(fullName)
        if ( (emailValidation && userNameValidation) && (validateFullName && !signUpStep1FUIState.value.isError) ) {
            _signUpStep1FUIState.update { SignUpStep1UIState(isSuccess = true) }
        } else {
            setErrorMsg("Invalid data, please")
        }
    }
}