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
                    OutlinedStyledButton(
                        modifier = Modifier.padding(start = 32.dp),
                        onClick = { navHostController.navigate("${Screens.Session.name}/${mainScreenViewModel.currentTrainingCard?.userId}/${mainScreenViewModel.currentTrainingCard?.id}") },
                        textLabel = "Start"
                    )
                }
                mainScreenViewModel.currentTrainingCard?.let {
                    WorkoutList(card = it, navHostController = navHostController)
                }
            }
        }
    }
}