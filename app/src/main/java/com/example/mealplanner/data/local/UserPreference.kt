package com.example.mealplanner.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import androidx.datastore.preferences.core.edit
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_settings")

@Singleton
class UserPreference @Inject constructor(
    @ApplicationContext private val context: Context
) {

    // Helper to create unique keys per user
    private fun userNameKey(userId: String) = stringPreferencesKey("${userId}_name")
    private fun imagePathKey(userId: String) = stringPreferencesKey("${userId}_image")
    private fun themeKey(userId: String) = booleanPreferencesKey("${userId}_dark_mode")


    fun userName(userId: String): Flow<String?> =
        context.dataStore.data
            .map { it[userNameKey(userId)] }

    fun profileImagePath(userId: String): Flow<String?> =
        context.dataStore.data
            .map { it[imagePathKey(userId)] }

    fun isDarkMode(userId: String): Flow<Boolean> =
        context.dataStore.data.map { it[themeKey(userId)] ?: false }

    // Save the preference
    suspend fun saveTheme(userId: String, isDark: Boolean) {
        context.dataStore.edit { it[themeKey(userId)] = isDark }
    }

    suspend fun saveImagePath(userId: String, fileName: String) {
        context.dataStore.edit {
            it[imagePathKey(userId)]=fileName
        }
    }

    suspend fun saveUserName(userId: String, name: String) {
        context.dataStore.edit { it[userNameKey(userId)] = name }
    }



}