package com.example.mealplanner.domain.usecase

import com.example.mealplanner.domain.entity.Meal
import com.example.mealplanner.domain.repo.MealsRepo
import java.time.LocalDate
import javax.inject.Inject

class AddToPlan @Inject constructor(
    private val mealsRepo: MealsRepo
) {
    suspend operator fun invoke(meal: Meal, date: LocalDate) =
        mealsRepo.insertPlannedMeal(meal, date)
}