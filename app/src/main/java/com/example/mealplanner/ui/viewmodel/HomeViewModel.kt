package com.example.mealplanner.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealplanner.domain.entity.CategoryResponse
import com.example.mealplanner.domain.entity.MealResponse
import com.example.mealplanner.domain.usecase.GetCategories
import com.example.mealplanner.domain.usecase.GetMeals
import com.example.mealplanner.domain.usecase.GetMealsByFirstLetter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMeals: GetMeals,
    private val getCategories: GetCategories,
    private val getMealsByFirstLetter: GetMealsByFirstLetter,

) : ViewModel() {
    private val _searchQuery=MutableStateFlow("")
    val searchQuery=_searchQuery.asStateFlow()

    private val _isSearchActive=MutableStateFlow(false)
    val isSearchActive=_isSearchActive.asStateFlow()
    fun  onQueryChange(newQuery:String){
        _searchQuery.value=newQuery
    }
    fun onActiveChange(active:Boolean){
        _isSearchActive.value=active
        if(!active){
            _searchQuery.value=""
        }
    }

    private val _filteredMeals=MutableStateFlow<MealResponse?>(null)
    val filteredMeals = _filteredMeals.asStateFlow()
   fun searchByFirstLetter(letter:String){
        viewModelScope.launch {
            try {
                _filteredMeals.value = getMealsByFirstLetter(letter.first().toString())

            } catch (e: Exception) {
                Log.d("homeViewModel", e.message.toString())
            }
        }
    }

    private val _categories = MutableStateFlow<CategoryResponse?>(null)
    val categories= _categories.asStateFlow()

    fun loadCategories() {
        viewModelScope.launch {
            try {
                _categories.value = getCategories()
            } catch (e: Exception) {
                Log.d("homeViewModel", e.message.toString())
            }
        }
    }



    private val _meals=MutableStateFlow<MealResponse?>(null)
    val meals = _meals.asStateFlow()
    fun loadMeals(){
        viewModelScope.launch {
            try {
                _meals.value = getMeals()
            } catch (e: Exception) {
                Log.d("homeViewModel", e.message.toString())
            }
        }
    }
    init {
        loadCategories()
        loadMeals()

    }
//
//    private val _meal=MutableStateFlow<Meal?>(null)
//    val meal=_meal.asStateFlow()
//    fun findMealById(id:String){
//        viewModelScope.launch {
//            try {
//                _meal.value=getMealDetails(id)
//            }catch (e: Exception) {
//                Log.d("homeViewModel", e.message.toString())
//            }
//        }


}