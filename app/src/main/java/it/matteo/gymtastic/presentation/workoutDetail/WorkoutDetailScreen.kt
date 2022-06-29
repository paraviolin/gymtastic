package it.matteo.gymtastic.presentation.workoutDetail

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import it.matteo.gymtastic.presentation.auth.viewModel.LoadingState
import it.matteo.gymtastic.presentation.common.BottomNavigationBar
import it.matteo.gymtastic.presentation.workoutDetail.viewModel.WorkoutDetailViewModel

@Composable
fun WorkoutDetailScreen(navHostController: NavHostController, exerciseId: String?) {
    val workoutDetailViewModel: WorkoutDetailViewModel = hiltViewModel()
    workoutDetailViewModel.getExerciseDetail(exerciseId ?: "")
    val exercise = workoutDetailViewModel.exercise
    val state by workoutDetailViewModel.loadingState.collectAsState()

    Scaffold(bottomBar = { BottomNavigationBar(navHostController) }) {

        when(state) {
            LoadingState.LOADING ->
                Column(
                    Modifier
                        .padding(55.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            LoadingState.LOADED ->
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = exercise?.name ?: "",
                        modifier = Modifier.padding(top = 16.dp),
                        style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
                    )
                    Card(
                        contentColor = MaterialTheme.colors.primary,
                        shape = MaterialTheme.shapes.small
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = "Play")
                        Text(
                            text = exercise?.duration ?: "",
                            modifier = Modifier.padding(top = 16.dp),
                            style = MaterialTheme.typography.body1,
                            textAlign = TextAlign.Justify
                        )
                    }
                    Text(
                        text = exercise?.description ?: "",
                        modifier = Modifier.padding(top = 16.dp),
                        style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
                    )


                }
        }

    }
}