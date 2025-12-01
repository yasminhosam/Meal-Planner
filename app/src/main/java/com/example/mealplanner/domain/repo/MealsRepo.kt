package com.example.mealplanner.domain.repo

import com.example.mealplanner.domain.entity.CategoryResponse
import com.example.mealplanner.domain.entity.Meal
import com.example.mealplanner.domain.entity.MealResponse

interface MealsRepo {

  suspend  fun getCategories(): CategoryResponse
  suspend fun getRandomMeals():MealResponse
  suspend fun getMealsByArea(area:String):MealResponse
  suspend fun getMealsByCategory(category: String):MealResponse
  suspend fun getMealsByIngredient(ingredient:String):MealResponse
  suspend fun getMealById(id:String):Meal
  suspend fun getMealByFirstLetter(letter:String):MealResponse

}