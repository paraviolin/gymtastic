package it.matteo.gymtastic.presentation.session

import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import it.matteo.gymtastic.presentation.Screens
import it.matteo.gymtastic.presentation.auth.viewModel.LoadingState
import it.matteo.gymtastic.presentation.common.LoaderComponent
import it.matteo.gymtastic.presentation.common.OutlinedStyledButton
import it.matteo.gymtastic.presentation.profile.components.TextFieldComponent
import it.matteo.gymtastic.presentation.session.viewModel.SessionViewModel

@Composable
fun SessionScreen(navHostController: NavHostController, userId: String, trainingId: String) {
    val sessionViewModel: SessionViewModel = hiltViewModel()

    val state by sessionViewModel.loadingState.collectAsState()
    val currentExercise = sessionViewModel.currentExercise
    val notes = sessionViewModel.notes
    val needToSave = sessionViewModel.needToSave

    sessionViewModel.fetchExercises(trainingId)

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
            },
        )
    }) {
        when (state) {
            LoadingState.LOADING -> LoaderComponent()
            LoadingState.LOADED -> {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = currentExercise.value?.name ?: "",
                        modifier = Modifier.padding(top = 16.dp),
                        style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
                    )


                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Icon(Icons.Default.DateRange, contentDescription = "Play")
                        Text(
                            text = currentExercise.value?.duration ?: "",
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
                        content = currentExercise.value?.description ?: "",
                        labelName = "Description",
                        labelFontSize = 16.sp,
                        onValueChange = {},
                        modifier = Modifier.padding(horizontal = 32.dp),
                        enabled = false,
                        shape = RoundedCornerShape(20),
                    )

                    TextFieldComponent(
                        content = notes.value,
                        labelName = "Notes",
                        labelFontSize = 16.sp,
                        onValueChange = {
                            sessionViewModel.updateNote(it)
                        },
                        modifier = Modifier.padding(horizontal = 32.dp),
                        enabled = true,
                        shape = RoundedCornerShape(20),
                    )


                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlinedStyledButton(
                            onClick = {
                                sessionViewModel.saveCurrentSession(
                                    userId,
                                    trainingId
                                )
                                navHostController.navigate(Screens.Main.name)
                            },
                            textLabel = "Close",
                            color = MaterialTheme.colors.onError,
                        )
                        OutlinedStyledButton(
                            modifier = Modifier.padding(start = 32.dp),
                            onClick = {
                                if (needToSave.value) {
                                    sessionViewModel.saveCurrentSession(userId, trainingId)
                                    navHostController.navigate(Screens.Main.name)
                                } else {
                                    sessionViewModel.getNextExercise()
                                }
                            }, textLabel = if (needToSave.value)
                                "Save"
                            else
                                "Next"
                        )
                    }

                }
            }
        }
    }

}