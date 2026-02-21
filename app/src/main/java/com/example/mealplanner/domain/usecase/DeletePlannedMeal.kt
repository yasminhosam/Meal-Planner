package com.example.mealplanner.domain.usecase

import com.example.mealplanner.domain.repo.MealsRepo
import java.time.LocalDate
import javax.inject.Inject

class DeletePlannedMeal @Inject constructor(
    private val mealsRepo: MealsRepo
) {
    suspend operator fun invoke(mealId: String, date: LocalDate) =
        mealsRepo.deletePlannedMeal(mealId, date)
}