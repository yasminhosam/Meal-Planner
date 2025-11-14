package com.example.mealplanner.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Egg
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Icecream
import androidx.compose.material.icons.filled.Liquor
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.outlined.RestaurantMenu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonShapes
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Shapes
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mealplanner.R
import com.example.mealplanner.ui.components.FavoriteButton

@Composable
fun RecipeDetailsScreen(navController: NavController) {
    var selectedTabIndex by rememberSaveable { mutableStateOf(0) }
    val tabTitles = listOf("INGREDIENTS", "INSTRUCTIONS", "TUTORIAL")
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            imageVector = Icons.Outlined.RestaurantMenu,
            contentDescription = "Meal image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.45f)
                .align(Alignment.TopCenter)
                .background(color = Color.Gray)
        )

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
                    text = "Meal name",
                     )
                var isFav by rememberSaveable { mutableStateOf(false) }
                FavoriteButton(
                    isFavorite = isFav,
                    onFavoriteClick = {isFav=it},
                    backgroundEnabled = true
                )

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
                            .padding(horizontal = 6.dp, vertical = 8.dp) // 👈 you control spacing
                    )
                }
            }

            when (selectedTabIndex) {
                0 -> IngredientsContent()
                1 -> InstructionsContent()
                2 -> TutorialContent()
            }

        }

        Button(
            onClick = {},
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(60.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFD7623E)),
            shape = RoundedCornerShape(23.dp)

        ) {
            Text("Add to plan", fontSize = 21.sp)
        }
    }

}

@Composable
fun TutorialContent() {
    Text(
        text = "Tutorial Video would go here.",
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    )

}

@Composable
fun InstructionsContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Step 1: Brine the chicken...")
        Text("Step 2: Prepare the dredge...")

    }
}



//  Data Model for an Ingredient
data class Ingredient(
    val name: String,
    val quantity: String,
    val icon: ImageVector
)

//  Dummy data to show in our list

val dummyIngredientList = listOf(
    Ingredient("Pickle Juice", "1/4 cup", Icons.Default.Liquor),
    Ingredient("Egg", "1", Icons.Default.Egg),
    Ingredient("Milk", "1/4 cup", Icons.Default.Liquor),
    Ingredient("Flour", "1/2 cup", Icons.Default.Fastfood),
    Ingredient("Icing Sugar", "1 tbs", Icons.Default.Icecream),
    Ingredient("Chicken Breast", "2 large", Icons.Default.Fastfood),
    Ingredient("Peanut Oil", "3 cups", Icons.Default.Liquor),
    Ingredient("Buns", "2", Icons.Default.Fastfood)
)
@Composable
fun IngredientsContent() {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)
    ) {
        items(dummyIngredientList){ ingredient ->
            IngredientItem(ingredient = ingredient)
        }
    }
}

@Composable
fun IngredientItem(ingredient: Ingredient) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ){
        Icon(
            imageVector = ingredient.icon,
            contentDescription = ingredient.name,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = ingredient.name,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = ingredient.quantity,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRecipeDetailsScreen() {

}