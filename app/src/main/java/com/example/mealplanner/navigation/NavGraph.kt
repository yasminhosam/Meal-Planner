package com.example.mealplanner.navigation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import com.example.mealplanner.ui.screens.HomeScreen
import com.example.mealplanner.ui.screens.ProfileScreen
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.mealplanner.ui.screens.CategoryScreen
import com.example.mealplanner.ui.screens.EditProfileScreen
import com.example.mealplanner.ui.screens.FavoriteScreen
import com.example.mealplanner.ui.screens.LogInScreen
import com.example.mealplanner.ui.screens.MealDetailsScreen
import com.example.mealplanner.ui.screens.PlannedMealsScreen
import com.example.mealplanner.ui.screens.SearchScreen
import com.example.mealplanner.ui.screens.SignUpScreen
import com.example.mealplanner.ui.screens.SplashScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier =Modifier,

){
    NavHost(
        navController = navController,
        startDestination = NavigationItem.Splash.route,
        modifier = modifier
    ){

        composable(NavigationItem.Splash.route) {
            SplashScreen(
                onFinish = { isLoggedIn ->
                    navController.navigate(
                        if (isLoggedIn as Boolean) "main_graph" else "auth_graph"
                    ) {
                        popUpTo(NavigationItem.Splash.route) { inclusive = true }
                    }
                }
            )
        }
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
            composable(NavigationItem.Search.route) { SearchScreen(navController =navController)  }
            composable(
                route=NavigationItem.RecipeDetails.route,
                arguments = listOf(
                    navArgument("id"){type=NavType.StringType}
                )
                ){ MealDetailsScreen(navController) }
            composable(
                route=NavigationItem.Category.route,
                arguments = listOf(
                    navArgument("category"){type=NavType.StringType}
                )
            ){CategoryScreen(navController)}

            composable(NavigationItem.Favorite.route) { FavoriteScreen(navController)  }
            composable(NavigationItem.Plan.route) { PlannedMealsScreen(navController)  }
            composable(NavigationItem.EditProfile.route) { EditProfileScreen(navController) }

        }
    }
}

