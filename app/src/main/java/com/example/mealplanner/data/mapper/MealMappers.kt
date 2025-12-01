package com.example.mealplanner.data.mapper

import com.example.mealplanner.data.remote.dto.MealDto
import com.example.mealplanner.data.remote.dto.MealResponseDto
import com.example.mealplanner.domain.entity.Meal
import com.example.mealplanner.domain.entity.MealResponse


fun MealResponseDto.toDomain():MealResponse{
    return MealResponse(
        meals = this.meals.map { it.toDomain() }
    )

}
fun MealDto.toDomain():Meal {
    val ingredientsList = mutableListOf<Pair<String, String>>()
    val fields = listOf(
        strIngredient1 to strMeasure1,
        strIngredient2 to strMeasure2,
        strIngredient3 to strMeasure3,
        strIngredient4 to strMeasure4,
        strIngredient5 to strMeasure5,
        strIngredient6 to strMeasure6,
        strIngredient7 to strMeasure7,
        strIngredient8 to strMeasure8,
        strIngredient9 to strMeasure9,
        strIngredient10 to strMeasure10,
        strIngredient11 to strMeasure11,
        strIngredient12 to strMeasure12,
        strIngredient13 to strMeasure13,
        strIngredient14 to strMeasure14,
        strIngredient15 to strMeasure15,
        strIngredient16 to strMeasure16,
        strIngredient17 to strMeasure17,
        strIngredient18 to strMeasure18,
        strIngredient19 to strMeasure19,
        strIngredient20 to strMeasure20,

        )
    fields.forEach { (ingredient,measure) ->
        if ( ingredient is String && !ingredient.isNullOrBlank()) {
            ingredientsList.add((ingredient to (measure ?: "")) as Pair<String, String>)
        }
    }


    return Meal(
        idMeal = idMeal,
        strMeal = strMeal,
        strMealThumb = strMealThumb,
        strYoutube = strYoutube,
        strInstructions = strInstructions,
        strCategory = strCategory,
        ingredients = ingredientsList
    )

}
