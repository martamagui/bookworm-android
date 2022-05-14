package com.marta.bookworm.ui.profile

import com.marta.bookworm.model.response.UserResponse

data class ProfileUIState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMsg: String? = null,
    val user: UserResponse? = null
)
