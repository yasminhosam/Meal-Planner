package com.example.mealplanner.data.local

import android.content.Context
import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileImageManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val auth: FirebaseAuth
) {
    //takes an image from gallery and copies it to internal storage
    suspend fun saveImageToInternalStorage(uri:Uri):String? {
        val userId= auth.currentUser?.uid?:return null
        return withContext(Dispatchers.IO) {
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                    ?: return@withContext null
                // create directory if it doesn't exist
                val userDir=File(context.filesDir,"profiles/$userId")
                if(!userDir.exists()) userDir.mkdirs()

                val file = File(userDir, "profile.jpg")
                // .use{} automatically closes the stream
                file.outputStream().use { output ->
                    inputStream.use { input ->
                        input.copyTo(output)
                    }
                }
                "profiles/$userId/profile.jpg"
            } catch (e: Exception) {
                e.printStackTrace()
                null

            }
        }
    }

    fun getProfileImageFile(relativePath:String):File{
        return File(context.filesDir,relativePath)
    }

    fun clearCurrentUserImage() {
        val userId = auth.currentUser?.uid ?: return
        File(context.filesDir, "profiles/$userId").deleteRecursively()
    }

}