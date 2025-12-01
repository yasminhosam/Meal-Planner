package com.example.mealplanner.data.remote

import com.example.mealplanner.domain.entity.CategoryResponse
import com.example.mealplanner.data.remote.dto.MealResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
//    List all meal categories
//    www.themealdb.com/api/json/v1/1/   categories.php
    @GET("categories.php")
    suspend fun getCategories(): CategoryResponse

//    List all meals by first letter
//    www.themealdb.com/api/json/v1/1/  search.php?f=a
    @GET("search.php")
    suspend fun getMealsByFirstLetter(@Query("f") letter:String):MealResponseDto

//    Lookup full meal details by id
//    www.themealdb.com/api/json/v1/1/   lookup.php?i=52772
    @GET("lookup.php")
    suspend fun getMealDetails(@Query("i") id:String):MealResponseDto

//    Filter by main ingredient
//    www.themealdb.com/api/json/v1/1/   filter.php?i=chicken_breast
@GET("filter.php")
suspend fun getMealsByIngredient(@Query("i") ingredient:String):MealResponseDto
//    Filter by Category
//    www.themealdb.com/api/json/v1/1/  filter.php?c=Seafood
@GET("filter.php")
suspend fun getMealsByCategory(@Query("c") category:String):MealResponseDto
//    Filter by Area
//    www.themealdb.com/api/json/v1/1/  filter.php?a=Canadian

    @GET("filter.php")
    suspend fun getMealsByArea(@Query("a") area:String):MealResponseDto
}