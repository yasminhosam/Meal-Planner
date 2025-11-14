package com.example.mealplanner.ui.components

import android.graphics.drawable.Icon
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mealplanner.R
import com.example.mealplanner.navigation.NavigationItem


@Composable
fun CustomTextField(
    value:String,
    onValueChange:(String)->Unit,
    leadinIcon: @Composable (()->Unit),
    label:String,
    isPassword:Boolean=false
){
    var passwordVisible by remember { mutableStateOf(false) }
    OutlinedTextField(value = value, onValueChange = onValueChange,
        leadingIcon = leadinIcon,
        label = { Text(label, color = Color(0xFFFF9800)) },
        modifier = Modifier.fillMaxWidth(),
        visualTransformation =
            if(isPassword && !passwordVisible)
                PasswordVisualTransformation()
            else
                VisualTransformation.None
          ,
        trailingIcon = {
            if (isPassword){
                val icon =if(passwordVisible)  Icons.Default.Visibility
                else Icons.Default.VisibilityOff


                IconButton(onClick = {passwordVisible=!passwordVisible}) {
                    Icon(imageVector = icon, contentDescription ="Toggle password visibility" )
                }
            }
        },
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFFF9800),
            unfocusedBorderColor = Color(0xFFFFB74D),
            cursorColor = Color(0xFFFF9800)
        )
    )
}


@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit
){
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(Color(0xFFFF6F00)),
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
    ) {
        Text(text, color = Color.White, fontSize = 18.sp)
    }
}



@Composable
fun SocialButton(icon: Int) {
    Surface(
        shape = CircleShape,
        //shadowElevation = 2.dp,
        color = Color.Transparent
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = null,

            modifier = Modifier
                .padding(12.dp)
                .size(24.dp)
        )
    }
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SimpleSearchBar(
    query:String,
    onQueryChange:(String)->Unit,
    active:Boolean,
    onActiveChange:(Boolean) -> Unit,
    modifier: Modifier = Modifier
) {


    SearchBar(
        modifier = Modifier.fillMaxWidth(),
        query = query,
        onQueryChange =onQueryChange,
        onSearch = {
            onActiveChange(false)
        },
        active = active,
        onActiveChange = onActiveChange,
        placeholder = {
            Text("Search")
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "search icon")
        },
        trailingIcon = {
            if (active) {
                Icon(
                    modifier = Modifier.clickable {
                        if (query.isNotEmpty()) {
                            onQueryChange("")
                        } else {
                            onActiveChange(false)
                        }
                    },
                    imageVector = Icons.Default.Close,
                    contentDescription = "close icon"
                )
            }
        },

        content = {
            if (query.isEmpty()) {
                Text("Type something to search your recipes")
            }
        }
    )
}

@Composable
fun MealCategories() {
    val categories = listOf("Breakfast", "Lunch", "Dinner", "Snacks","Diet","Dairy","sweets")

    LazyRow(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(categories.size) { index ->
            Surface(
                modifier = Modifier,
                shape = CircleShape,

                ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        painter = painterResource(R.drawable.user),
                        contentDescription = "categories icons",
                        modifier = Modifier.size(40.dp)
                    )
                     Spacer(modifier = Modifier.padding(6.dp))
                    Text(categories[index])
                }
            }

        }
    }
}

@Composable
fun RecipesSection(navController: NavController) {
    val recipes = listOf("pasta", "meat", "salad", "chicken", "soup", "pie", "cookies", "potato")
    LazyColumn(
        modifier = Modifier
            .padding(8.dp), verticalArrangement = Arrangement.spacedBy(9.dp)
    ) {
        items(recipes.size) { index ->
            val currentRecipe = recipes[index]
            RecipeCard(currentRecipe,navController)

        }
    }
}

@Composable
fun RecipeCard(recipeName: String = " Rcipce",navController: NavController) {
    Card(
        onClick = {navController.navigate(NavigationItem.RecipeDetails.route)},
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)

    ) {
        Box() {
            Image(
                painter = painterResource(R.drawable.cooking),
                contentDescription = "meal image",
                modifier = Modifier.fillMaxSize()
            )
            // 2. Gradient Scrim for readability of text
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = 300f // Start the gradient a bit down for better effect
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(11.dp)
            ) {
                var isFav by rememberSaveable { mutableStateOf(false) }
                FavoriteButton(
                    isFav, onFavoriteClick = {isFav=it},
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp))
                Text(
                    text = recipeName,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.BottomStart)
                )
            }

        }
    }
}

@Composable
fun ProfileRow(
    icon:ImageVector,
    text:String,
    trailing:@Composable (()->Unit)?=null,
    onClick:()->Unit={},
){
    Card(
        onClick=onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = Color.Black)
            Spacer(modifier = Modifier.width(12.dp))
            Text(text, modifier = Modifier.weight(1f), fontSize = 16.sp)
            trailing?.invoke()
        }
    }
}

@Composable
fun ProfileSwitchRow(
    icon: ImageVector,
    text: String
){
    var checked by rememberSaveable { mutableStateOf(false) }
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(6.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = Color.Black)
            Spacer(modifier = Modifier.width(12.dp))
            Text(text, modifier = Modifier.weight(1f), fontSize = 16.sp)
           Switch(
               checked =checked,
               onCheckedChange = {checked=it},
               modifier =Modifier.scale(0.6f)

               )
        }
    }
}

@Composable
fun FavoriteButton(
    isFavorite:Boolean,
    onFavoriteClick:(Boolean)->Unit,
    modifier: Modifier=Modifier,
    backgroundEnabled:Boolean=false
){
    val bgColor by animateColorAsState(
        targetValue = if (isFavorite && backgroundEnabled) Color(0xFFFFEBEE) else Color.Transparent,
        label = ""
    )
    IconButton(
        onClick = {onFavoriteClick(!isFavorite)},
        modifier=modifier
            .background(bgColor,CircleShape)
        ) {
        Icon(
            imageVector =  if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = "Favorite",
            tint = if(isFavorite) Color.Red else Color.Gray,
            modifier = modifier
        )
    }
}
@Preview(showBackground = true)
@Composable
fun Preview() {
   // RecipeCard()

}