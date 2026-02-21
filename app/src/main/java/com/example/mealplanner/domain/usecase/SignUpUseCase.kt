package com.example.mealplanner.domain.usecase

import com.example.mealplanner.domain.repo.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        username: String
    )=repository.signUp(email,password,username)
}