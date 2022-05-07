package com.marta.bookworm.model.response


import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("_id")
    val id: String,
    @SerializedName("userId")
    val userId: String,
    @SerializedName("bookTitle")
    val bookTitle: String,
    @SerializedName("bookAuthor")
    val bookAuthor: String,
    @SerializedName("score")
    val score: Double,
    @SerializedName("image")
    val image: String,
    @SerializedName("amazonLink")
    val amazonLink: String,
    @SerializedName("reviewDescription")
    val reviewDescription: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("likes")
    val likes: Int,
    @SerializedName("hastags")
    val hastags: List<String>,
    val user: UserResponse
)