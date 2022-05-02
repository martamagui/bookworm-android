package com.marta.bookworm.ui.feed

data class FeedUIState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val isSuccess: Boolean = false
    //TODO add feedResponse to models
)
