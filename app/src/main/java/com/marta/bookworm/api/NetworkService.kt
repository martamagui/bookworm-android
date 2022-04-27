package com.marta.bookworm.api

import com.marta.bookworm.model.body.user.*
import com.marta.bookworm.model.response.StandarResponse
import com.marta.bookworm.model.response.TokenResponse
import com.marta.bookworm.model.response.UserResponse
import retrofit2.http.*

interface NetworkService {
    //-------------------------------------- USER
    //--- GET
    @GET("user")
    suspend fun getUnfilteredUsers(): List<UserResponse>

    @GET("user/{userId}")
    suspend fun getUserById(@Path("userId") userId: String): UserResponse

    @GET("user/myProfile/{userId}")
    suspend fun getMyProfile(
        @Header("Authorization") token: String,
        @Path("userId") userId: String
    ): UserResponse

    @GET("user/list-ids")
    suspend fun getListByIdsList(@Query("userIds") tag: List<String>): List<UserResponse>

    @GET("user/list-names/{userName}")
    suspend fun searchUserByName(@Path("userName") userName: String): List<UserResponse>

    @GET("user/is-username-taken/{userName}")
    suspend fun isUserNameTaken(@Path("userName") userName: String): StandarResponse

    @GET("user/is-email-taken/{email}")
    suspend fun isEmailTaken(@Path("email") email: String): StandarResponse

    //--- POST
    @POST("user")
    suspend fun createNewUser(@Body user: UserBody): StandarResponse

    @POST("user/login")
    suspend fun login(@Body credentials: Credentials): TokenResponse

    //--- PUT
    @PUT("user/update-password/{userId}")
    suspend fun updatePassword(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Body user: UserBody
    ): StandarResponse

    @PUT("user/update-email/{userId}")
    suspend fun updateEmail(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Body user: UserBody
    ): StandarResponse

    @PUT("user/update-username/{userId}")
    suspend fun updateUserName(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Body user: UserBody
    ): StandarResponse

    @PUT("user/update-newsletter/{userId}")
    suspend fun updateNewsletter(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Body user: UserBody
    ): StandarResponse

    @PUT("user/update-banner/{userId}")
    suspend fun updateBanner(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Body user: ProfileBody
    ): StandarResponse

    @PUT("user/update-avatar/{userId}")
    suspend fun updateAvatar(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Body user: ProfileBody
    ): StandarResponse

    @PUT("user/unfollow/{userId}")
    suspend fun unfollowUser(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Body followingBody: FollowBody
    ): StandarResponse

    @PUT("user/follow/{userId}")
    suspend fun followUser(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Body followingBody: FollowBody
    ): StandarResponse

    @PUT("user/remove-saved-review/{userId}")
    suspend fun removeSavedReview(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Body savedReview: SavedReviewBody
    ): StandarResponse

    @PUT("user/save-review/{userId}")
    suspend fun savedReview(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Body savedReview: SavedReviewBody
    ): StandarResponse

    //--- DELETE
    @DELETE("user/{userId}")
    suspend fun deleteUser(
        @Header("Authorization") token: String,
        @Path("userId") userId: String
    ): StandarResponse


}