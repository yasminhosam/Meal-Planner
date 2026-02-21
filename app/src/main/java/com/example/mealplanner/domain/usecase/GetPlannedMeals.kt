package com.example.mealplanner.domain.usecase

import com.example.mealplanner.domain.repo.MealsRepo
import java.time.LocalDate
import javax.inject.Inject

class GetPlannedMeals@Inject constructor(
    private val mealsRepo: MealsRepo
) {
    operator fun invoke(date: LocalDate)=mealsRepo.getPlannedMealsByDate(date)

}