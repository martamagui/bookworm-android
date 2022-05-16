package com.marta.bookworm.api.model.response

data class TopResponse(
    val _id: String,
    val reviews: List<ReviewResponse>,
    val total: Int
)

