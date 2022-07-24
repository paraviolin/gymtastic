package it.matteo.gymtastic.presentation.trainingCard

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import it.matteo.gymtastic.presentation.Screens
import it.matteo.gymtastic.presentation.auth.viewModel.LoadingState
import it.matteo.gymtastic.presentation.common.LoaderComponent
import it.matteo.gymtastic.presentation.common.OutlinedStyledButton
import it.matteo.gymtastic.presentation.common.WorkoutList
import it.matteo.gymtastic.presentation.trainingCard.viewModel.DetailCardViewModel

@Composable
fun DetailCard(navHostController: NavHostController, cardId: String?) {

    val detailViewModel: DetailCardViewModel = hiltViewModel()
    val state by detailViewModel.loadingState.collectAsState()
    detailViewModel.updateUser()

    if (!detailViewModel.hasTrainingCard && state.status != LoadingState.Status.SUCCESS) {
        if (cardId != null) {
            detailViewModel.getTrainingCard(cardId)
        }
    }

    when (state) {
        LoadingState.LOADING -> LoaderComponent()
        LoadingState.LOADED -> Scaffold(topBar = {
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
                Row(
                    modifier = Modifier.padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "EXERCISES",
                        modifier = Modifier.padding(top = 16.dp),
                        style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.ExtraLight),
                    )
                    if(!detailViewModel.isTrainer()) {
                        OutlinedStyledButton(
                            modifier = Modifier.padding(start = 32.dp),
                            onClick = { navHostController.navigate("${Screens.Session.name}/${detailViewModel.currentTrainingCard?.userId}/${detailViewModel.currentTrainingCard?.id}") },
                            textLabel = "Start"
                        )
                    }
                }
                detailViewModel.currentTrainingCard?.let {
                    WorkoutList(card = it, navHostController = navHostController)
                }
            }
        }
    }
}