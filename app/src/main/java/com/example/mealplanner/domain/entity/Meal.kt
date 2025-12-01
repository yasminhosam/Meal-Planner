package com.example.mealplanner.domain.entity

data class Meal(
    val idMeal: String="",
    val strCategory: String="",
    val strMealThumb: String="",
    val ingredients: List<Pair<String, String>>,// name + measure
    val strMeal: String="",
    val strInstructions: String="",
    val strYoutube: String=""
)