package com.example.mealplanner.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealplanner.domain.entity.MealResponse
import com.example.mealplanner.domain.usecase.GetMealsByArea
import com.example.mealplanner.domain.usecase.GetMealsByCategory
import com.example.mealplanner.domain.usecase.GetMealsByIngredient
import com.example.mealplanner.domain.usecase.GetMealsByName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getMealsByName: GetMealsByName,
    private val getMealsByArea: GetMealsByArea,
    private val getMealsByIngredient: GetMealsByIngredient,
    private val getMealsByCategory: GetMealsByCategory
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _searchType = MutableStateFlow(SearchType.NAME)
    val searchType = _searchType.asStateFlow()

    private val _filteredMeals = MutableStateFlow<MealResponse?>(null)
    val filteredMeals = _filteredMeals.asStateFlow()

    fun onQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun onActiveChange(active: Boolean) {
        _isSearching.value = active
        if (!active) {
            _searchQuery.value = ""
            _filteredMeals.value = null
        }
    }

    fun onSearchTypeChange(type: SearchType) {
        _searchType.value = type
        _searchQuery.value = ""
        _filteredMeals.value = null
    }
    fun search(){
        viewModelScope.launch {
            try {
                _filteredMeals.value=when(_searchType.value){
                    SearchType.NAME ->getMealsByName(_searchQuery.value)
                    SearchType.AREA -> getMealsByArea(_searchQuery.value)
                    SearchType.CATEGORY -> getMealsByCategory(_searchQuery.value)
                    SearchType.INGREDIENT -> getMealsByIngredient(_searchQuery.value)
                }
                Log.d("searchViewModel","size of meals is ${_filteredMeals.value?.meals.orEmpty().size}")
            }catch (e:Exception){
                Log.d("searchViewModel",e.message.toString())
            }
        }
    }


}

enum class SearchType {
    NAME,
    AREA,
    CATEGORY,
    INGREDIENT
}
