package com.example.mealplanner.domain.usecase

import com.example.mealplanner.domain.repo.MealsRepo
import javax.inject.Inject

class GetMealsByIngredient @Inject constructor(private val mealsRepo: MealsRepo) {
    suspend operator fun invoke(ingredient:String)=mealsRepo.getMealsByIngredient(ingredient)
}