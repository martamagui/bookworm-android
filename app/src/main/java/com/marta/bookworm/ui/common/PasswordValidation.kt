package com.marta.bookworm.ui.common

fun validatePassword(pass: String, repPass: String): Boolean {
    if (pass.length > 4 && pass == repPass) {
        val regexNum = Regex("[0123456789]")
        val regexAbc = Regex("[a-z]")
        val regexCaps = Regex("[A-B]")
        val regexWSpaces = Regex("[ \\s\\n]")
        if (!regexNum.containsMatchIn(pass) || !regexAbc.containsMatchIn(pass) ||
            regexWSpaces.containsMatchIn(pass) || !regexCaps.containsMatchIn(pass)
        ) {
            return false
        }
    }else{
        return false
    }
    return true
}