package com.example.mealplanner.domain.repo

import com.example.mealplanner.domain.entity.CategoryResponse
import com.example.mealplanner.domain.entity.Meal
import com.example.mealplanner.domain.entity.MealResponse
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface MealsRepo {

  suspend fun getMealsByName(name:String):MealResponse
  suspend fun getCategories(): CategoryResponse
  suspend fun getRandomMeals():MealResponse
  suspend fun getMealsByArea(area:String):MealResponse
  suspend fun getMealsByCategory(category: String):MealResponse
  suspend fun getMealsByIngredient(ingredient:String):MealResponse

  suspend fun getMealById(id:String):Meal
  suspend fun getMealByFirstLetter(letter:String):MealResponse

   fun getAllFavoriteMeals():Flow<List<Meal>>
   fun getPlannedMealsByDate(date:LocalDate):Flow<List<Meal>>
   fun getAllPlannedMeals():Flow<List<Meal>>
  suspend fun toggleFavorite(meal: Meal)

  suspend fun insertPlannedMeal(meal: Meal,date: LocalDate)
  suspend fun deletePlannedMeal(mealId: String,date: LocalDate)
}