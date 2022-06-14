package com.marta.bookworm.ui.createReview

data class CreateReviewSharedUIState(
    val author: String = "",
    val title: String = "",
    val image: String = "",
    val score: Double = 5.0,
    val description: String = "",
    val hashTags: List<String> = listOf()
)
