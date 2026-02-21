package com.example.mealplanner.data.remote

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.example.mealplanner.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

class GoogleAuthUiClient(
    private val context: Context,
    private  val tapClient:SignInClient
) {
    // google one tap return IntentSender not Intent
    suspend fun signIn(): IntentSender?{
        val result=try {
            tapClient.beginSignIn(
                buildSignInRequest()
            ).await()

        }catch (e:Exception){
            e.printStackTrace()
            if( e is CancellationException) throw e
            null

        }
        return result?.pendingIntent?.intentSender

    }

     fun getSignInResultFromIntent(intent: Intent):String?{
        val credential=tapClient.getSignInCredentialFromIntent(intent)
        return credential.googleIdToken
    }


    //describes what kind of Google login we want
    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)// enables google id token login
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString( R.string.default_web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)// if we want to auto select the account
            .build()

    }
}