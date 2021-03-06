package com.marta.bookworm.api

import com.marta.bookworm.api.model.body.review.ReviewBody
import com.marta.bookworm.api.model.body.user.*
import com.marta.bookworm.api.model.response.*
import retrofit2.http.*

interface NetworkService {
    //-------------------------------------- USER
    //--- GET

    @GET("user/{userId}")
    suspend fun getUserById(
        @Path("userId") userId: String,
        @Header("Authorization") token: String
    ): UserResponse

    @GET("profile/my-profile")
    suspend fun getMyProfile(
        @Header("Authorization") token: String
    ): UserResponse

    @GET("user/list-ids")
    suspend fun getListByIdsList(@Query("userIds") tag: List<String>): List<UserResponse>

    @GET("user/list-names/{userName}")
    suspend fun searchUserByName(@Path("userName") userName: String): List<UserResponse>

    @GET("user/is-username-taken/{userName}")
    suspend fun isUserNameTaken(@Path("userName") userName: String): StandarResponse

    @GET("user/is-email-taken/{email}")
    suspend fun isEmailTaken(@Path("email") email: String): StandarResponse

    @GET("profile/saved/posts")
    suspend fun getSavedPost(@Header("Authorization") token: String): List<ReviewResponse>?

    @GET("top-books")
    suspend fun getTopBooks(@Header("Authorization") token: String): List<TopResponse>?

    //--- POST
    @POST("user")
    suspend fun createNewUser(@Body user: UserBody): StandarResponse

    @POST("review")
    suspend fun createReview(
        @Header("Authorization") token: String,
        @Body review: ReviewBody
    ): StandarResponse

    @POST("user/login")
    suspend fun login(@Body credentials: Credentials): TokenResponse

    //--- PUT
    @PUT("user/update-password")
    suspend fun updatePassword(
        @Header("Authorization") token: String,
        @Body user: UserBody
    ): StandarResponse

    @PUT("user/update-username")
    suspend fun updateUserName(
        @Header("Authorization") token: String,
        @Body user: UserBody
    ): StandarResponse

    @PUT("user/update-banner")
    suspend fun updateBanner(
        @Header("Authorization") token: String,
        @Body user: UserBody
    ): StandarResponse

    @PUT("user/update-avatar")
    suspend fun updateAvatar(
        @Header("Authorization") token: String,
        @Body user: UserBody
    ): StandarResponse

    @PUT("user/update-description")
    suspend fun updateDescription(
        @Header("Authorization") token: String,
        @Body user: UserBody
    ): StandarResponse


    @PUT("user/follow/{userId}")
    suspend fun followUnfollowUser(
        @Header("Authorization") token: String,
        //User To follow
        @Path("userId") userId: String,
    ): StandarResponse

    //--- DELETE
    @DELETE("user")
    suspend fun deleteUser(
        @Header("Authorization") token: String,
    ): StandarResponse

    @DELETE("review/{reviewId}")
    suspend fun deleteReview(
        @Header("Authorization") token: String,
        @Path("reviewId") reviewId: String
    )

    //-------------------------------------- Review
    //--- GET
    @GET("review")
    suspend fun getAllPosts(@Header("Authorization") token: String): List<ReviewResponse>

    @GET("review/{id}")
    suspend fun getPostDetail(
        @Path("id") userId: String,
        @Header("Authorization") token: String
    ): ReviewResponse

    @GET("review/title/{bookTitle}")
    suspend fun getByTitle(
        @Path("bookTitle") title: String,
        @Header("Authorization") token: String
    ): List<ReviewResponse>

    @GET("find/hastag/{hastag}")
    suspend fun getByTag(
        @Path("hastag") tag: String,
        @Header("Authorization") token: String
    ): List<ReviewResponse>

    @GET("find/author/{author}")
    suspend fun getByAuthor(
        @Path("author") author: String,
        @Header("Authorization") token: String
    ): List<ReviewResponse>

    @PUT("review/like/{postId}")
    suspend fun likeDislike(
        @Header("Authorization") token: String,
        @Path("postId") userId: String,
    ): StandarResponse

    @PUT("user/save-review/{postId}")
    suspend fun saveUnsavePost(
        @Header("Authorization") token: String,
        @Path("postId") userId: String,
    ): StandarResponse

}