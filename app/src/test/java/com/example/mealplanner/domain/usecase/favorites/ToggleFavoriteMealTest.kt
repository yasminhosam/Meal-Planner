package com.example.mealplanner.domain.usecase.favorites

import com.example.mealplanner.domain.entity.Meal
import com.example.mealplanner.domain.repo.MealsRepo
import com.example.mealplanner.domain.usecase.ToggleFavoriteMeal
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ToggleFavoriteMealTest {
    private lateinit var mealsRepo: MealsRepo
    private lateinit var useCase: ToggleFavoriteMeal

    @Before
    fun setup() {
        mealsRepo = mockk()
        useCase = ToggleFavoriteMeal(mealsRepo)
    }

    @Test
    fun `invoke calls repository toggleFavorite with the given meal`()= runTest {
        val meal=mockk<Meal>(relaxed = true)
        coEvery { mealsRepo.toggleFavorite(meal) } returns Unit

        useCase(meal)

        coVerify(exactly = 1) { mealsRepo.toggleFavorite(meal) }
    }

}