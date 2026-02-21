package com.example.mealplanner.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mealplanner.domain.entity.MealResponse
import com.example.mealplanner.ui.components.RecipesSection
import com.example.mealplanner.ui.viewmodel.FavoriteViewModel

@Composable
fun FavoriteScreen(
    navController: NavController,
    viewModel: FavoriteViewModel=hiltViewModel()
) {
    val favoriteMeals by viewModel.favoriteMeals.collectAsState()
    if (favoriteMeals.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("No favorite meals found")
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {

            RecipesSection(
                navController = navController,
                response = MealResponse(meals = favoriteMeals)
            )
        }
    }
}