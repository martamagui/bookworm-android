package com.marta.bookworm.ui.createReview

data class CreateReviewStep1UIState(
    val urlPic: String = "",
    val  title: String = "",
    val author: String = "",
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMsg: String = "",
)
