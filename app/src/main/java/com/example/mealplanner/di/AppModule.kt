package com.example.mealplanner.di

import android.content.Context
import androidx.room.Room
import com.example.mealplanner.data.local.AppDatabase
import com.example.mealplanner.data.remote.ApiService
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideOKHttp():OkHttpClient{
        return OkHttpClient.Builder()
            .connectTimeout(20,TimeUnit.SECONDS)
            .build()
    }
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient):Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit):ApiService{
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase{
        return Room.databaseBuilder(
           context = context,
            klass = AppDatabase::class.java,
            name = "app_database"

        ).build(
        )
    }

    @Provides
    @Singleton
    fun provideFavoriteDao(appDatabase: AppDatabase) = appDatabase.favoriteDao()

    @Provides
    @Singleton
    fun providePlannedDao(appDatabase: AppDatabase) = appDatabase.plannedDao()

}