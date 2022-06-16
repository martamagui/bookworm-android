package com.marta.bookworm.ui.profile.settings.editProfile

import android.net.Uri
import android.widget.EditText
import androidx.lifecycle.ViewModel
import com.marta.bookworm.api.NetworkService
import com.marta.bookworm.db.BookWorm_Database
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val networkService: NetworkService,
    private val db: BookWorm_Database
) : ViewModel() {
    private val _editprofileUIState: MutableStateFlow<EditProfileUIState> =
        MutableStateFlow(EditProfileUIState())
    val editProfileUIState: StateFlow<EditProfileUIState> get() = _editprofileUIState


    fun setAvatarURI(avatarUri: Uri) {
        TODO("Not yet implemented")
    }

    fun setBannerURI(bannerUri: Uri) {
        TODO("Not yet implemented")
    }

    fun submitChanges(etUsername: EditText, etmlProfileDescription: EditText) {
        TODO("Not yet implemented")
    }

    private fun uploadPicture(rute: String) {

    }
}