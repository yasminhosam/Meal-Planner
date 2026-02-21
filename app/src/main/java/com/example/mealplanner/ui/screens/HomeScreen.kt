package com.example.mealplanner.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mealplanner.R
import com.example.mealplanner.navigation.NavigationItem
import com.example.mealplanner.ui.components.MealCategories
import com.example.mealplanner.ui.components.RecipesSection
import com.example.mealplanner.ui.components.SimpleSearchBar
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.mealplanner.ui.viewmodel.HomeViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel= hiltViewModel()
) {
    val categoriesState by viewModel.categories.collectAsState()
    val mealsState by viewModel.meals.collectAsState()
    Log.d("homeScreen"," size of meals is ${mealsState?.meals?.size}")

    val query by viewModel.searchQuery.collectAsState()
    val active by viewModel.isSearchActive.collectAsState()
    val searchResult by viewModel.filteredMeals.collectAsState()

    val image by viewModel.profileImage.collectAsStateWithLifecycle()
    val username by viewModel.username.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier.clickable {
                    navController.navigate(NavigationItem.Profile.route)
                }
            ){

                AsyncImage(
                    model = image ?: R.drawable.user,
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
                        ,
                    contentScale = ContentScale.Crop,

                )
            }
            Spacer(modifier = Modifier.weight(0.1F))
            Column {
                Text("Hi, $username", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(2.dp))
                Text("What are you cooking today?", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
            }
            Spacer(modifier = Modifier.weight(1f))


        }
        SimpleSearchBar(
            query = query,
            onQueryChange = viewModel::onQueryChange,
            active = active,
            onActiveChange = viewModel::onActiveChange,
            onSearch = { viewModel.searchByFName(query) },
            searchResult = searchResult,
            navController = navController,


        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Categories",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(4.dp)
        )
        if (categoriesState != null) {
            // It is safe to use !! here because we checked the 'if' above
            MealCategories(
                response = categoriesState!!
            ) { categoryName ->
                navController.navigate(
                    NavigationItem.Category.createRoute(categoryName)
                )
            }

        }

        Spacer(modifier = Modifier.height(4.dp))
        if(mealsState !=null)
        RecipesSection( navController,mealsState!!)

    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun HomeScreenPreview() {
//    val fakeNavController = rememberNavController()
//    HomeScreen(navController = fakeNavController)
//}