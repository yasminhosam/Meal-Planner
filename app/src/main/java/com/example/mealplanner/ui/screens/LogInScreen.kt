package com.example.mealplanner.ui.screens

import android.widget.Toast
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
import androidx.compose.material3.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mealplanner.R
import com.example.mealplanner.navigation.NavigationItem
import com.example.mealplanner.ui.components.CustomButton
import com.example.mealplanner.ui.components.CustomTextField
import com.example.mealplanner.ui.components.SocialButton
import com.example.mealplanner.ui.viewmodel.AuthState
import com.example.mealplanner.ui.viewmodel.AuthViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun LogInScreen(
    navController: NavController,
    viewModel: AuthViewModel= hiltViewModel()
    ) {
//    var password by remember { mutableStateOf("") }
//    var email by remember { mutableStateOf("") }

    val context= LocalContext.current
    val state=viewModel.loginState
    LaunchedEffect(key1 =state ) {
        when(state){
            is AuthState.Success ->{
                Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show()
                navController.navigate(NavigationItem.Home.route){
                    popUpTo("auth_graph"){inclusive=true}
                }
            }
            is AuthState.Error ->{
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            else ->{}
        }
    }
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
        Text("Login", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Text("Welcome back ", color = Color.Gray)
        Spacer(modifier = Modifier.height(2.dp))

        CustomTextField(
            value = viewModel.loginEmail,
            onValueChange = { viewModel.loginEmail = it },
            leadinIcon = { Icon(Icons.Default.Email, contentDescription = "email") },
            label = "eamil"
        )
        Spacer(modifier = Modifier.height(11.dp))
        CustomTextField(
            value = viewModel.loginPassword,
            onValueChange = { viewModel.loginPassword = it },
            leadinIcon = { Icon(Icons.Default.Lock, contentDescription = "password") },
            label = "password",
            isPassword = true
        )
        Text(
            text = "Forgot Password?",
            color = Color(0xFFFD7E14),
            modifier = Modifier
                .align(Alignment.End)
                .clickable {},
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(24.dp))
        if(state is AuthState.Loading){
            CircularProgressIndicator()
        }else {
            CustomButton("Login", onClick = {
                viewModel.onLoginClick()

            })
        }
//
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(modifier = Modifier.weight(1f))
            Text(
                text = "  Or login with  ",
                color = Color.Gray,
                fontSize = 13.sp
            )
            Divider(modifier = Modifier.weight(1f))
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            SocialButton(R.drawable.facebook, onClick = {})
            SocialButton(R.drawable.google, onClick = {})

        }
        Spacer(modifier = Modifier.height(24.dp))
        Row {
            Text(text = "Don't have an account?", color = Color.Gray)

            Spacer(modifier = Modifier.width(6.dp))


            Text(
                text = "Sign Up",
                color = Color(0xFFE74C3C),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { navController.navigate(NavigationItem.SignUp.route) }
            )
        }
    }
}


