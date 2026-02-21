package com.example.mealplanner.domain

sealed interface AuthMethod {
    data class EmailPassword(
        val email:String,
        val password:String
    ):AuthMethod
    data class Google(val idToken: String):AuthMethod
    // data class Facebook(val accessToken: String):AuthMethod
}