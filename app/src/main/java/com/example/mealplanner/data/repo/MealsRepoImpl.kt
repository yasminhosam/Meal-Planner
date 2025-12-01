package com.example.mealplanner.data.repo

import com.example.mealplanner.data.mapper.toDomain
import com.example.mealplanner.data.remote.ApiService
import com.example.mealplanner.domain.entity.CategoryResponse
import com.example.mealplanner.domain.entity.Meal
import com.example.mealplanner.domain.entity.MealResponse
import com.example.mealplanner.domain.repo.MealsRepo
import javax.inject.Inject

class MealsRepoImpl @Inject constructor(
    private val api:ApiService
):MealsRepo {


    override suspend fun getCategories(): CategoryResponse {
        return api.getCategories()
    }

    override suspend fun getRandomMeals(): MealResponse {
        val randomLetter=('a'..'v').random()
        val responseDto=api.getMealsByFirstLetter(randomLetter.toString())
        return responseDto.toDomain()
    }

    override suspend fun getMealsByArea(area: String): MealResponse {

        return api.getMealsByArea(area).toDomain()
    }

    override suspend fun getMealsByCategory(category: String): MealResponse {
        return api.getMealsByCategory(category).toDomain()
    }

    override suspend fun getMealsByIngredient(ingredient: String): MealResponse {
       return api.getMealsByIngredient(ingredient).toDomain()
    }

    override suspend fun getMealById(id: String): Meal {
        val response=api.getMealDetails(id)
        if(!response.meals.isNullOrEmpty()){
            return response.meals.first().toDomain()
        }else{
            throw Exception("Meal not found")
        }
    }

    override suspend fun getMealByFirstLetter(letter: String): MealResponse {
        return api.getMealsByFirstLetter(letter).toDomain()
    }


}