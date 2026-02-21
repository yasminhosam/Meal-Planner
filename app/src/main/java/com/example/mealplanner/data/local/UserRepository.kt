package com.example.mealplanner.data.local

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userPreference: UserPreference,
    private val profileImageManager: ProfileImageManager,
    private val auth: FirebaseAuth
) {
    private val userId:String? get() = auth.currentUser?.uid
    //when the data in preference changes this flow pushes the updated file to the collector
    //

    val userProfileImage:Flow<File?> = callbackFlow {
        val uId=userId?:return@callbackFlow

        userPreference.profileImagePath(uId).collect{path ->
            if (path!=null){
                val file=profileImageManager.getProfileImageFile(path)
                // push the file to the ViewModel
                trySend(file)

            }else{
                trySend(null)
            }

        }
    }


    suspend fun updateProfileImage(uri: Uri) {
        val uId = userId ?: return
        // save the bytes of the image into the internal storage
        //returns the relative path of the image
        profileImageManager.saveImageToInternalStorage(uri)?.let {
            //if saving worked update the datastore
            userPreference.saveImagePath(uId, it)
        }
    }

}