package com.marta.bookworm.model.response

data class UserResponse(
    val _id: String,
    val userName: String,
    val address: String?,
    val avatar: String?,
    val banner: String?,
    val birthDate: String?,
    val city: String?,
    val description: String?,
    val email: String?,
    val following: List<String>?,
    val followers: Int?,
    val fullName: String?,
    val password: String?,
    val reviews: List<String>?,
    val savedReviewsIds: List<String>?,
    val subscribedToNewsLetter: Boolean?
)