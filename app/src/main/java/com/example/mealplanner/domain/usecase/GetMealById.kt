package com.example.mealplanner.domain.usecase

import com.example.mealplanner.domain.repo.MealsRepo
import javax.inject.Inject

class GetMealById @Inject constructor(private val mealsRepo: MealsRepo) {
    suspend operator fun invoke(id:String)=mealsRepo.getMealById(id)
}