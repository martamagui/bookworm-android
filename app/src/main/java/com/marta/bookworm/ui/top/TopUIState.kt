package com.marta.bookworm.ui.top

import com.marta.bookworm.api.model.response.TopResponse

data class TopUIState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMsg: String? = null,
    val topResponse: List<TopResponse>? = null,
)
