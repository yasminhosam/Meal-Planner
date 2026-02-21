package com.example.mealplanner.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.mealplanner.data.local.entity.FavoriteMeal
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMealDao {
    @Insert
    suspend fun insertFavoriteMeal(favoriteMeal: FavoriteMeal)

    @Delete
    suspend fun deleteFavoriteMeal(favoriteMeal: FavoriteMeal)



    @Query("SELECT * FROM favorite_meals WHERE userId = :userId")
     fun getAllFavoriteMeals(userId: String): Flow<List<FavoriteMeal>>


    @Query("SELECT EXISTS(SELECT 1 FROM favorite_meals WHERE idMeal = :id AND userId = :userId)")
    suspend fun isFavorite(id: String, userId: String): Boolean

}