package com.marta.bookworm.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marta.bookworm.api.NetworkService
import com.marta.bookworm.model.body.user.Credentials
import com.marta.bookworm.model.response.TokenResponse
import com.marta.bookworm.ui.MainActivityUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModel  @Inject constructor(private val networkService: NetworkService) :ViewModel() {
    private val _loginUIState: MutableStateFlow<LoginUIState> = MutableStateFlow(LoginUIState())
    private val _activityMainActivity: MutableStateFlow<MainActivityUIState> = MutableStateFlow(
        MainActivityUIState()
    )
    val loginUIState: StateFlow<LoginUIState>
    get()= _loginUIState

    fun loginAction(credentials: Credentials) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loginUIState.update { LoginUIState(isLoading = true) }
                val token: TokenResponse = networkService.login(credentials);
                Log.d("Token", "$token")
                _loginUIState.update { LoginUIState(isLoading = false, isSuccess = true)}
            }catch (e: Exception){
                Log.e("Exception", "${e}")
                _loginUIState.update { LoginUIState(isLoading = false, errorMessage = "$e", isError = true ) }
            }
        }
    }

    fun saveToken(token: String){
        //TODO
    }
}