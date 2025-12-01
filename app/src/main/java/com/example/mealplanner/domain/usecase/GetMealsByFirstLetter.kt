package com.example.mealplanner.domain.usecase

import com.example.mealplanner.domain.repo.MealsRepo
import javax.inject.Inject

class GetMealsByFirstLetter @Inject constructor(private val mealsRepo: MealsRepo) {
    suspend operator fun invoke(letter:String)=mealsRepo.getMealByFirstLetter(letter)
}