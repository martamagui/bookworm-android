package com.marta.bookworm.ui.profile.settings

data class SettingsUIState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMsg: String? = null
)