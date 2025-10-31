package com.example.mealplanner.navigation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.mealplanner.navigation.NavigationItem
import com.example.mealplanner.ui.screens.HomeScreen
import com.example.mealplanner.ui.screens.ProfileScreen
import androidx.navigation.compose.composable
@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier =Modifier){
    NavHost(
        navController = navController,
        startDestination = NavigationItem.Home.route,
        modifier = modifier
    ){
        composable(NavigationItem.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(NavigationItem.Profile.route) {
            ProfileScreen(navController)
        }
    }
}