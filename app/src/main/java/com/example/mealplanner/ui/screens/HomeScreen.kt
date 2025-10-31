package com.example.mealplanner.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mealplanner.R
import com.example.mealplanner.navigation.NavigationItem
import com.example.mealplanner.ui.components.MealCategories
import com.example.mealplanner.ui.components.RecipesSection
import com.example.mealplanner.ui.components.SimpleSearchBar
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay


@Composable
fun HomeScreen(navController: NavController) {

    var searchText by rememberSaveable { mutableStateOf("") }
    var searchActive by rememberSaveable { mutableStateOf(false) }
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
                Text("Hi, user", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(2.dp))
                Text("What are you cooking today?", color = Color.Gray)
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Filled.Notifications,
                contentDescription = "Notification",
                modifier = Modifier.size(23.dp),
                Color.Black
            )

        }
        SimpleSearchBar(
            query = searchText,
            onQueryChange = { searchText = it },
            active = searchActive,
            onActiveChange = { searchActive = it },
            modifier = Modifier
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Categories",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(4.dp)
        )
        MealCategories()
        Spacer(modifier = Modifier.height(10.dp))
        RecipesSection()

    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun HomeScreenPreview() {
//    val fakeNavController = rememberNavController()
//    HomeScreen(navController = fakeNavController)
//}