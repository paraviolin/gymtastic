package it.matteo.gymtastic.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import it.matteo.gymtastic.presentation.auth.viewModel.LoadingState
import it.matteo.gymtastic.presentation.common.BottomNavigationBar
import it.matteo.gymtastic.presentation.common.LoaderComponent
import it.matteo.gymtastic.presentation.common.WorkoutList
import it.matteo.gymtastic.presentation.main.viewModel.MainScreenViewModel

@Composable
fun MainScreen(
    navHostController: NavHostController,
    mainScreenViewModel: MainScreenViewModel = hiltViewModel()
) {

    val state by mainScreenViewModel.loadingState.collectAsState()
    mainScreenViewModel.getTrainingCards()

    Scaffold(bottomBar = { BottomNavigationBar(navHostController) }) {
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
                        text = mainScreenViewModel.user?.name ?: "",
                        modifier = Modifier.padding(top = 16.dp),
                        style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.ExtraBold),
                    )
                    Text(
                        text = "YOUR TRAINING CARD",
                        modifier = Modifier.padding(top = 32.dp),
                        style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
                    )

                    if (mainScreenViewModel.trainingCards.isNotEmpty()) {
                        val lastTrainingCardModel = mainScreenViewModel.trainingCards.first()
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