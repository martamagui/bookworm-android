package com.marta.bookworm.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marta.bookworm.api.NetworkService
import com.marta.bookworm.db.BookWorm_Database
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val networkService: NetworkService,
    private val db: BookWorm_Database
) : ViewModel() {
    private val _profileUIState: MutableStateFlow<ProfileUIState> =
        MutableStateFlow(ProfileUIState())
    val profileUIState: StateFlow<ProfileUIState> get() = _profileUIState

    fun getProfileInfo(id: String) {
        if (id != "empty") {
            getOthersProfile(id)
        } else {
            getMYProfileInfo()
        }
    }

    fun getOthersProfile(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val myToken = getMyToken()
                if (myToken != null) {
                    val userInfo = networkService.getUserById(id, "Bearer $myToken")
                    _profileUIState.update {
                        ProfileUIState(
                            isLoading = false,
                            isSuccess = true,
                            user = userInfo
                        )
                    }
                }
            } catch (error: Error) {
                updateError("Error retrieveing information")
            }
        }
    }

    fun getMYProfileInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val myToken = getMyToken()
                if (myToken != null) {
                    val userInfo = networkService.getMyProfile("Bearer $myToken")
                    _profileUIState.update {
                        ProfileUIState(
                            isLoading = false,
                            isSuccess = true,
                            user = userInfo
                        )
                    }
                }
            } catch (error: Error) {
                updateError("Error retrieveing information")
            }
        }
    }

    fun updateError(msg: String) {
        _profileUIState.update { ProfileUIState(isLoading = false, isError = true, errorMsg = msg) }
    }

    fun followUnfollow(userToFollow: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val myToken = getMyToken()
                if (myToken != null) {
                    networkService.likeDislike("Bearer ${myToken}", userToFollow)
                }
            } catch (error: Error) {
                updateError("Couldn't update follow.")
            }
        }
    }

    //--BD
    private suspend fun getMyToken(): String {
        return db.dao().findMyToken().token
    }
}