package com.example.mealplanner.domain.usecase

import com.example.mealplanner.domain.repo.MealsRepo
import javax.inject.Inject

class GetMealDetailsUseCase @Inject constructor(private val mealsRepo: MealsRepo) {
    suspend operator fun invoke(id:String)=mealsRepo.getMealById(id)
}