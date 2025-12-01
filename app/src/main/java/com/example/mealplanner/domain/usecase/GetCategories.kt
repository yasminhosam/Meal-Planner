package com.example.mealplanner.domain.usecase;

import com.example.mealplanner.domain.repo.MealsRepo
import javax.inject.Inject

public class GetCategories @Inject constructor(
    private val mealsRepo: MealsRepo
) {
    suspend  operator fun invoke ()=mealsRepo.getCategories()
}
