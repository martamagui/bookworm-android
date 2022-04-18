package com.marta.bookworm.api

import com.marta.bookworm.model.body.FollowBody
import com.marta.bookworm.model.body.ProfileBody
import com.marta.bookworm.model.body.SavedReviewBody
import com.marta.bookworm.model.body.UserBody
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
    suspend fun getMyProfile(@Path("userId") userId: String): UserResponse

    @GET("user/list-ids")
    suspend fun getListByIdsList(@Query("userIds") tag: List<String>): List<UserResponse>

    @GET("user/list-names/{userName}")
    suspend fun searchUserByName(@Path("userName") tag: List<String>): List<UserResponse>

    @GET("user/is-username-taken/{userName}")
    suspend fun isUserNameTaken(@Path("userName") tag: List<String>): StandarResponse

    @GET("user/is-email-taken/{email}")
    suspend fun isEmailTaken(@Path("email") tag: List<String>): StandarResponse

    //--- POST
    @POST("user")
    suspend fun createNewUser(@Body user: UserBody): StandarResponse

    @POST("user/login")
    suspend fun login(@Body email: String, password: String): TokenResponse

    //--- PUT
    @PUT("user/update-password/{userId}")
    suspend fun updatePassword(
        @Path("userId") userId: String,
        @Body user: UserBody
    ): StandarResponse

    @PUT("user/update-email/{userId}")
    suspend fun updateEmail(@Path("userId") userId: String, @Body user: UserBody): StandarResponse

    @PUT("user/update-username/{userId}")
    suspend fun updateUserName(
        @Path("userId") userId: String,
        @Body user: UserBody
    ): StandarResponse

    @PUT("user/update-newsletter/{userId}")
    suspend fun updateNewsletter(
        @Path("userId") userId: String,
        @Body user: UserBody
    ): StandarResponse

    @PUT("user/update-banner/{userId}")
    suspend fun updateBanner(
        @Path("userId") userId: String,
        @Body user: ProfileBody
    ): StandarResponse

    @PUT("user/update-avatar/{userId}")
    suspend fun updateAvatar(
        @Path("userId") userId: String,
        @Body user: ProfileBody
    ): StandarResponse

    @PUT("user/unfollow/{userId}")
    suspend fun unfollowUser(
        @Path("userId") userId: String,
        @Body followingBody: FollowBody
    ): StandarResponse

    @PUT("user/follow/{userId}")
    suspend fun followUser(
        @Path("userId") userId: String,
        @Body followingBody: FollowBody
    ): StandarResponse

    @PUT("user/remove-saved-review/{userId}")
    suspend fun removeSavedReview(
        @Path("userId") userId: String,
        @Body savedReview: SavedReviewBody
    ): StandarResponse

    @PUT("user/save-review/{userId}")
    suspend fun savedReview(
        @Path("userId") userId: String,
        @Body savedReview: SavedReviewBody
    ): StandarResponse

    //--- DELETE
    @DELETE("user/{userId}")
    suspend fun deleteUser(@Path("userId") userId: String): StandarResponse


}