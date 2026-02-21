package com.example.mealplanner.domain.usecase

import com.example.mealplanner.domain.repo.MealsRepo
import javax.inject.Inject

class GetFavoriteMeals @Inject constructor(
    private val mealsRepo: MealsRepo
) {
     operator fun invoke()= mealsRepo.getAllFavoriteMeals()
}