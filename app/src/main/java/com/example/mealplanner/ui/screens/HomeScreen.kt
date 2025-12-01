package com.example.mealplanner.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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

    val query by viewModel.searchQuery.collectAsState()
    val active by viewModel.isSearchActive.collectAsState()
    val searchResult by viewModel.filteredMeals.collectAsState()
    val user=Firebase.auth.currentUser
    val username=user?.displayName?:"User"

    val onMealClick:(String)->Unit={id->
        navController.navigate(NavigationItem.RecipeDetails.route)
    }
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
            IconButton(onClick = {navController.navigate(NavigationItem.Profile.route)}) {

                Image(
                    painter = painterResource(R.drawable.user),
                    contentDescription = "user profile picture",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(3.dp)
                        .clip(CircleShape)

                )
            }
            Spacer(modifier = Modifier.weight(0.1F))
            Column {
                Text("Hi, $username", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(2.dp))
                Text("What are you cooking today?", color = Color.Gray)
            }
            Spacer(modifier = Modifier.weight(1f))


        }
        SimpleSearchBar(
            query = query,
            onQueryChange = viewModel::onQueryChange,
            active = active,
            onActiveChange = viewModel::onActiveChange,
            onSearch = { viewModel.searchByFirstLetter(query) },
            searchResult = searchResult,
            navController = navController,
            modifier = Modifier,

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
            MealCategories( categoriesState!!)}

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