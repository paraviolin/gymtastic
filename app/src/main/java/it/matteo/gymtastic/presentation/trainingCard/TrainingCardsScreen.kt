package it.matteo.gymtastic.presentation.trainingCard

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import it.matteo.gymtastic.presentation.Screens
import it.matteo.gymtastic.presentation.auth.viewModel.LoadingState
import it.matteo.gymtastic.presentation.common.BottomNavigationBar
import it.matteo.gymtastic.presentation.common.LoaderComponent
import it.matteo.gymtastic.presentation.main.viewModel.MainScreenViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TrainingCardsScreen(
    navHostController: NavHostController,
    mainScreenViewModel: MainScreenViewModel = hiltViewModel()
) {
    val state by mainScreenViewModel.loadingState.collectAsState()

    if (!mainScreenViewModel.hasTrainingCards && state.status != LoadingState.Status.SUCCESS) {
        mainScreenViewModel.getTrainingCards()
    }

    when (state) {
        LoadingState.LOADING -> LoaderComponent()
        LoadingState.LOADED -> Scaffold(bottomBar = { BottomNavigationBar(navHostController = navHostController) }) {
            Column( modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = CenterHorizontally){
                Text(
                    text = "TRAINING CARD HISTORY",
                    modifier = Modifier.padding(vertical = 16.dp),
                    style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.ExtraLight),
                )
                Text(
                    text = "Tap to see the details",
                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = CenterHorizontally
                ) {
                    items(mainScreenViewModel.trainingCards, itemContent = { card ->
                        Card(
                            modifier = Modifier.padding(
                                horizontal = 55.dp,
                                vertical = 8.dp
                            ),
                            elevation = 30.dp,
                            shape = MaterialTheme.shapes.medium,
                            backgroundColor = MaterialTheme.colors.secondary,
                            onClick = {
                                navHostController.navigate("${Screens.TrainingCard.name}/${card.id}")
                            }
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(30.dp)
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = CenterHorizontally
                            ) {
                                Text(
                                    text = card.id,
                                    style = MaterialTheme.typography.body2
                                )
                                Text(text = "Created the ${card.createdAt.toLocalDate()}")
                            }
                        }
                    })
                }
            }
        }
    }
}
