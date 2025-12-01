package com.example.mealplanner.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mealplanner.ui.components.SimpleSearchBar


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(){
    var searchText by rememberSaveable { mutableStateOf("") }
    var searchActive by rememberSaveable { mutableStateOf(false) }
   Scaffold(
       modifier = Modifier
           .padding(8.dp)
   ) {
//       SimpleSearchBar(
//       query = searchText,
//       onQueryChange = { searchText = it },
//       active = searchActive,
//       onActiveChange = { searchActive = it },
//       modifier = Modifier
//   )
   }

}
