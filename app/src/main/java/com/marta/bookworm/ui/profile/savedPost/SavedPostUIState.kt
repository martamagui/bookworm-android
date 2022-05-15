package com.marta.bookworm.ui.profile.savedPost

import com.marta.bookworm.api.model.response.ReviewResponse
import com.marta.bookworm.api.model.response.UserResponse

data class SavedPostUIState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMsg: String? = null,
    val reviews: List<ReviewResponse>? = null
)


