package com.example.mealplanner.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mealplanner.domain.entity.MealResponse
import com.example.mealplanner.ui.components.RecipesSection
import com.example.mealplanner.ui.theme.OrangePrimary
import com.example.mealplanner.ui.viewmodel.PlannedMealsViewModel
import kotlinx.coroutines.delay
import java.time.LocalDate
@Composable
fun PlannedMealsScreen(
    navController: NavController,
    viewModel: PlannedMealsViewModel= hiltViewModel(),

) {
    val selectedDate by viewModel.selectedDate.collectAsState()
    val meals by viewModel.meals.collectAsState()

    val today = remember { LocalDate.now() }
    val weakDays =(0..6).map { today.plusDays(it.toLong()) }
    LaunchedEffect(selectedDate) {
        viewModel.onDateSelected(selectedDate)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
    ) {

        // Week selector
        WeekCalendarBar(
            selectedDate = selectedDate,
            onDateSelected = {viewModel.onDateSelected(it) },
            weekDays = weakDays
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Planned meals list
        if (meals.isEmpty()) {
            EmptyPlannedMealsState(selectedDate)
        } else {
            RecipesSection(
                navController,
                MealResponse(meals),
                onDeleteMeal = {mealId -> viewModel.deleteMeal(mealId,selectedDate) }
                )
            }
        }

}


@Composable
fun WeekCalendarBar(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    weekDays: List<LocalDate>
) {


    LazyRow(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        items(weekDays) { day ->
            MealDayItem(
                day = day,
                isSelected = day == selectedDate,
                onClick = { onDateSelected(day) }
            )
        }
    }
}
@Composable
fun MealDayItem(
    day: LocalDate,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .padding(6.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (isSelected)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.surface
            )
            .clickable(onClick = onClick)
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = day.dayOfWeek.name.take(3),
            color = if (isSelected) Color.White else Color.Gray,
            fontSize = 12.sp
        )
        Text(
            text = day.dayOfMonth.toString(),
            fontWeight = FontWeight.Bold,
            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun EmptyPlannedMealsState(date: LocalDate) {
    Text(
        text = "No meals planned for ${date.dayOfWeek}",
        modifier = Modifier.padding(24.dp),
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
fun SwipeToDeleteContainer(
    key: Any,
    onDelete: () -> Unit,
    animationDuration:Int=500,
    content: @Composable () -> Unit
) {
    // when key changes ,remember will reset the isRemoved state
    var isRemoved by remember(key) { mutableStateOf(false) }
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                isRemoved = true
                true
            } else false
        }
    )
    // trigger the actual deletion after the animation is complete
    LaunchedEffect(isRemoved) {
        if(isRemoved){
            delay(animationDuration.toLong())
            onDelete()
        }
    }
    //if meal isn't removed show the swipe to delete animation
    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        )

    ) {

        SwipeToDismissBox(
            state = dismissState,
            enableDismissFromStartToEnd = false,
            backgroundContent = { DeleteBackground(swipeToDismissBoxState = dismissState) },
            content = {content()},
        )
    }
}


@Composable
fun DeleteBackground(
    swipeToDismissBoxState: SwipeToDismissBoxState
){
    val color=if(swipeToDismissBoxState.dismissDirection==SwipeToDismissBoxValue.EndToStart){
        OrangePrimary.copy(alpha = 0.9f)
    }else Color.Transparent

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color, shape = RoundedCornerShape(20.dp))
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.CenterEnd
    ){
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete",
            tint = Color.White
        )
    }

}