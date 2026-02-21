package com.example.mealplanner.ui.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealplanner.domain.entity.MealResponse
import com.example.mealplanner.domain.usecase.GetMealsByCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getMealsByCategory: GetMealsByCategory,
    savedStateHandle: SavedStateHandle
) :ViewModel(){
    private val _meals= MutableStateFlow<MealResponse?>(null)
    val meals=_meals.asStateFlow()

    init {
        val category:String?=savedStateHandle["category"]
        if(category!=null){
            loadMeals(category)
        }

    }
    fun loadMeals(category:String){
        viewModelScope.launch {
            try {
                _meals.value=getMealsByCategory(category)
            }catch (e:Exception){
                Log.d("categoryViewModel", e.message.toString())
            }
        }
    }

}