package com.example.mealplanner.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealplanner.domain.entity.Meal
import com.example.mealplanner.domain.usecase.DeletePlannedMeal
import com.example.mealplanner.domain.usecase.GetPlannedMeals
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class PlannedMealsViewModel @Inject constructor(
    private val getPlannedMeals: GetPlannedMeals,
    private val deletePlannedMeal: DeletePlannedMeal

) : ViewModel() {

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate = _selectedDate.asStateFlow()

    val meals: StateFlow<List<Meal>> = _selectedDate
        .flatMapLatest { date -> getPlannedMeals(date) }
        .stateIn(
           scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onDateSelected(date: LocalDate) {
        _selectedDate.value = date
        Log.d("PlannedMealsViewModel", "Date selected: $date")
    }

    fun deleteMeal(mealId: String, date: LocalDate){
        viewModelScope.launch {
            try {
                deletePlannedMeal(mealId,date)
            }catch (e:Exception){
                Log.d("PlannedMealsViewModel","${e.message}")
            }

        }
    }

}
