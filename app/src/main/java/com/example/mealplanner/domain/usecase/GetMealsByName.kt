package com.example.mealplanner.domain.usecase

import com.example.mealplanner.domain.repo.MealsRepo
import javax.inject.Inject

class GetMealsByName@Inject constructor(private val mealsRepo: MealsRepo) {
    suspend operator fun invoke(name:String)=mealsRepo.getMealsByName(name)
}