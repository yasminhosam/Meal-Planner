package com.example.mealplanner.data.remote

import com.example.mealplanner.domain.entity.CategoryResponse
import com.example.mealplanner.data.remote.dto.MealResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

//    Search meal by name

    @GET("search.php")
    suspend fun getMealsByName(@Query("s") name: String): MealResponseDto

    //    List all meal categories

    @GET("categories.php")
    suspend fun getCategories(): CategoryResponse

    //    List all meals by first letter

    @GET("search.php")
    suspend fun getMealsByFirstLetter(@Query("f") letter: String): MealResponseDto

    //    Lookup full meal details by id

    @GET("lookup.php")
    suspend fun getMealDetails(@Query("i") id: String): MealResponseDto


    //    Filter by main ingredient

    @GET("filter.php")
    suspend fun getMealsByIngredient(@Query("i") ingredient: String): MealResponseDto


    //    Filter by Category

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): MealResponseDto


//    Filter by Area

    @GET("filter.php")
    suspend fun getMealsByArea(@Query("a") area: String): MealResponseDto
}