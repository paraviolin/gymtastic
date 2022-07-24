package it.matteo.gymtastic.presentation.workoutDetail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import it.matteo.gymtastic.presentation.auth.viewModel.LoadingState
import it.matteo.gymtastic.presentation.common.LoaderComponent
import it.matteo.gymtastic.presentation.profile.components.TextFieldComponent
import it.matteo.gymtastic.presentation.workoutDetail.viewModel.WorkoutDetailViewModel

@Composable
fun WorkoutDetailScreen(navHostController: NavHostController, exerciseId: String?) {
    val workoutDetailViewModel: WorkoutDetailViewModel = hiltViewModel()
    workoutDetailViewModel.getExerciseDetail(exerciseId ?: "")
    val exercise = workoutDetailViewModel.exercise
    val state by workoutDetailViewModel.loadingState.collectAsState()
    val notes = workoutDetailViewModel.notes.value

    workoutDetailViewModel.getUser()

    if (exerciseId != null && workoutDetailViewModel.user.value?.id != null)
        workoutDetailViewModel.getNotes(
            exerciseId = exerciseId,
            userId = workoutDetailViewModel.user.value?.id!!
        )

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
    }) {
        when (state) {
            LoadingState.LOADING -> LoaderComponent()
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

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Icon(Icons.Default.DateRange, contentDescription = "Play")
                        Text(
                            text = exercise?.duration ?: "",
                            modifier = Modifier.padding(top = 16.dp),
                            style = MaterialTheme.typography.body1,
                            textAlign = TextAlign.Justify
                        )
                    }

                    Divider(
                        color = Color.DarkGray,
                        modifier = Modifier
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                            .fillMaxWidth()
                            .width(1.dp)
                    )

                    TextFieldComponent(
                        content = exercise?.description ?: "",
                        labelName = "Description",
                        labelFontSize = 16.sp,
                        onValueChange = {},
                        modifier = Modifier.padding(horizontal = 32.dp),
                        enabled = false,
                        shape = RoundedCornerShape(20),

                        )

                    notes?.let { exerciseNotes ->
                        if (exerciseNotes.isNotEmpty()) {
                            Text(
                                text = "Notes",
                                modifier = Modifier.padding(top = 16.dp),
                                style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
                            )
                            val filteredNotes = exerciseNotes.filterNot { it.id.isEmpty() }
                            LazyColumn {
                                items(filteredNotes, itemContent = {
                                    TextFieldComponent(
                                        content = it.note,
                                        labelName = it.createdAt?.toLocalDate().toString(),
                                        labelFontSize = 16.sp,
                                        onValueChange = {},
                                        modifier = Modifier.padding(horizontal = 32.dp),
                                        enabled = false,
                                        shape = RoundedCornerShape(20),
                                    )
                                })
                            }
                        }
                    }


                }
        }

    }
}