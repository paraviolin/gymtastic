package it.matteo.gymtastic.presentation.workoutDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import it.matteo.gymtastic.presentation.common.BottomNavigationBar

@Composable
fun WorkoutDetailScreen(navHostController: NavHostController, exerciseId: String?) {
    Scaffold(bottomBar = { BottomNavigationBar(navHostController) }) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = exerciseId ?: "",
                modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
            )
        }
    }
}