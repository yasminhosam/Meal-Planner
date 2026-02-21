package com.example.mealplanner.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mealplanner.data.local.dao.FavoriteMealDao
import com.example.mealplanner.data.local.dao.PlannedMealDao
import com.example.mealplanner.data.local.entity.FavoriteMeal
import com.example.mealplanner.data.local.entity.PlannedMeal

@Database(entities = [FavoriteMeal::class, PlannedMeal::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase :RoomDatabase(){
    abstract fun favoriteDao():FavoriteMealDao
    abstract fun plannedDao(): PlannedMealDao

}