package com.marta.bookworm.ui.profile.settings.editProfile

import android.net.Uri
import android.util.Log
import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.marta.bookworm.api.NetworkService
import com.marta.bookworm.api.model.body.user.UserBody
import com.marta.bookworm.api.model.response.UserResponse
import com.marta.bookworm.db.BookWorm_Database
import com.marta.bookworm.ui.createReview.step1.CreateReviewStep1UIState
import com.marta.bookworm.ui.profile.ProfileUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val networkService: NetworkService,
    private val db: BookWorm_Database
) : ViewModel() {
    private val _editprofileUIState: MutableStateFlow<EditProfileUIState> =
        MutableStateFlow(EditProfileUIState())
    val editProfileUIState: StateFlow<EditProfileUIState> get() = _editprofileUIState
    private val imageRef = Firebase.storage.reference


    fun getProfileInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            setLoading(true)
            try {
                val myToken = getMyToken()
                if (myToken != null) {
                    val userInfo = networkService.getMyProfile("Bearer $myToken")
                    setCurrentProfileInfo(userInfo)
                }
            } catch (error: Error) {
                setError("Error retrieveing information")
            }
        }
    }

    fun submitChanges(username: String, profileDescription: String) {
        Log.d("Edit", "$username $profileDescription")
        setLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            if (editProfileUIState.value.avatarUri != null) {
                uploadImage(editProfileUIState.value.avatarUri!!, true, "images/avatar/")
            }
            if (editProfileUIState.value.bannerUri != null) {
                uploadImage(editProfileUIState.value.bannerUri!!, false, "images/banner/")
            }
            if (editProfileUIState.value.userName != username) {
                validateAndSetUserName(username)
            }
            if (editProfileUIState.value.description != profileDescription) {
                changeDescription(profileDescription)
            }
            if (!editProfileUIState.value.isError) {
                setSuccess()
            }
        }
    }

    private fun changeBanner(link: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                networkService.updateBanner(
                    editProfileUIState.value.token,
                    UserBody(banner = link)
                )
            } catch (error: Error) {
                Log.e("Profile", "$error")
                setError("Error changing banner")
            }
        }
    }

    private fun changeAvatar(link: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                networkService.updateAvatar(
                    editProfileUIState.value.token,
                    UserBody(avatar = link)
                )
            } catch (error: Error) {
                Log.e("Profile", "$error")
                setError("Error changing avatar")
            }
        }
    }

    private fun changeDescription(profileDescription: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                networkService.updateDescription(
                    editProfileUIState.value.token,
                    UserBody(description = profileDescription)
                )
                setDescription(profileDescription)
            } catch (error: Error) {
                Log.e("Profile", "$error")
                setError("Error changing description")
            }
        }
    }

    private suspend fun changeUserName(etUsername: String) {
        try {
            networkService.updateUserName(
                editProfileUIState.value.token,
                UserBody(userName = etUsername)
            )
        } catch (error: Error) {
            Log.e("Profile", "$error")
            setError("Error changing User Name")
        }
    }

    private fun uploadImage(imageUri: Uri, isAvatar: Boolean, route: String) {
        try {
            setLoading(true)
            if (imageUri != null) {
                val ref = imageRef.child("${route}${UUID.randomUUID()}")
                ref.putFile(imageUri)
                    .continueWithTask { task ->
                        if (!task.isSuccessful) {
                            setError("Failed image upload")
                            task.exception?.let { throw it }
                        }
                        ref.downloadUrl
                    }.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            if (isAvatar) {
                                setNewAvatar("${task.result}")
                                changeAvatar("${task.result}")
                            } else {
                                setNewBanner("${task.result}")
                                changeBanner("${task.result}")
                            }
                        } else {
                            setError("Failed image upload")
                        }
                    }
            } else {
                setError("Please, select a picture")
            }
        } catch (error: Error) {
            Log.e("Profile", "$error")
            setError("Fail on submit")
        }
        setLoading(false)
    }

    private fun validateAndSetUserName(userName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val isTaken = networkService.isUserNameTaken(userName)
            if (isTaken.message == "available") {
                changeUserName(userName)
                setUserName(userName)
            } else {
                setError("Please introduce a valid userName")
            }
        }
    }
    //-------------- DB

    private suspend fun getMyToken(): String {
        val token = db.dao().findAllToken()
        setToken(token[0].token)
        return token[0].token
    }

    //-------------- State Changes

    private fun setCurrentProfileInfo(userInfo: UserResponse) {
        val newState = _editprofileUIState.value.copy(
            userName = userInfo.userName,
            description = userInfo.description.toString(),
            avatarLink = userInfo.avatar.toString(),
            bannerLink = userInfo.banner.toString(),
            isLoading = false
        )
        _editprofileUIState.update { newState }
    }

    private fun setDescription(description: String) {
        val newState = _editprofileUIState.value.copy(description = description)
        _editprofileUIState.update { newState }
    }

    private fun setToken(token: String) {
        val newState = _editprofileUIState.value.copy(token = "Bearer $token")
        _editprofileUIState.update { newState }
    }

    private fun setUserName(name: String) {
        val newState = _editprofileUIState.value.copy(isError = false, userName = name)
        _editprofileUIState.update { newState }
    }

    fun setAvatarURI(avatarUri: Uri) {
        val newState = _editprofileUIState.value.copy(avatarUri = avatarUri)
        _editprofileUIState.update { newState }
    }

    fun setBannerURI(bannerUri: Uri) {
        val newState = _editprofileUIState.value.copy(bannerUri = bannerUri)
        _editprofileUIState.update { newState }
    }

    private fun setNewBanner(link: String) {
        val newState = _editprofileUIState.value.copy(isError = false, bannerLink = link)
        _editprofileUIState.update { newState }
    }

    private fun setNewAvatar(link: String) {
        val newState = _editprofileUIState.value.copy(isError = false, avatarLink = link)
        _editprofileUIState.update { newState }
    }

    private fun setError(msg: String) {
        val newState =
            _editprofileUIState.value.copy(isError = true, errorMsg = msg, isLoading = false)
        _editprofileUIState.update { newState }
    }

    private fun setSuccess() {
        val newState =
            _editprofileUIState.value.copy(isError = false, isSuccess = true, isLoading = false)
        _editprofileUIState.update { newState }
    }

    private fun setLoading(loading: Boolean) {
        val newState = _editprofileUIState.value.copy(isLoading = loading)
        _editprofileUIState.update { newState }
    }
}