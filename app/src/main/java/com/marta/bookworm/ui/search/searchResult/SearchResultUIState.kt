package com.marta.bookworm.ui.search.searchResult

import com.marta.bookworm.api.model.response.ReviewResponse

data class SearchResultUIState (
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMsg: String? = null,
    val feedList: List<ReviewResponse>? = null
)