package com.marta.bookworm.ui.createReview.step1

data class CreateReviewStep1UIState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMsg: String = "",
    val imageLink: String? = null
)
