package com.example.mealplanner.domain.usecase.favorites

import app.cash.turbine.test
import com.example.mealplanner.domain.entity.Meal
import com.example.mealplanner.domain.repo.MealsRepo
import com.example.mealplanner.domain.usecase.GetFavoriteMeals
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetFavoriteMealsTest {
    private lateinit var mealsRepo: MealsRepo
    private lateinit var useCase: GetFavoriteMeals


    @Before
    fun setup(){
        mealsRepo= mockk()
        useCase= GetFavoriteMeals(mealsRepo)
    }

    @Test
    fun `invoke returns the favorits meals flow from repository`()= runTest {
        val fakeMeals=listOf(mockk<Meal>(relaxed = true),mockk<Meal>(relaxed = true))
        every { mealsRepo.getAllFavoriteMeals() } returns flowOf(fakeMeals)

        useCase().test{
            val emitted = awaitItem()
            assertEquals(fakeMeals, emitted)
            assert(emitted.size == 2)
            awaitComplete()
        }
    }
}