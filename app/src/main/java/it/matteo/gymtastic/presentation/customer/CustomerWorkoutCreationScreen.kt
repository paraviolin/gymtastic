package it.matteo.gymtastic.presentation.customer

import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import it.matteo.gymtastic.domain.exercise.model.ExerciseModel
import it.matteo.gymtastic.presentation.common.OutlinedStyledButton
import it.matteo.gymtastic.presentation.customer.viewModel.CustomerViewModel

@Composable
fun CustomerWorkoutCreationScreen(navHostController: NavHostController, customerId: String) {
    val customerViewModel: CustomerViewModel = hiltViewModel()
    customerViewModel.fetchAllAvailableExercises()
    customerViewModel.fetchUserDetailFromId(customerId)

    val customer = remember {
        customerViewModel.customer
    }

    val addedExercises = remember {
        mutableListOf<ExerciseModel>()
    }

    val availableExercises = remember {
        customerViewModel.availableExercises
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

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(availableExercises.value, itemContent = { exercise ->
                    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                        val checked = remember {
                            mutableStateOf(addedExercises.contains(exercise))
                        }
                        Checkbox(checked = checked.value, onCheckedChange = {
                            addedExercises.add(exercise)
                            checked.value = !checked.value
                        }, colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colors.secondary,
                            uncheckedColor = MaterialTheme.colors.secondaryVariant
                        ))
                        Text(text = exercise.name)
                    }
                })
            }
            OutlinedStyledButton(onClick = { /*TODO*/ }, textLabel = "SAVE")
        }

    }
}

@Composable
fun DropdownListMenu(list: List<ExerciseModel>, onSelected: (exercise: ExerciseModel) -> Unit) {
    val expanded = remember {
        mutableStateOf(false)
    }

    val selectedIndex = remember {
        mutableStateOf(0)
    }

    DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false },
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Red)
    ) {
        list.forEachIndexed { index, exerciseModel ->
            DropdownMenuItem(onClick = {
                expanded.value = false
                selectedIndex.value = index
                onSelected(exerciseModel)
            }) {
                Text(text = "${exerciseModel.name} - ${exerciseModel.duration}")
            }
        }
    }
}