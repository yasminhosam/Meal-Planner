package com.example.mealplanner.data.repo

import com.example.mealplanner.data.local.dao.FavoriteMealDao
import com.example.mealplanner.data.local.dao.PlannedMealDao
import com.example.mealplanner.data.remote.ApiService
import com.example.mealplanner.domain.entity.Meal
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith

class MealsRepoImplTest {
    private lateinit var auth: FirebaseAuth
    private lateinit var api: ApiService
    private lateinit var favoriteDao: FavoriteMealDao
    private lateinit var plannedMealsDao: PlannedMealDao
    private lateinit var repo: MealsRepoImpl

    private val userId = "user123"

    @Before
    fun setup(){
        auth=mockk(relaxed =true)
        api=mockk(relaxed = true)
        favoriteDao = mockk(relaxed = true)
        plannedMealsDao = mockk(relaxed = true)
        repo = MealsRepoImpl(auth, api, favoriteDao, plannedMealsDao)

        // Simulate a logged-in user for every test by default
        val fakeUser = mockk<FirebaseUser>()
        every { fakeUser.uid } returns userId
        every { auth.currentUser } returns fakeUser
    }

    @Test
    fun `toggleFavorite deletes meal when it is already favorite`() = runTest {
        val fakeMeal=mockk<Meal>(relaxed = true)
        every { fakeMeal.idMeal } returns "456"
        coEvery { favoriteDao.isFavorite("456",userId) } returns true

        repo.toggleFavorite(fakeMeal)

        coVerify(exactly = 1) { favoriteDao.deleteFavoriteMeal(any()) }
        coVerify(exactly = 0) { favoriteDao.insertFavoriteMeal(any()) }
    }

    @Test
    fun `toggleFavorite insert meal when it is not favorite`() = runTest {
        val fakeMeal=mockk<Meal>(relaxed = true)
        every { fakeMeal.idMeal } returns "456"
        coEvery { favoriteDao.isFavorite("456" +
                "",userId) } returns true

        repo.toggleFavorite(fakeMeal)

        coVerify(exactly = 1) { favoriteDao.insertFavoriteMeal(any()) }
        coVerify(exactly = 0) { favoriteDao.deleteFavoriteMeal(any()) }
    }

    @Test
    fun `toggleFavorite throws when user is not logged in` ()=runTest {
        every { auth.currentUser } returns null

        val fakeMeal=mockk<Meal>(relaxed = true)
        assertFailsWith<IllegalStateException>{
            repo.toggleFavorite(fakeMeal)
        }
    }
}