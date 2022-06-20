package com.marta.bookworm.ui.profile.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marta.bookworm.api.NetworkService
import com.marta.bookworm.db.BookWorm_Database
import com.marta.bookworm.ui.profile.ProfileUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val networkService: NetworkService,
    private val db: BookWorm_Database
) : ViewModel() {
    private val _settingsUIState: MutableStateFlow<SettingsUIState> =
        MutableStateFlow(SettingsUIState())
    val settingsUIState: StateFlow<SettingsUIState> get() = _settingsUIState

    fun deleteAccount() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val myToken = getMyToken()
                if (myToken != null) {
                    val userInfo = networkService.deleteUser("Bearer $myToken")
                    _settingsUIState.update {
                        SettingsUIState(
                            isLoading = false,
                            isSuccess = true,
                        )
                    }
                }
            } catch (error: Error) {
                updateError("Error retrieveing information")
            }
        }
    }

    fun updateError(msg: String) {
        _settingsUIState.update {
            SettingsUIState(
                isLoading = false,
                isError = true,
                errorMsg = msg
            )
        }
    }

    fun logOut(){
        viewModelScope.launch(Dispatchers.IO) {
            db.dao().delteToken()
        }
    }

    //--BD
    private suspend fun getMyToken(): String {
        return db.dao().findMyToken().token
    }

}