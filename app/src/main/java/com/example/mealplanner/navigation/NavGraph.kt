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
import androidx.navigation.compose.navigation
import com.example.mealplanner.ui.screens.FavoriteScreen
import com.example.mealplanner.ui.screens.LogInScreen
import com.example.mealplanner.ui.screens.PlanScreen
import com.example.mealplanner.ui.screens.RecipeDetailsScreen
import com.example.mealplanner.ui.screens.SearchScreen
import com.example.mealplanner.ui.screens.SignUpScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier =Modifier
){
    NavHost(
        navController = navController,
        startDestination = "auth_graph",
        modifier = modifier
    ){

        navigation(
            startDestination = NavigationItem.Login.route,
            route = "auth_graph"
        ) {
            composable(NavigationItem.Login.route) { LogInScreen(navController)  }
            composable(NavigationItem.SignUp.route) { SignUpScreen(navController) }


        }
        navigation(
            startDestination = NavigationItem.Home.route,
            route="main_graph"
        ){
            composable(NavigationItem.Home.route) {
                HomeScreen(navController = navController)
            }
            composable(NavigationItem.Profile.route) {
                ProfileScreen(navController)
            }
            composable(NavigationItem.Search.route) { SearchScreen()  }
            composable(NavigationItem.RecipeDetails.route){ RecipeDetailsScreen(navController) }

            composable(NavigationItem.Favorite.route) { FavoriteScreen()  }
            composable(NavigationItem.Plan.route) { PlanScreen()  }
        }
    }
}

