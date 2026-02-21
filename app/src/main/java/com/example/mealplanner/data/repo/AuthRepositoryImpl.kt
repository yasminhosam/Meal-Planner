package com.example.mealplanner.data.repo

import com.example.mealplanner.domain.AuthMethod
import com.example.mealplanner.domain.repo.AuthRepository
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
):AuthRepository {
    override suspend fun login(method: AuthMethod) {
        when (method) {
            is AuthMethod.EmailPassword -> {
                firebaseAuth
                    .signInWithEmailAndPassword(
                        method.email,
                        method.password
                    ).await()
            }
            is AuthMethod.Google ->{
                val credential = GoogleAuthProvider.getCredential(method.idToken,null)
                firebaseAuth.signInWithCredential(credential).await()
            }
//            is AuthMethod.Facebook ->{
//                val credential =FacebookAuthProvider.getCredential(method.accessToken)
//                firebaseAuth.signInWithCredential(credential).await()
//            }

        }
    }

    override suspend fun signUp(email: String, password: String, username: String) {
        firebaseAuth.createUserWithEmailAndPassword(email,password).await()
        val user=firebaseAuth.currentUser
        // set username
        val profileUpdates=UserProfileChangeRequest.Builder()
            .setDisplayName(username).build()
        user?.updateProfile(profileUpdates)?.await()
        // send verification email
        user?.sendEmailVerification()?.await()
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
    }

    override fun isUserLoggedIn(): Boolean {
        return firebaseAuth.currentUser !=null
    }

    override suspend fun isEmailVerified(): Boolean {
        val user = firebaseAuth.currentUser
        user?.reload()?.await() // Refresh to see if they clicked the link
        return user?.isEmailVerified ?: false
    }
}