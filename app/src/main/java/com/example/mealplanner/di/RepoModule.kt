package com.example.mealplanner.di

import com.example.mealplanner.data.remote.ApiService
import com.example.mealplanner.data.repo.MealsRepoImpl
import com.example.mealplanner.domain.repo.MealsRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Provides
    fun provideRepo(apiService: ApiService):MealsRepo{
        return MealsRepoImpl(apiService)
    }
}