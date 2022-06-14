package com.marta.bookworm.ui.createReview.step2

data class CreateReviewStep2UIState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMsg: String = ""
)
