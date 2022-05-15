package com.marta.bookworm.ui.feed

import com.marta.bookworm.api.model.response.ReviewResponse

data class FeedUIState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMsg: String = "",
    val feedList: List<ReviewResponse>? = null
)
