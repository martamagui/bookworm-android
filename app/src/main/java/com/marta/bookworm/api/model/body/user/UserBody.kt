package com.marta.bookworm.api.model.body.user

data class UserBody(
    val userName: String = "",
    val address: String = "",
    val birthDate: String = "",
    val city: String = "",
    val country: String = "",
    val email: String = "",
    val fullName: String = "",
    val password: String = "",
    val subscribedToNewsLetter: Boolean = true,
    val banner: String = "",
    val avatar: String = "",
    val description: String = ""
)