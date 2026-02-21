package com.example.mealplanner.ui.screens


import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mealplanner.R
import com.example.mealplanner.navigation.NavigationItem
import com.example.mealplanner.data.remote.GoogleAuthUiClient
import com.example.mealplanner.ui.components.CustomButton
import com.example.mealplanner.ui.components.CustomTextField
import com.example.mealplanner.ui.components.SocialButton
import com.example.mealplanner.ui.viewmodel.AuthEvent
import com.example.mealplanner.ui.viewmodel.AuthViewModel
import com.example.mealplanner.ui.viewmodel.ProfileViewModel
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LogInScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel= hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val state by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val googleAuthUiClient by remember {
        mutableStateOf(
            GoogleAuthUiClient(
                context = context,
                tapClient = Identity.getSignInClient(context)
            )
        )
    }

    // launch google login and get result
    val googleLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        // user selected an account
        if (result.resultCode == Activity.RESULT_OK) {
            // extract the intent safely
            val intent = result.data ?: return@rememberLauncherForActivityResult

            val idToken = runCatching {
                googleAuthUiClient.getSignInResultFromIntent(intent)
            }.getOrNull()

            if (idToken != null) {
                viewModel.loginWithGoogle(idToken)

            }
        }

    }


    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            if (event is AuthEvent.LoginSuccess) {
                navController.navigate(NavigationItem.Home.route) {
                    popUpTo(NavigationItem.SignUp.route) { inclusive = true }
                }
            }
        }
    }
    LaunchedEffect(state.generalError) {
        state.generalError?.let {
            snackbarHostState.showSnackbar(it) }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(R.drawable.recipe),
                contentDescription = null,
                modifier = Modifier.padding(24.dp)
            )

            Text(
                "Login",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                "Welcome back",
                color = MaterialTheme.colorScheme.onSurface.copy(0.7f)
            )

            Spacer(modifier = Modifier.height(16.dp))


            CustomTextField(
                value = state.emailInput,
                onValueChange = { viewModel.onEmailChange(it) },
                leadingIcon = { Icon(Icons.Default.Email, null) },
                label = "Email",
                isError = state.emailError != null,
                errorText = state.emailError
            )

            Spacer(modifier = Modifier.height(12.dp))


            CustomTextField(
                value = state.passwordInput,
                onValueChange = { viewModel.onPasswordChange(it) },
                leadingIcon = { Icon(Icons.Default.Lock, null) },
                label = "Password",
                isPassword = true,
                isError = state.passwordError != null,
                errorText = state.passwordError
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (state.isLoading) {
                Spacer(modifier = Modifier.height(8.dp))
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }else{
                CustomButton(
                    text = "Login",
                    isEnabled = !state.isLoading,
                    onClick = {
                        viewModel.loginWithEmail()
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(modifier = Modifier.weight(1f))
                Text(
                    text = "  Or login with  ",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 13.sp
                )
                Divider(modifier = Modifier.weight(1f))
            }

            Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {

                SocialButton(R.drawable.google) {
                    scope.launch {
                        val intentSender = googleAuthUiClient.signIn()
                        if (intentSender != null) {
                            googleLauncher.launch(
                                IntentSenderRequest.Builder(intentSender).build()
                            )
                        }
                    }
                }

//            SocialButton(R.drawable.facebook) {
//
//            }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row {
                Text(
                    "Don't have an account?",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    "Sign Up",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        navController.navigate(NavigationItem.SignUp.route)
                    }
                )
            }
        }
    }
}



