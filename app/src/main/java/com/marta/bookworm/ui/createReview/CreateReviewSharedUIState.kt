package com.marta.bookworm.ui.createReview

data class CreateReviewSharedUIState(
    val author: String = "",
    val title: String = "",
    val image: String = "",
    val score: String = "",
    val description: String = "",
    val hashTags: List<String> = listOf()
)
