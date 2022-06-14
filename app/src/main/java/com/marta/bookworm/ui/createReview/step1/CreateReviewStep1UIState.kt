package com.marta.bookworm.ui.createReview.step1

import android.net.Uri

data class CreateReviewStep1UIState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMsg: String = "",
    val imageLink: String? = null,
    val imageToUpload: Uri? = null
)
