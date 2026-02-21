package com.example.mealplanner.ui.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealplanner.data.local.UserPreference
import com.example.mealplanner.data.local.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val preference: UserPreference,
    private val userRepository: UserRepository,
    private val auth: FirebaseAuth,
):ViewModel() {


    private val userId get() = auth.currentUser?.uid

    val userName = userId?.let {
        preference.userName(it)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue =""
            )
    }?: MutableStateFlow("")

    val profileImage=userRepository.userProfileImage
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )



    private val _imageUpdateTrigger = MutableStateFlow(System.currentTimeMillis())
    val imageUpdateTrigger = _imageUpdateTrigger.asStateFlow()

    fun uploadProfileImage(uri: Uri) {

        viewModelScope.launch {
            userRepository.updateProfileImage(uri)
            _imageUpdateTrigger.value=System.currentTimeMillis()
        }
    }

    val isDarkMode: StateFlow<Boolean> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser?.uid)
        }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }
        .flatMapLatest { uid ->
            if (uid != null) preference.isDarkMode(uid)
            else flowOf(false)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )




    fun toggleTheme(){
        val userId = userId?:return
        viewModelScope.launch {
            try {
                val current=isDarkMode.value
                val nextState = !current
                preference.saveTheme(userId,nextState)

                Log.d("ThemeDebug", "Successfully saved to DataStore: $nextState")
            } catch (e: Exception) {
                Log.e("ThemeDebug", "Failed to save theme", e)
            }

        }
    }


    fun updateUserName(newName: String) {
        Log.d("ProfileUpdate", "Function called with name: $newName")
        val user = auth.currentUser ?: run {
            Log.e("ProfileUpdate", "No user logged in!")
            return
        }
        val userId = userId?:return
        viewModelScope.launch {
            preference.saveUserName(userId,newName)
            Log.d("ProfileUpdate", "Saved to DataStore: $newName")
        }

        user.updateProfile(
            userProfileChangeRequest {
                displayName = newName
            }
        ).addOnFailureListener {
            Log.e("ProfileUpdate", "Failed to update name", it)

        }
    }

    fun logout() {
        viewModelScope.launch {

            auth.signOut()
        }
    }




}