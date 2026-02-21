package com.example.mealplanner.domain.repo

import com.example.mealplanner.domain.AuthMethod

interface AuthRepository {
    suspend fun login (method: AuthMethod)
    suspend fun signUp(
        email:String,
        password:String,
        username:String,
    )


    suspend fun logout()

    fun isUserLoggedIn():Boolean
   suspend fun isEmailVerified():Boolean
}