package com.example.mealplanner.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorite_meals",
    primaryKeys = ["userId","idMeal"]
)
data class FavoriteMeal(
    val userId: String,
    val idMeal: String,

    val strMeal: String,
    val strMealThumb: String,
)
