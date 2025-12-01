package com.example.mealplanner

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.ContactsContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mealplanner.navigation.NavGraph
import com.example.mealplanner.navigation.NavigationItem
import com.example.mealplanner.ui.components.BottomNavBar


import com.example.mealplanner.ui.theme.MealPlannerTheme
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MealPlannerTheme {
                val navController= rememberNavController()
                //observe which screen (destination) is currently visible.
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                val currentRoute=navBackStackEntry?.destination?.route

                val screensWithBottomBar = listOf(
                    NavigationItem.Home.route,
                    NavigationItem.Search.route,
                    NavigationItem.Favorite.route,
                    NavigationItem.Plan.route
                )

                Scaffold(
                    bottomBar = {
                        if(currentRoute in screensWithBottomBar)
                            BottomNavBar(navController)
                    }
                ) { innerpading ->
                   NavGraph(navController, modifier = Modifier.padding(innerpading))

                }
            }
        }
    }
}
