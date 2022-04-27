package com.marta.bookworm.ui.signup

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marta.bookworm.api.NetworkService
import com.marta.bookworm.model.body.user.UserBody
import com.marta.bookworm.ui.signup.step1.SignUpStep1UIState
import com.marta.bookworm.ui.signup.step2.SignUpStep2UIState
import com.marta.bookworm.ui.signup.step3.SignUpStep3UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
    private fun setStep1(email: String, userName: String, fullName: String) {
        if ((validateEmail(email)) && (userName != null && userName.length > 2) && (fullName != null && fullName.length > 2)) {
            _user.update { UserBody(email = email, userName = userName, fullName = fullName.trim()) }
        }else{
            _signUpStep1FUIState.update { SignUpStep1UIState(isError = true) }
        }
    }

    fun validateEmail(email: String): Boolean {
        var availableMail = false
        viewModelScope.launch(Dispatchers.IO){
            if(Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                val isTaken = networkService.isEmailTaken(email)
                if(isTaken.message=="available"){
                    availableMail = true
                }
            }
        }
        if(availableMail == false){
            setErrorMsg("Plase introduce a valid email")
        }
        return availableMail
    }

    private fun setErrorMsg(msg:String){
        _signUpStep1FUIState.update { SignUpStep1UIState(isError = true, errorMssg = msg) }
    }
    
    fun resetError1(){
        _signUpStep1FUIState.update { SignUpStep1UIState(isError = false)}
    }
    fun resetError2(){
        _signUpStep2FUIState.update { SignUpStep2UIState(isError = false)}
    }
    fun resetError3(){
        _signUpStep2FUIState.update { SignUpStep2UIState(isError = false)}
    }

}