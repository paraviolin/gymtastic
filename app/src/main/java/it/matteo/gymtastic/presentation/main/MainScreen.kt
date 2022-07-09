package it.matteo.gymtastic.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import it.matteo.gymtastic.data.user.entity.UserEntity
import it.matteo.gymtastic.presentation.auth.viewModel.LoadingState
import it.matteo.gymtastic.presentation.common.BottomNavigationBar
import it.matteo.gymtastic.presentation.common.CustomerList
import it.matteo.gymtastic.presentation.common.LoaderComponent
import it.matteo.gymtastic.presentation.common.WorkoutList
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

    val trainingCards by remember {
        mutableStateOf(mainScreenViewModel.trainingCards)
    }

    if (mainScreenViewModel.isTrainer()) {
        mainScreenViewModel.fetchCustomers()
    } else {
        mainScreenViewModel.fetchTrainingCards()
    }

    Scaffold(bottomBar = { BottomNavigationBar(navHostController, !mainScreenViewModel.isTrainer()) }) {
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
                        modifier = Modifier.padding(top = 16.dp),
                        style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.ExtraBold),
                    )
                    val subtitle = if(mainScreenViewModel.isTrainer()) {
                        "CUSTOMERS"
                    } else {
                        "YOUR TRAINING CARD"
                        }
                    Text(
                        text = subtitle,
                        modifier = Modifier.padding(top = 32.dp),
                        style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
                    )

                    if (mainScreenViewModel.isTrainer()) {
                      CustomerList(customers = customers.value, navHostController = navHostController)
                    } else if (trainingCards.value.isNotEmpty()) {
                        val lastTrainingCardModel = trainingCards.value.first()
                        lastTrainingCardModel.let { card ->
                            Text(text = "Created ${card.createdAt.toLocalDate()}")
                            WorkoutList(card = card, navHostController = navHostController)
                        }
                    }
                }
            }
        }
    }

}