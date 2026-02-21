package com.example.mealplanner.domain.usecase

import com.example.mealplanner.domain.repo.MealsRepo
import javax.inject.Inject

class GetAllPlannedMeals @Inject constructor(
    private val mealsRepo: MealsRepo
) {
    operator fun invoke()= mealsRepo.getAllPlannedMeals()
}