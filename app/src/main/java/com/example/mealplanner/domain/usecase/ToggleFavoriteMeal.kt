package com.example.mealplanner.domain.usecase

import com.example.mealplanner.domain.entity.Meal
import com.example.mealplanner.domain.repo.MealsRepo
import javax.inject.Inject

class ToggleFavoriteMeal @Inject constructor(
   private val mealsRepo: MealsRepo
) {
    suspend operator fun invoke(meal: Meal)=mealsRepo.toggleFavorite(meal)

}