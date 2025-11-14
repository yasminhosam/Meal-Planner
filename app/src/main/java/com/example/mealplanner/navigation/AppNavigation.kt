package com.example.mealplanner.navigation

sealed class NavigationItem(val route:String) {
    object SignUp:NavigationItem("sing_up")
    object Login:NavigationItem("login")
    object Home : NavigationItem("home")
    object Profile : NavigationItem("profile")
    object Search:NavigationItem("search")
    object Favorite:NavigationItem("favorite")
    object RecipeDetails: NavigationItem("details")
    object Plan:NavigationItem("Plan")

}