package it.matteo.gymtastic.presentation.trainingCard

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import it.matteo.gymtastic.presentation.auth.viewModel.LoadingState
import it.matteo.gymtastic.presentation.common.BottomNavigationBar
import it.matteo.gymtastic.presentation.common.LoaderComponent
import it.matteo.gymtastic.presentation.main.viewModel.MainScreenViewModel

@Composable
fun TrainingCardsScreen(
    navHostController: NavHostController,
    mainScreenViewModel: MainScreenViewModel = hiltViewModel()
) {
    val state by mainScreenViewModel.loadingState.collectAsState()

    if (!mainScreenViewModel.hasTrainingCards && state.status != LoadingState.Status.SUCCESS) {
        mainScreenViewModel.fetchTrainingCards()
    }

    when (state) {
        LoadingState.LOADING -> LoaderComponent()
        LoadingState.LOADED -> Scaffold(bottomBar = { BottomNavigationBar(navHostController = navHostController) }) {
            TrainingCardsColumn(
                list = mainScreenViewModel.trainingCards.value,
                navHostController = navHostController
            )
        }
    }
}
