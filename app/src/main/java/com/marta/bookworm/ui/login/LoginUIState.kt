package com.marta.bookworm.ui.login

data class LoginUIState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val savedToken:Boolean = false
)
