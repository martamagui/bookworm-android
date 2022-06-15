package com.marta.bookworm.ui.profile.settings.updatePsw

data class UpdatePasswordUIState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMsg: String? = null
)
