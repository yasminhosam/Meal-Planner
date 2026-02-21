package com.example.mealplanner.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.mealplanner.ui.components.SimpleSearchBar
import com.example.mealplanner.ui.viewmodel.SearchType
import com.example.mealplanner.ui.viewmodel.SearchViewModel
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavController
) {
    val query by viewModel.searchQuery.collectAsState()
    val active by viewModel.isSearching.collectAsState()
    val searchResult by viewModel.filteredMeals.collectAsState()
    val searchType by viewModel.searchType.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        SimpleSearchBar(
            query = query,
            onQueryChange = viewModel::onQueryChange,
            active = active,
            onActiveChange = viewModel::onActiveChange,
            onSearch = viewModel::search,
            searchResult = searchResult,
            navController = navController
        )

        SearchHint(searchType)

        SearchTypeChips(
            selected = searchType,
            onSelect = viewModel::onSearchTypeChange
        )
    }

}
@Composable
fun SearchHint(searchType: SearchType) {
    val hint = when (searchType) {
        SearchType.NAME -> "Search by meal name (e.g. Pasta)"
        SearchType.AREA -> "Search by country (e.g. Canadian)"
        SearchType.CATEGORY -> "Search by category (e.g. Seafood)"
        SearchType.INGREDIENT -> "Search by ingredient (e.g. Chicken)"
    }

    Text(
        text = hint,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        modifier = Modifier.padding(start = 8.dp)
    )
}
@Composable
fun SearchTypeChips(
    selected: SearchType,
    onSelect: (SearchType) -> Unit
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(SearchType.values()) { type ->
            val isSelected = selected == type

            FilterChip(
                selected = isSelected,
                onClick = { onSelect(type) },
                label = {
                    Text(
                        text = when (type) {
                            SearchType.NAME -> "Name"
                            SearchType.AREA -> "Country"
                            SearchType.CATEGORY -> "Category"
                            SearchType.INGREDIENT -> "Ingredient"
                        }
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}
