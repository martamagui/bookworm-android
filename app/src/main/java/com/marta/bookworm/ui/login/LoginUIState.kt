package com.marta.bookworm.ui.login

import android.net.Credentials

data class LoginUIState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val savedToken: Boolean = false,
    val credentials: Credentials? = null,
    val errorMessage: String = ""
)
