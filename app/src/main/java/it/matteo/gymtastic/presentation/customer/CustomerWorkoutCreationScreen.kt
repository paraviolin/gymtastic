package it.matteo.gymtastic.presentation.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import it.matteo.gymtastic.domain.exercise.model.ExerciseModel
import it.matteo.gymtastic.domain.exercise.model.ExerciseType
import it.matteo.gymtastic.presentation.common.OutlinedStyledButton
import it.matteo.gymtastic.presentation.customer.viewModel.CustomerViewModel
import it.matteo.gymtastic.presentation.profile.components.BasicTextField
import it.matteo.gymtastic.presentation.profile.components.TextFieldComponent

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomerWorkoutCreationScreen(navHostController: NavHostController, customerId: String) {
    val customerViewModel: CustomerViewModel = hiltViewModel()
    customerViewModel.fetchAllAvailableExercises()
    customerViewModel.fetchUserDetailFromId(customerId)

    val customer = remember {
        customerViewModel.customer
    }

    val addedExercises = remember {
        mutableStateOf(listOf<ExerciseModel>())
    }

    val availableExercises = remember {
        customerViewModel.availableExercises
    }

    val hasEnteredExercises = remember {
        mutableStateOf(false)
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
    }) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "NEW TRAINING CARD FOR",
                modifier = Modifier.padding(vertical = 16.dp),
                style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.ExtraLight),
            )
            Text(
                text = customer.value?.name.toString(), modifier = Modifier.padding(top = 32.dp),
                style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
            )


            LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
                items(addedExercises.value, itemContent = { ex ->
                    Box {
                        val isOpen = remember { mutableStateOf(false) } // initial value

                        val text = remember { mutableStateOf(ex.name) } // initial value

                        val openCloseOfDropDownList: (Boolean) -> Unit = {
                            isOpen.value = it
                            hasEnteredExercises.value = true
                        }
                        val userSelectedString: (ExerciseModel) -> Unit = { exerciseModel ->
                            val current = addedExercises.value.find { it.id == ex.id }
                            val currentList = addedExercises.value.toMutableList()
                            currentList.remove(current)
                            currentList.add(exerciseModel)
                            addedExercises.value = currentList
                            text.value = exerciseModel.name
                        }

                        BasicTextField(
                            modifier = Modifier.padding(bottom = 16.dp),
                            content = text.value,
                            labelName = "Exercise",
                            onValueChange = { text.value = it })
                        DropDownList(
                            requestToOpen = isOpen.value,
                            list = availableExercises.value,
                            openCloseOfDropDownList,
                            userSelectedString
                        )
                        Spacer(
                            modifier = Modifier
                                .matchParentSize()
                                .background(Color.Transparent)
                                .padding(10.dp)
                                .clickable(
                                    onClick = { isOpen.value = true }
                                )
                        )
                    }
                })
            }

            OutlinedStyledButton(onClick = {
                val currList = addedExercises.value.toMutableList()
                currList.add(
                    ExerciseModel(
                        id = "",
                        name = "",
                        description = "",
                        duration = "",
                        type = ExerciseType.Aerobic
                    )
                )
                addedExercises.value = currList
            }, textLabel = "+")
            OutlinedStyledButton(
                onClick = {
                    customerViewModel.saveTrainingCard(addedExercises.value)
                    navHostController.navigateUp()
                },
                textLabel = "SAVE",
                enabled = customer.value != null && hasEnteredExercises.value
            )
        }

    }
}


@Composable
fun DropDownList(
    requestToOpen: Boolean = false,
    list: List<ExerciseModel>,
    request: (Boolean) -> Unit,
    selectedExercise: (ExerciseModel) -> Unit
) {
    DropdownMenu(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colors.primaryVariant
            ),
        expanded = requestToOpen,
        onDismissRequest = { request(false) },
    ) {
        list.forEach { exerciseModel ->
            DropdownMenuItem(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    request(false)
                    selectedExercise(exerciseModel)
                }
            ) {
                Text(
                    text = "${exerciseModel.name} - ${exerciseModel.duration}",
                    modifier = Modifier.wrapContentWidth(),
                    style = MaterialTheme.typography.body1.copy(MaterialTheme.colors.secondary)
                )
            }
        }
    }
}