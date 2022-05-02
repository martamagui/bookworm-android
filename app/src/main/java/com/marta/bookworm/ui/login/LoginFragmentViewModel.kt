package com.marta.bookworm.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marta.bookworm.api.NetworkService
import com.marta.bookworm.db.BookWorm_Database
import com.marta.bookworm.db.entities.Token
import com.marta.bookworm.model.body.user.Credentials
import com.marta.bookworm.model.response.TokenResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModel @Inject constructor(
    private val networkService: NetworkService,
    private val db: BookWorm_Database
) :
    ViewModel() {
    private val _loginUIState: MutableStateFlow<LoginUIState> = MutableStateFlow(LoginUIState())
    val loginUIState: StateFlow<LoginUIState>
        get() = _loginUIState

    fun loginAction(credentials: Credentials) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loginUIState.update { LoginUIState(isLoading = true) }
                val token: TokenResponse = networkService.login(credentials);
                saveToken(credentials.email, token.token)

            } catch (e: Exception) {
                Log.e("Exception", "${e}")
                _loginUIState.update {
                    LoginUIState(
                        isLoading = false,
                        errorMessage = "$e",
                        isError = true
                    )
                }
            }
        }
    }

    fun saveToken(email: String, token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            db.dao().saveToken(Token(email, token))
            _loginUIState.update { LoginUIState(isLoading = false, isSuccess = true) }
        }
    }

}