package com.example.mealplanner.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mealplanner.R
import com.example.mealplanner.navigation.NavigationItem
import com.example.mealplanner.ui.components.CustomButton
import com.example.mealplanner.ui.components.CustomTextField
import com.example.mealplanner.ui.components.SocialButton

@Composable
fun SignUpScreen(navController:NavController) {
    val userName = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.recipe),
            contentDescription = "",
            modifier = Modifier
                .padding(23.dp)


        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("Sing Up", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Text("Create your new account", color = Color.Gray)
        Spacer(modifier = Modifier.height(2.dp))
        CustomTextField(
            value = userName.value,
            onValueChange = { userName.value = it },
            leadinIcon = { Icon(Icons.Default.Person, contentDescription = "person") },
            label = "username"
        )
        Spacer(modifier = Modifier.height(11.dp))
        CustomTextField(
            value = email.value,
            onValueChange = { email.value = it },
            leadinIcon = { Icon(Icons.Default.Email, contentDescription = "email") },
            label = "eamil"
        )
        Spacer(modifier = Modifier.height(11.dp))
        CustomTextField(
            value = password.value,
            onValueChange = { password.value = it },
            leadinIcon = { Icon(Icons.Default.Lock, contentDescription = "password") },
            label = "password",
            isPassword = true
        )
        Spacer(modifier = Modifier.height(22.dp))
        CustomButton("Sing Up", onClick = {})
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(modifier = Modifier.weight(1f))
            Text(
                text = "  Or sing up with  ",
                color = Color.Gray,
                fontSize = 13.sp
            )
            Divider(modifier = Modifier.weight(1f))
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            SocialButton(R.drawable.facebook)
            SocialButton(R.drawable.google)

        }
        Spacer(modifier = Modifier.height(24.dp))
        Row {
            Text(text = "Already have an account?", color = Color.Gray)

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = "Login",
                color = Color(0xFFE74C3C),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { navController.navigate(NavigationItem.Login.route) }
            )
        }
    }
}
