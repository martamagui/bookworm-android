package com.marta.bookworm.ui.detail

import com.marta.bookworm.api.model.response.ReviewResponse

data class DetailUIState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMsg: String = "",
    val review: ReviewResponse? = null
)
