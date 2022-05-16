package com.marta.bookworm.api.model.response

import com.google.gson.annotations.SerializedName

data class TopReviewResponse(
    @SerializedName(value="id", alternate=["_id"])
    val id: String,
    @SerializedName("userId")
    val userId: String,
    @SerializedName("bookTitle")
    val bookTitle: String,
    @SerializedName("bookAuthor")
    val bookAuthor: String,
    @SerializedName("image")
    val image: String?,
)