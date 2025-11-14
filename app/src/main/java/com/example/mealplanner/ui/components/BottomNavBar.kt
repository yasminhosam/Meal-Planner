package com.example.mealplanner.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Today
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Today
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mealplanner.navigation.NavigationItem

data class BottomNavItem(
    val label:String,
    val selectedIcon:ImageVector,
    val unselectedIcon:ImageVector,
    val route:String
)
@Composable
fun BottomNavBar(navController: NavHostController){
    val items= listOf(
        BottomNavItem(
            "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon =Icons.Outlined.Home ,
            NavigationItem.Home.route
        ),
        BottomNavItem("Search",
            selectedIcon = Icons.Filled.Search,
            unselectedIcon =Icons.Outlined.Search ,
            NavigationItem.Search.route
        ),
        BottomNavItem("Favorite",
            selectedIcon = Icons.Filled.Favorite,
            unselectedIcon =Icons.Outlined.FavoriteBorder ,
            NavigationItem.Favorite.route
        ),
        BottomNavItem(label = "Plan",
            selectedIcon = Icons.Filled.Today,
            unselectedIcon = Icons.Outlined.Today,
            route = NavigationItem.Plan.route
        )
    )
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute=navBackStackEntry?.destination?.route

        items.forEach{item ->
            val selected=currentRoute== item.route
            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.route){
                        // Pop up to the start destination
                        popUpTo(NavigationItem.Home.route) {
                            saveState = true
                        }
                        //Prevents stacking multiple copies of the same screen on top of each other
                        launchSingleTop=true
                        // check if you have any state saved for this route. If you do, restore it.
                        restoreState=true
                    }

                },
                icon = {
                    Icon(
                        imageVector = if(selected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label
                    )
                },
                label = {Text(item.label)},
                alwaysShowLabel = false,
                // 5. Apply custom theme colors
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                )

            )
        }
    }
}

