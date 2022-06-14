package com.marta.bookworm.api.model.body.review

data class ReviewBody (
    val bookTitle: String="",
    val bookAuthor: String="",
    val score: Double = 5.0,
    val image: String = "",
    val reviewDescription: String = "",
    val hastags: List<String> = listOf(),
)