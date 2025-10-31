package com.example.mealplanner.domain.repo

import com.example.mealplanner.domain.entity.MealResponse

interface MealsRepo {
    fun getMealsFromRemote():MealResponse
}