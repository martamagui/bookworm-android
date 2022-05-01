package com.marta.bookworm.ui.signup

import android.util.Log
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

    private val _signUpStep3FUIState: MutableStateFlow<SignUpStep3UIState> = MutableStateFlow(
        SignUpStep3UIState()
    )
    private val _user: MutableStateFlow<UserBody> = MutableStateFlow(UserBody())

    val signUpStep3FUIState: StateFlow<SignUpStep3UIState> get() = _signUpStep3FUIState

    val user: StateFlow<UserBody> get() = _user

    fun setStepOneData(email:String, userName: String, fullName: String){
        _user.update { UserBody(email = email, userName = userName, fullName = fullName ) }
    }

    fun setStepTwoData(dob:String, password: String){
        _user.update { UserBody(birthDate = dob, password = password ) }
        Log.d("Datos", "$user")
    }

}