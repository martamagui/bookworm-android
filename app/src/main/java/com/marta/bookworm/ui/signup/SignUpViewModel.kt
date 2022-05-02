package com.marta.bookworm.ui.signup

import android.util.Log
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
class SignUpViewModel @Inject constructor(private val networkService: NetworkService) :
    ViewModel() {

    private val _user: MutableStateFlow<UserBody> = MutableStateFlow(UserBody())

    val user: StateFlow<UserBody> get() = _user

    fun setStepOneData(email: String, userName: String, fullName: String) {
        _user.update { UserBody(email = email, userName = userName, fullName = fullName) }
    }

    fun setStepTwoData(dob: String, password: String) {
        _user.update {
            UserBody(
                email = user.value.email,
                userName = user.value.userName,
                fullName = user.value.fullName,
                birthDate = dob,
                password = password
            )
        }
        Log.d("Datos", "$user")
    }

    fun setStepThreeData(country: String, city: String, address: String) {
        _user.update {
            UserBody(
                email = user.value.email,
                userName = user.value.userName,
                fullName = user.value.fullName,
                birthDate = user.value.birthDate,
                password = user.value.password,
                country = country,
                city = city,
                address = address,
                subscribedToNewsLetter = true,
                avatar = "https://i.kym-cdn.com/photos/images/facebook/000/959/794/310.jpg",
                banner = "https://www.meme-arsenal.com/memes/089f4c766373a00975d7a2e25ab2d524.jpg",
                description = "Hi! just joined Bookworm! :D"
            )
        }
        register()
    }

    private fun register() {
        viewModelScope.launch(Dispatchers.IO) {
            networkService.createNewUser(user = user.value)
        }
    }

}