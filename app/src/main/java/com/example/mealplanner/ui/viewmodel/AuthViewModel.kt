package com.example.mealplanner.ui.viewmodel

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealplanner.navigation.NavigationItem
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel:ViewModel(){
    var loginEmail by mutableStateOf("")
    var loginPassword by mutableStateOf("")

    // Sign Up State
    var signUpUsername by mutableStateOf("")
    var signUpEmail by mutableStateOf("")
    var signUpPassword by mutableStateOf("")
    // track request status
    var loginState by mutableStateOf<AuthState>(AuthState.Idle)
        private set


    var signUpState by mutableStateOf<AuthState>(AuthState.Idle)
        private set

    private val auth:FirebaseAuth=FirebaseAuth.getInstance()

    fun onLoginClick(){
        if(loginEmail.isBlank() || loginPassword.isBlank()) {
            loginState = AuthState.Error("Please fill in all fields")
            return
        }
            loginState=AuthState.Loading

            auth.signInWithEmailAndPassword(loginEmail,loginPassword)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        loginState=AuthState.Success
                    }
                    else{
                        loginState=AuthState.Error(task.exception?.message?:"login failed")
                    }
                }

    }

        fun resetState() {
            loginState = AuthState.Idle
            signUpState=AuthState.Idle
        }

    fun onSignUpClick(){
        if( signUpUsername.isBlank()|| signUpEmail.isBlank() || signUpPassword.isBlank()) {
            signUpState = AuthState.Error("Please fill in all fields")
            return
        }
        if (signUpPassword.length < 6) {
            signUpState = AuthState.Error("Password must be at least 6 characters")
            return
        }
        viewModelScope.launch {
            try{
                signUpState=AuthState.Loading

                auth.createUserWithEmailAndPassword(signUpEmail,signUpPassword).await()
                //update profile
                val user =auth.currentUser

                val profileUpdates=UserProfileChangeRequest.Builder()
                    .setDisplayName(signUpUsername)
                    .build()

                user?.updateProfile(profileUpdates)?.await()

                signUpState=AuthState.Success

            }catch (e:Exception){
                signUpState=AuthState.Error(e.message?:"sign Up failed")
            }
        }


    }
}
    sealed interface AuthState{
         object Idle:AuthState
        object  Loading:AuthState
        object Success:AuthState
        data class Error(val message:String):AuthState
    }
