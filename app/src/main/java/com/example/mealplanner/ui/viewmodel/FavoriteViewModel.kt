package com.example.mealplanner.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealplanner.domain.entity.Meal
import com.example.mealplanner.domain.usecase.GetFavoriteMeals
import com.example.mealplanner.domain.usecase.GetMealById
import com.example.mealplanner.domain.usecase.ToggleFavoriteMeal
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteMeals: GetFavoriteMeals,
    private val toggleFavoriteMeal: ToggleFavoriteMeal,
    private val getMealById: GetMealById

)
:ViewModel() {


     val favoriteMeals:StateFlow<List<Meal>> = getFavoriteMeals()
         .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue= emptyList()
        )


     val favoriteMealIds:StateFlow<Set<String>> = favoriteMeals
         .map { meals -> meals.map { it.idMeal }.toSet() }
         .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())


    fun onFavoriteClick(mealId: String) {
        viewModelScope.launch {
            try {
                val meal=getMealById(mealId)
                toggleFavoriteMeal(meal)
            }catch (e:Exception){
                Log.d("favoriteViewModel",e.message.toString())
            }

            }
        }
    }


