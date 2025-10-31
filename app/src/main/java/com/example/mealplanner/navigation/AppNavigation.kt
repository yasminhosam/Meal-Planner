package com.example.mealplanner.navigation

sealed class NavigationItem(val route:String) {
    object Home : NavigationItem("home_screen")
    object Profile : NavigationItem("profile_screen")

}