package com.example.mealplanner.di

import com.example.mealplanner.data.local.dao.FavoriteMealDao
import com.example.mealplanner.data.local.dao.PlannedMealDao
import com.example.mealplanner.data.remote.ApiService
import com.example.mealplanner.data.repo.AuthRepositoryImpl
import com.example.mealplanner.data.repo.MealsRepoImpl
import com.example.mealplanner.domain.repo.AuthRepository
import com.example.mealplanner.domain.repo.MealsRepo
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Singleton
    @Provides
    fun provideRepo(
        apiService: ApiService,
        firebaseAuth: FirebaseAuth,
        favoriteMealDao: FavoriteMealDao,
        plannedMealDao: PlannedMealDao
    ): MealsRepo {
        return MealsRepoImpl(firebaseAuth, apiService,favoriteMealDao,plannedMealDao)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }

}