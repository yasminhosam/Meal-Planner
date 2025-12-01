package com.example.mealplanner.ui.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealplanner.domain.entity.Meal
import com.example.mealplanner.domain.usecase.GetMealDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getMealDetailsUseCase: GetMealDetailsUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _uiSate=MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val uiState=_uiSate.asStateFlow()
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
                val result=getMealDetailsUseCase(id)
                _uiSate.value=DetailsUiState.Success(result)
            } catch (e: Exception) {
                Log.d("DetailsVM", e.message.toString())
                _uiSate.value=DetailsUiState.Error("Error:${e.message}")
            }
        }


    }

}
sealed interface DetailsUiState{
    object  Loading:DetailsUiState
    data class Success(val meal:Meal):DetailsUiState
    data class Error(val message:String):DetailsUiState
}