package com.example.mealplanner.domain.usecase

import com.example.mealplanner.domain.repo.MealsRepo
import javax.inject.Inject

class GetMealsByCategories @Inject constructor(private val mealsRepo: MealsRepo) {
    suspend operator fun invoke(category:String )=mealsRepo.getMealsByCategory(category)
}