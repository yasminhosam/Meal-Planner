package com.example.mealplanner.data.repo

import com.example.mealplanner.data.local.dao.FavoriteMealDao
import com.example.mealplanner.data.local.dao.PlannedMealDao
import com.example.mealplanner.data.mapper.toDomain
import com.example.mealplanner.data.mapper.toFavoriteEntity
import com.example.mealplanner.data.mapper.toPlannedEntity
import com.example.mealplanner.data.remote.ApiService
import com.example.mealplanner.domain.entity.CategoryResponse
import com.example.mealplanner.domain.entity.Meal
import com.example.mealplanner.domain.entity.MealResponse
import com.example.mealplanner.domain.repo.MealsRepo
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class MealsRepoImpl @Inject constructor(
    private val auth:FirebaseAuth,
    private val api:ApiService,
    private val favoriteDao: FavoriteMealDao,
    private val plannedMealsDao: PlannedMealDao
):MealsRepo {


    override suspend fun getMealsByName(name:String): MealResponse {
        return api.getMealsByName(name).toDomain()
    }

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
            throw NoSuchElementException("Meal not found")

        }
    }

    override suspend fun getMealByFirstLetter(letter: String): MealResponse {
        return api.getMealsByFirstLetter(letter).toDomain()
    }

    private fun requireUserId(): String {
        return auth.currentUser?.uid
            ?: throw IllegalStateException("User not logged in")
    }

    override  fun getAllFavoriteMeals(): Flow<List<Meal>> {
        return favoriteDao.getAllFavoriteMeals(requireUserId()).map { entities->
            entities.map { it.toDomain() }
        }
    }

    override  fun getPlannedMealsByDate(date: LocalDate): Flow<List<Meal>> {
        return plannedMealsDao.getMealsByDate(date, requireUserId()).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getAllPlannedMeals(): Flow<List<Meal>> {
        return plannedMealsDao.getAllPlannedMeals(requireUserId()).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun toggleFavorite(meal: Meal) {

        if(favoriteDao.isFavorite(meal.idMeal,requireUserId())){
            favoriteDao.deleteFavoriteMeal(meal.toFavoriteEntity(requireUserId()))
            return
        }else {
            favoriteDao.insertFavoriteMeal(
                meal.toFavoriteEntity(
                    userId = requireUserId()
                )
            )
        }
    }

    override suspend fun insertPlannedMeal(meal: Meal,date: LocalDate) {
        plannedMealsDao.insertPlannedMeal(meal.toPlannedEntity(date,requireUserId()))
    }

    override suspend fun deletePlannedMeal(mealId: String, date: LocalDate) {
        plannedMealsDao.deletePlannedMeal(mealId,requireUserId(),date)
    }

}