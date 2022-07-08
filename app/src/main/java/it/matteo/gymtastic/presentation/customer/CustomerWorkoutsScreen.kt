package it.matteo.gymtastic.presentation.customer

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.twotone.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import it.matteo.gymtastic.domain.user.model.GymRole
import it.matteo.gymtastic.presentation.Screens
import it.matteo.gymtastic.presentation.customer.viewModel.CustomerViewModel
import it.matteo.gymtastic.presentation.trainingCard.TrainingCardsColumn

@Composable
fun CustomerWorkoutsScreen(navHostController: NavHostController, customerId: String) {
    val customerViewModel = hiltViewModel<CustomerViewModel>()
    val trainingCards = remember {
        customerViewModel.workouts
    }

    customerViewModel.fetchUserWorkouts(customerId)

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
            onClick = { navHostController.navigate("${Screens.CustomerCreationWorkout}/${customerId}")}) {
            Icon(Icons.TwoTone.Add, contentDescription = "Add training card", modifier = Modifier.fillMaxSize(0.07f))
        }
    },
        isFloatingActionButtonDocked = true, floatingActionButtonPosition = FabPosition.End
    ) {
        TrainingCardsColumn(list = trainingCards.value, navHostController = navHostController)

    }

}
