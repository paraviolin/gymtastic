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
import it.matteo.gymtastic.presentation.trainingCard.viewModel.TrainingCardViewModel

@Composable
fun TrainingCardsScreen(
    navHostController: NavHostController,
    trainingCardViewModel: TrainingCardViewModel = hiltViewModel()
) {
    val state by trainingCardViewModel.loadingState.collectAsState()

    if (!trainingCardViewModel.hasTrainingCards && state.status != LoadingState.Status.SUCCESS) {
        trainingCardViewModel.fetchTrainingCards()
    }

    when (state) {
        LoadingState.LOADING -> LoaderComponent()
        LoadingState.LOADED -> Scaffold(bottomBar = {
            BottomNavigationBar(
                navHostController = navHostController,
                !trainingCardViewModel.isTrainer()
            )
        }) {
            TrainingCardsColumn(
                list = trainingCardViewModel.trainingCards.value.sortedBy { it.createdAt }
                    .reversed(),
                navHostController = navHostController
            )
        }
    }
}
