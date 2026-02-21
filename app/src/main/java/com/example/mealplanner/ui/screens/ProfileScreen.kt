package com.example.mealplanner.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Edit

import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mealplanner.R
import com.example.mealplanner.navigation.NavigationItem
import com.example.mealplanner.ui.components.ProfileRow
import com.example.mealplanner.ui.components.ProfileSwitchRow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mealplanner.ui.viewmodel.ProfileViewModel


@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel= hiltViewModel()

) {
    val image by viewModel.profileImage.collectAsStateWithLifecycle()
    val imageVersion by viewModel.imageUpdateTrigger.collectAsStateWithLifecycle()
    val isDarkMode by viewModel.isDarkMode.collectAsStateWithLifecycle()
    val username by viewModel.userName.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let { viewModel.uploadProfileImage(it) }
        }
    )

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Box(contentAlignment = Alignment.BottomEnd) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(image?: R.drawable.user)
                    .setParameter("version",imageVersion)
                    .crossfade(true)
                    .build(),
                contentDescription = "user profile picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
            )
            IconButton(
                onClick = {
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                modifier = Modifier
                    .size(32.dp)
                    .background(MaterialTheme.colorScheme.primary, CircleShape)

            ) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = "Edit Photo",
                    tint = MaterialTheme.colorScheme.onPrimary,


                    )
            }

        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(
           text = "$username",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold

        )
        Spacer(modifier = Modifier.height(8.dp))
        ProfileRow(
            icon = Icons.Outlined.Person,
            text = "Edit Profile",
            trailing = {
                Icon(Icons.Default.Edit, contentDescription = "Edit Profile")
            },
            onClick = {navController.navigate(NavigationItem.EditProfile.route)}

        )
        Spacer(modifier = Modifier.height(12.dp))
//        ProfileSwitchRow(
//            icon = Icons.Outlined.Notifications,
//            text = "Notifications"
//        )
        ProfileSwitchRow(
            icon = Icons.Outlined.DarkMode,
            text = "Dark Mode",
            checked = isDarkMode,
            onCheckedChange = { viewModel.toggleTheme() }
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedButton(
            onClick = {
                viewModel.logout()
                navController.navigate(NavigationItem.Login.route) {
                    popUpTo(navController.graph.id) { inclusive = true }
                }
            },
            border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.error),
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = "Logout",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}