package com.example.mealplanner.data.local.entity

import androidx.room.Entity
import java.time.LocalDate

@Entity(
    tableName = "planned_meals",
    primaryKeys = ["userId","idMeal", "plannedDate"]
)
data class PlannedMeal(
    val userId: String,
    val idMeal: String,
    val plannedDate: LocalDate,
    val strMeal: String,
    val strMealThumb: String,
)
