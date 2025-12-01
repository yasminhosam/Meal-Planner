package com.example.mealplanner.domain.usecase

import com.example.mealplanner.domain.repo.MealsRepo
import javax.inject.Inject

class GetMealsByArea @Inject constructor(
    private val mealsRepo: MealsRepo
) {
    suspend operator fun invoke(area:String)=mealsRepo.getMealsByArea(area)
}