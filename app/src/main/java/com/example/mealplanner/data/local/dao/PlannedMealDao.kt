package com.example.mealplanner.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mealplanner.data.local.entity.PlannedMeal
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import java.time.LocalDate

@Dao
interface PlannedMealDao {
    //if the meal already exists,do nothing
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPlannedMeal(plannedMeal: PlannedMeal)


    @Query("SELECT * FROM planned_meals WHERE plannedDate = :date AND userId = :userId")
     fun getMealsByDate(date: LocalDate, userId: String): Flow<List<PlannedMeal>>

    @Query("DELETE FROM planned_meals WHERE idMeal = :idMeal AND userId = :userId AND plannedDate = :date")
    suspend fun deletePlannedMeal(idMeal: String, userId: String, date: LocalDate)

    @Query("SELECT * FROM planned_meals WHERE userId = :userId")
    fun getAllPlannedMeals(userId: String): Flow<List<PlannedMeal>>


}