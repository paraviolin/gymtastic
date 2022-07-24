package it.matteo.gymtastic.presentation.main

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import it.matteo.gymtastic.data.user.entity.UserEntity
import it.matteo.gymtastic.presentation.Screens
import it.matteo.gymtastic.presentation.auth.viewModel.LoadingState
import it.matteo.gymtastic.presentation.common.*
import it.matteo.gymtastic.presentation.main.viewModel.MainScreenViewModel

@Composable
fun MainScreen(
    navHostController: NavHostController,
    mainScreenViewModel: MainScreenViewModel = hiltViewModel()
) {

    val state by mainScreenViewModel.loadingState.collectAsState()

    mainScreenViewModel.updateUser()

    val customers by remember {
        mutableStateOf(mainScreenViewModel.customers)
    }

    val user by remember {
        mutableStateOf(mainScreenViewModel.user)
    }

    val lastTrainingCard by remember {
        mutableStateOf(mainScreenViewModel.lastTrainingCard)
    }

    if (mainScreenViewModel.isTrainer()) {
        mainScreenViewModel.fetchCustomers()
    } else if(mainScreenViewModel.user.value != null) {
        mainScreenViewModel.fetchLastTrainingCard()
    }

    Scaffold(bottomBar = {
        BottomNavigationBar(
            navHostController,
            !mainScreenViewModel.isTrainer()
        )
    }) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = CenterHorizontally
        ) {
            when (state) {
                LoadingState.LOADING -> LoaderComponent()
                LoadingState.LOADED -> {
                    Text(
                        text = "WELCOME BACK,",
                        modifier = Modifier.padding(top = 16.dp),
                        style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.ExtraLight),
                    )
                    Text(
                        text = user.value?.name ?: "",
                        modifier = Modifier.padding(top = 8.dp),
                        style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.ExtraBold),
                    )
                    val subtitle = if (mainScreenViewModel.isTrainer()) {
                        "CUSTOMERS"
                    } else {
                        "YOUR WORKOUT"
                    }
                    if (!mainScreenViewModel.isTrainer()) {
                        Row(
                            modifier = Modifier.padding(bottom = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = subtitle,
                                    modifier = Modifier.padding(top = 16.dp),
                                    style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
                                )
                                Text(text = "Created ${lastTrainingCard.value?.createdAt?.toLocalDate()}")
                            }
                            OutlinedStyledButton(
                                modifier = Modifier.padding(start = 32.dp),
                                onClick = { navHostController.navigate("${Screens.Session.name}/${lastTrainingCard.value?.userId}/${lastTrainingCard.value?.id}") },
                                textLabel = "Start"
                            )
                        }
                    }

                    if (mainScreenViewModel.isTrainer()) {
                        CustomerList(
                            customers = customers.value,
                            navHostController = navHostController
                        )
                    } else {
                        lastTrainingCard.value?.let { card ->
                            WorkoutList(card = card, navHostController = navHostController)
                        }
                    }
                }
            }
        }
    }

}