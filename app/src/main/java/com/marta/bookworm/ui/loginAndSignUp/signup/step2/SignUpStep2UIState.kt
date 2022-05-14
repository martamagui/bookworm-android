package com.marta.bookworm.ui.loginAndSignUp.signup.step2

data class SignUpStep2UIState(
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMssg: String = "",
    val dob: String = ""
)
