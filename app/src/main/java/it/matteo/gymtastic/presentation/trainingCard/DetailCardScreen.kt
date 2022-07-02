package it.matteo.gymtastic.presentation.trainingCard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import it.matteo.gymtastic.presentation.auth.viewModel.LoadingState
import it.matteo.gymtastic.presentation.common.WorkoutList
import it.matteo.gymtastic.presentation.main.viewModel.MainScreenViewModel

@Composable
fun DetailCard(navHostController: NavHostController, cardId: String?) {

    val mainScreenViewModel: MainScreenViewModel = hiltViewModel()
    val state by mainScreenViewModel.loadingState.collectAsState()

    if (!mainScreenViewModel.hasTrainingCard && state.status != LoadingState.Status.SUCCESS) {
        if (cardId != null) {
            mainScreenViewModel.getTrainingCard(cardId)
        }
    }

    when (state) {
        LoadingState.LOADING -> {
            Column(
                Modifier
                    .padding(55.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
        LoadingState.LOADED -> {

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
                mainScreenViewModel.currentTrainingCard?.let {
                    WorkoutList(card = it, navHostController = navHostController) }
                }

        }
    }
}