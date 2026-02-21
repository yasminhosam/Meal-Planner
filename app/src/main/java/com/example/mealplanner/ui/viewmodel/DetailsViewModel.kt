package com.example.mealplanner.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealplanner.domain.entity.Meal
import com.example.mealplanner.domain.usecase.AddToPlan
import com.example.mealplanner.domain.usecase.GetAllPlannedMeals
import com.example.mealplanner.domain.usecase.GetMealById
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getMealById: GetMealById,
    private val addToPlan: AddToPlan,
    private val getAllPlannedMeals: GetAllPlannedMeals,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _uiSate=MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val uiState=_uiSate.asStateFlow()

     val plannedMeals:StateFlow<List<Meal>> = getAllPlannedMeals()
         .stateIn(
             scope = viewModelScope,
             started = SharingStarted.WhileSubscribed(5000),
             initialValue= emptyList()
         )

    val isPlanned: StateFlow<Boolean> =
        combine(
            uiState,
            plannedMeals
        ) { uiState, plannedMeals ->
            if (uiState is DetailsUiState.Success) {
                plannedMeals.any { it.idMeal == uiState.meal.idMeal }
            } else {
                false
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )



    init {
        val mealId:String?=savedStateHandle["id"]
        if(mealId !=null){
            Log.d("DetailsVM", "Fetching details for ID: $mealId")
            getMealDetails(mealId)

        }else{
            _uiSate.value=DetailsUiState.Error("No mealId provided")
        }

    }


    private fun getMealDetails(id: String) {
        viewModelScope.launch {
            try {
                val result=getMealById(id)
                _uiSate.value=DetailsUiState.Success(result)
            } catch (e: Exception) {
                Log.d("DetailsVM", e.message.toString())
                _uiSate.value=DetailsUiState.Error("Error:${e.message}")
            }
        }


    }
    fun onPlanClick(meal:Meal,date: LocalDate){
        viewModelScope.launch {
            try {
                addToPlan(meal,date)
                Log.d("detailsViewModel","${meal.strMeal} added to plan")
            }catch (e:Exception){
                Log.d("detailsViewModel","${e.message}")

            }
        }
    }
    fun getIngredientImageUrl(name: String): String {

        return "https://www.themealdb.com/images/ingredients/${name}-Small.png"
    }


}
sealed interface DetailsUiState{
    object  Loading:DetailsUiState
    data class Success(val meal:Meal):DetailsUiState
    data class Error(val message:String):DetailsUiState
}