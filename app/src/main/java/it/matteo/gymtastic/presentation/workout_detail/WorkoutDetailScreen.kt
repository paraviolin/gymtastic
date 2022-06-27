package it.matteo.gymtastic.presentation.workout_detail

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import it.matteo.gymtastic.presentation.common.BottomNavigationBar

@Composable
fun WorkoutDetailScreen(navHostController: NavHostController) {
    val list = listOf(1, 2, 3, 4, 5, 6, 7)
    Scaffold(bottomBar = { BottomNavigationBar(navHostController = navHostController) }){
        LazyColumn(modifier = Modifier.fillMaxHeight()) {
            items(
                itemContent = { item ->
                    Text(text = item.toString())
                }, count = list.size
            )
        }
    }
}
