package com.marta.bookworm.ui.profile.settings.editProfile

data class EditProfileUIState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMsg: String? = null
)
