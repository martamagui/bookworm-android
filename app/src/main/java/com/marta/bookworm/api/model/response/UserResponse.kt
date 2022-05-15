package com.marta.bookworm.api.model.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("_id")
    val id: String,
    val userName: String,
    val address: String?,
    val avatar: String?,
    val banner: String?,
    val birthDate: String?,
    val city: String?,
    val description: String?,
    val email: String?,
    val following: List<String>?,
    val followingAmount: Int?,
    val followers: Int?,
    val fullName: String?,
    val password: String?,
    val reviews: List<ReviewResponse>?,
    val savedReviewsIds: List<String>?,
    val subscribedToNewsLetter: Boolean?
)