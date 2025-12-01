package com.example.mealplanner.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Liquor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mealplanner.domain.entity.Meal
import com.example.mealplanner.ui.components.FavoriteButton
import com.example.mealplanner.ui.viewmodel.DetailsUiState
import com.example.mealplanner.ui.viewmodel.DetailsViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

@Composable
fun MealDetailsScreen(
    navController: NavController,
    viewModel: DetailsViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    when (state) {
        is DetailsUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator() // Show loader
            }
        }

        is DetailsUiState.Success -> {
            val meal = (state as DetailsUiState.Success).meal
            MealDetailsContent(navController,meal)

        }

        is DetailsUiState.Error -> {
            val msg = (state as DetailsUiState.Error).message
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = msg, color = Color.Red)
            }
        }

    }
}

@Composable
fun MealDetailsContent(navController: NavController,meal: Meal) {
    var selectedTabIndex by rememberSaveable { mutableStateOf(0) }
    val tabTitles = listOf("INGREDIENTS", "INSTRUCTIONS", "TUTORIAL")

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {



        AsyncImage(
            model = meal.strMealThumb,
            contentDescription = "Meal image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.45f)
                .align(Alignment.TopCenter)
                .background(color = Color.Gray)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .align(Alignment.TopStart)
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Black.copy(0.45f),Color.Transparent)
                    )
                )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .align(Alignment.TopStart),
            horizontalArrangement = Arrangement.SpaceBetween,

        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .background(Color.White.copy(0.7f), CircleShape)
                    .size(40.dp)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.Black)
            }

            var isFav by rememberSaveable { mutableStateOf(false) }
            FavoriteButton(
                isFavorite = isFav,
                onFavoriteClick = { isFav = it },
                //backgroundEnabled = true
            )

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 20.dp, vertical = 25.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = meal.strMeal ?: "",
                    style = MaterialTheme.typography.titleMedium
                )
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .background(Color.White.copy(0.7f), CircleShape)
                        .size(40.dp)
                ) {
                    Icon(Icons.Default.CalendarToday, contentDescription = "Back", tint = Color.Black)
                }

            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Text(
                        text = title,
                        style = if (index == selectedTabIndex)
                            MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.primary)
                        else
                            MaterialTheme.typography.labelMedium,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { selectedTabIndex = index }
                            .padding(
                                horizontal = 6.dp,
                                vertical = 8.dp
                            )
                    )
                }
            }

            when (selectedTabIndex) {
                0 -> IngredientsContent(meal.ingredients)
                1 -> InstructionsContent(meal.strInstructions)
                2 -> TutorialContent(meal.strYoutube)
            }

        }

//        Button(
//            onClick = {},
//            modifier = Modifier
//                .align(Alignment.BottomCenter)
//                .padding(60.dp),
//            colors = ButtonDefaults.buttonColors(Color(0xFFD7623E)),
//            shape = RoundedCornerShape(23.dp)
//
//        ) {
//            Text("Add to plan", fontSize = 21.sp)
//        }
    }

}


@Composable
fun TutorialContent(strYoutube: String) {
    val videoId=strYoutube.substringAfter("v=","")
    LaunchedEffect(videoId) {
        Log.d("YouTubeDebug", "Original URL: $strYoutube")
        Log.d("YouTubeDebug", "Extracted ID: $videoId")
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        item {
            Text(
                text = "Video Tutorial",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(7.dp)
            )
        }
        item {
            YouTubePlayer(videoId)
        }
    }


}

@Composable
fun YouTubePlayer(videoId: String) {
    if (videoId.isBlank()) {
        Text("No Tutorial Available")
        return
    }
    // get the lifecycle of the current screen so player knows when the app is paused, stopped, or destroyed
    val lifecycleOwner= LocalLifecycleOwner.current
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(RoundedCornerShape(12.dp)),
        // creating the actual android view
        factory = { context ->
            YouTubePlayerView(context).apply {
                lifecycleOwner.lifecycle.addObserver(this)
                addYouTubePlayerListener(object :AbstractYouTubePlayerListener(){
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.cueVideo(videoId,0f)
                    }
                })
            }

        },
        //  Remove observer when the Composable is disposed to prevent memory leaks
        onRelease = { view ->
            lifecycleOwner.lifecycle.removeObserver(view)
        }
    )
}

@Composable
fun InstructionsContent(strInstructions: String) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)

    ) {
        item {
            Text(
                text = "Cooking Instruction",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }


        val steps = strInstructions
            .split("\n")
            .map { it.trim() }
            .filter { it.isNotBlank() }
        itemsIndexed(steps) {index, step ->
            if (step.isNotBlank()) {

                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {

                    Text(
                        text = "${index + 1}.",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.width(28.dp)
                    )


                    Text(
                        text = step,
                        style = MaterialTheme.typography.bodyMedium,
                        // 'weight(1f)' makes the text take up all remaining space

                        modifier = Modifier.weight(1f)
                    )

                }
            }

        }

    }

}


////  Data Model for an Ingredient
//data class Ingredient(
//    val name: String,
//    val quantity: String,
//    val icon: ImageVector
//)
//
////  Dummy data to show in our list
//
//val dummyIngredientList =
//    listOf(
//        Ingredient("Pickle Juice", "1/4 cup", Icons.Default.Liquor),
//        Ingredient("Egg", "1", Icons.Default.Egg),
//        Ingredient("Milk", "1/4 cup", Icons.Default.Liquor),
//        Ingredient("Flour", "1/2 cup", Icons.Default.Fastfood),
//        Ingredient("Icing Sugar", "1 tbs", Icons.Default.Icecream),
//        Ingredient("Chicken Breast", "2 large", Icons.Default.Fastfood),
//        Ingredient("Peanut Oil", "3 cups", Icons.Default.Liquor),
//        Ingredient("Buns", "2", Icons.Default.Fastfood)
//    )

@Composable
fun IngredientsContent(ingredients: List<Pair<String, String>>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(ingredients) { ingredient ->
            IngredientItem(ingredient = ingredient)
        }
    }
}

@Composable
fun IngredientItem(ingredient: Pair<String, String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Cake,
            contentDescription = ingredient.first,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)

        )
        Spacer(modifier = Modifier.width(19.dp))

        Text(
            text = ingredient.first,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = ingredient.second,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRecipeDetailsScreen() {

}