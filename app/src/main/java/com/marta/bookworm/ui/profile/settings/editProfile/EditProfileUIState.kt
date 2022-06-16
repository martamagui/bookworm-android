package com.marta.bookworm.ui.profile.settings.editProfile

import android.net.Uri

data class EditProfileUIState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMsg: String? = null,
    val avatarUri: Uri? = null,
    val bannerUri: Uri? = null,
    val bannerLink: String = "",
    val avatarLink: String = "",
    val userName: String = "",
    val description: String = "",
    val token: String = ""
)