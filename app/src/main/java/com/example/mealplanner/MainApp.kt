package com.example.mealplanner

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.mealplanner.navigation.NavGraph


@Composable
fun MainApp(){
    val navController= rememberNavController()

    Scaffold(
        bottomBar = {}
    ) { innerpading ->
            NavGraph(navController, modifier =Modifier.padding(innerpading))
    }
}