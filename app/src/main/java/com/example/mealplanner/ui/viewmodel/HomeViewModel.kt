package com.example.mealplanner.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealplanner.domain.entity.CategoryResponse
import com.example.mealplanner.domain.entity.MealResponse
import com.example.mealplanner.domain.usecase.GetCategories
import com.example.mealplanner.domain.usecase.GetMeals
import com.example.mealplanner.domain.usecase.GetMealsByName
import com.example.mealplanner.data.local.UserPreference
import com.example.mealplanner.data.local.UserRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMeals: GetMeals,
    private val getCategories: GetCategories,
    private val getMealsByName: GetMealsByName,
    private val userRepository: UserRepository,
    private val preference: UserPreference,
    private val auth: FirebaseAuth,


    ) : ViewModel() {
    val userId get() = auth.currentUser?.uid
    private val _username=MutableStateFlow("")
    val username:StateFlow<String> = _username

    val profileImage=userRepository.userProfileImage
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    private val _searchQuery=MutableStateFlow("")
    val searchQuery=_searchQuery.asStateFlow()

    private val _isSearchActive=MutableStateFlow(false)
    val isSearchActive=_isSearchActive.asStateFlow()
    private val _categories = MutableStateFlow<CategoryResponse?>(null)

    val categories= _categories.asStateFlow()
    private val _meals=MutableStateFlow<MealResponse?>(null)
    val meals = _meals.asStateFlow()

    private val _filteredMeals=MutableStateFlow<MealResponse?>(null)
    val filteredMeals = _filteredMeals.asStateFlow()

    init {
        syncUserNameFromFirebase()
        observeUserName()
        loadCategories()
        loadMeals()

    }
    fun  onQueryChange(newQuery:String){
        _searchQuery.value=newQuery
    }
    fun onActiveChange(active:Boolean){
        _isSearchActive.value=active
        if(!active){
            _searchQuery.value=""
        }
    }

   fun searchByFName(name:String){
        viewModelScope.launch {
            try {
                _filteredMeals.value = getMealsByName(name)

            } catch (e: Exception) {
                Log.d("homeViewModel", e.message.toString())
            }
        }
    }


    fun loadCategories() {
        viewModelScope.launch {
            try {
                _categories.value = getCategories()
            } catch (e: Exception) {
                Log.d("homeViewModel", e.message.toString())
            }
        }
    }



    fun loadMeals(){
        viewModelScope.launch {
            try {
                _meals.value = getMeals()
            } catch (e: Exception) {
                Log.d("homeViewModel", e.message.toString())
            }
        }
    }
    private fun observeUserName() {
        val uid = auth.currentUser?.uid ?: return

        viewModelScope.launch {
            preference.userName(uid).collect { name ->
                Log.d("HomeViewModel", "User name from DataStore = $name")
                if (name != null) {
                    _username.value = name
                }
            }
        }
    }

    private fun syncUserNameFromFirebase(){
        val user = auth.currentUser ?: return
        val name = user.displayName ?: return
        viewModelScope.launch {
            preference.saveUserName(user.uid,name)
            Log.d("HomeViewModel", "Saved name to DataStore: $name")
        }
    }

}