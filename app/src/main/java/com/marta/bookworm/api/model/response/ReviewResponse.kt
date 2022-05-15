package com.marta.bookworm.api.model.response

import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName(value="id", alternate=["_id"])
    val id: String,
    @SerializedName("userId")
    val userId: UserResponse,
    @SerializedName("bookTitle")
    val bookTitle: String,
    @SerializedName("bookAuthor")
    val bookAuthor: String,
    @SerializedName("score")
    val score: Double,
    @SerializedName("image")
    val image: String?,
    @SerializedName("amazonLink")
    val amazonLink: String,
    @SerializedName("reviewDescription")
    val reviewDescription: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("likes")
    val likes:  List<String?>,
    @SerializedName("liked")
    var liked: Boolean?,
    @SerializedName("likesAmount")
    var likesAmount: Int?,
    @SerializedName("saved")
    var saved: Boolean?,
    @SerializedName("hastags")
    val hastags: List<String>,
)