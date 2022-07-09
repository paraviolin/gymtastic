package it.matteo.gymtastic.presentation.exercise

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import it.matteo.gymtastic.domain.exercise.model.ExerciseType
import it.matteo.gymtastic.presentation.common.OutlinedStyledButton
import it.matteo.gymtastic.presentation.exercise.viewModel.ExerciseViewModel
import it.matteo.gymtastic.presentation.profile.components.BasicTextField
import it.matteo.gymtastic.presentation.profile.components.TextFieldComponent

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CreateExerciseScreen(navHostController: NavHostController) {
    val exerciseViewModel: ExerciseViewModel = hiltViewModel()

    val name = remember {
        mutableStateOf("")
    }
    val description = remember {
        mutableStateOf("")
    }
    val duration = remember {
        mutableStateOf("")
    }

    val exerciseType = remember { mutableStateOf("") } // initial value

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
                text = "NEW EXERCISE",
                modifier = Modifier.padding(vertical = 16.dp),
                style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.ExtraLight),
            )
            TextFieldComponent(
                content = name.value,
                labelName = "Name",
                onValueChange = { name.value = it })
            TextFieldComponent(
                content = description.value,
                labelName = "Description",
                onValueChange = { description.value = it })
            TextFieldComponent(
                content = duration.value,
                labelName = "Duration",
                onValueChange = { duration.value = it })

            Box {
                val isOpen = remember { mutableStateOf(false) } // initial value

                val openCloseOfDropDownList: (Boolean) -> Unit = {
                    isOpen.value = it
                }
                val userSelectedString: (String) -> Unit = { type ->
                    exerciseType.value = type
                }

                BasicTextField(
                    modifier = Modifier.padding(vertical = 16.dp),
                    content = exerciseType.value,
                    labelName = "Exercise type",
                    onValueChange = { exerciseType.value = it })
                DropDownList(
                    requestToOpen = isOpen.value,
                    list = listOf(ExerciseType.Aerobic.name, ExerciseType.Anaerobic.name),
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

            OutlinedStyledButton(
                onClick = {
                    exerciseViewModel.save(
                        name = name.value,
                        description = description.value,
                        duration = duration.value,
                        type = exerciseType.value
                    )
                    navHostController.navigateUp()
                },
                textLabel = "SAVE",
                enabled = duration.value.isNotBlank() && description.value.isNotBlank() && name.value.isNotBlank() && exerciseType.value.isNotBlank()
            )

        }
    }
}


@Composable
fun DropDownList(
    requestToOpen: Boolean = false,
    list: List<String>,
    request: (Boolean) -> Unit,
    selectedType: (String) -> Unit
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
        list.forEach { type ->
            DropdownMenuItem(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    request(false)
                    selectedType(type)
                }
            ) {
                Text(
                    text = type,
                    modifier = Modifier.wrapContentWidth(),
                    style = MaterialTheme.typography.body1.copy(MaterialTheme.colors.secondary)
                )
            }
        }
    }
}