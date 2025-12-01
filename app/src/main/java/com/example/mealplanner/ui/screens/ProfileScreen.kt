package com.example.mealplanner.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.DarkMode

import androidx.compose.material.icons.outlined.Edit

import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mealplanner.R
import com.example.mealplanner.navigation.NavigationItem
import com.example.mealplanner.ui.components.ProfileRow
import com.example.mealplanner.ui.components.ProfileSwitchRow


@Composable
fun ProfileScreen(navController: NavController,modifier: Modifier=Modifier){

    Column(Modifier
        .fillMaxSize()
        .padding(8.dp)
        .background(color = Color(android.graphics.Color.parseColor("#ececec"))),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Box(contentAlignment =Alignment.BottomEnd){
            Image(
                painter = painterResource(R.drawable.user),
                contentDescription = "user profile picture",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.White, CircleShape)
            )
            IconButton(
                onClick = {},
                modifier = Modifier
                    .size(21.dp)
                    .background(Color.Red, CircleShape)
                    //.align(Alignment.BottomEnd)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = "Edit Photo",
                    tint = Color.White,


                )
            }

        }
        Spacer(modifier = Modifier.height(32.dp))
        ProfileRow(
            icon = Icons.Outlined.Person,
            text = "Edit Profile",
            trailing = {
                Icon(Icons.Default.Edit, contentDescription = "Edit Profile")
            },
            onClick = {}

        )
        Spacer(modifier = Modifier.height(12.dp))
        ProfileSwitchRow(
            icon = Icons.Outlined.Notifications,
            text = "Notifications"
        )
        ProfileSwitchRow(
            icon =Icons.Outlined.DarkMode ,
            text = "Dark Mode"
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedButton(
            onClick = {
                navController.navigate(NavigationItem.Login.route){
                    popUpTo(navController.graph.id){inclusive=true}
                }
            },
            border = BorderStroke(1.dp, color = Color.Red),
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = "Logout",
                fontSize = 21.sp
            )
        }
    }
}