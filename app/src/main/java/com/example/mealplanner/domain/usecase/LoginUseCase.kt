package com.example.mealplanner.domain.usecase

import com.example.mealplanner.domain.AuthMethod
import com.example.mealplanner.domain.repo.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke (method:AuthMethod)=repository.login(method)
}