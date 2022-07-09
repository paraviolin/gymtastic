package it.matteo.gymtastic.presentation.exercise

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.twotone.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import it.matteo.gymtastic.domain.exercise.model.ExerciseModel
import it.matteo.gymtastic.presentation.Screens
import it.matteo.gymtastic.presentation.auth.viewModel.LoadingState
import it.matteo.gymtastic.presentation.common.LoaderComponent
import it.matteo.gymtastic.presentation.exercise.viewModel.ExerciseViewModel

@Composable
fun ExercisesScreen(navHostController: NavHostController) {
    val exerciseViewModel = hiltViewModel<ExerciseViewModel>()
    val exercises = remember {
        exerciseViewModel.exercises
    }
    val state by exerciseViewModel.loadingState.collectAsState()


    exerciseViewModel.fetchExercises()

    if (state == LoadingState.LOADING) {
        LoaderComponent()
        return
    }

    Scaffold(topBar = {
        TopAppBar(
            modifier = Modifier,
            title = { Text(text = "") },
            navigationIcon = {
                if (navHostController.previousBackStackEntry != null) {
                    IconButton(onClick = { navHostController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            }
        )
    }, floatingActionButton = {
        FloatingActionButton(
            modifier = Modifier.padding(bottom = 16.dp),
            backgroundColor = Color.Transparent,
            onClick = { navHostController.navigate(Screens.CreateExercise.name) }) {
            Icon(
                Icons.TwoTone.Add,
                contentDescription = "Add training card",
                modifier = Modifier.fillMaxSize(0.07f),
                tint = MaterialTheme.colors.secondary
            )
        }
    },
        isFloatingActionButtonDocked = true, floatingActionButtonPosition = FabPosition.End
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "EXERCISES",
                modifier = Modifier.padding(vertical = 16.dp),
                style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.ExtraLight),
            )
            ExerciseCardsColumn(exercises = exercises.value, navHostController = navHostController)
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExerciseCardsColumn(exercises: List<ExerciseModel>?, navHostController: NavHostController) {
    exercises?.let {
        LazyColumn(modifier = Modifier.padding(bottom = 56.dp)) {
            items(it, itemContent =
            { item ->
                Card(
                    modifier = Modifier.padding(
                        horizontal = 55.dp,
                        vertical = 8.dp
                    ),
                    elevation = 30.dp,
                    shape = MaterialTheme.shapes.medium,
                    backgroundColor = MaterialTheme.colors.secondary,
                    onClick = {
                        navHostController.navigate("${Screens.WorkoutDetail.name}/${item.id}")
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .padding(30.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = item.name,
                            style = MaterialTheme.typography.body2
                        )
                        Text(text = item.duration)
                    }
                }

            }
            )
        }
    }

}