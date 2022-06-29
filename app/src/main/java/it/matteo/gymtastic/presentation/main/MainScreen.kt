package it.matteo.gymtastic.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import it.matteo.gymtastic.presentation.main.viewModel.MainScreenViewModel

@OptIn(ExperimentalMaterialApi::class)
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
                LoadingState.LOADING -> {
                    Column(
                        Modifier
                            .padding(55.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                }
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
                    mainScreenViewModel.trainingCards.forEach {
                        LazyColumn(modifier = Modifier.padding(bottom = 56.dp)) {
                            items(it.exercises, itemContent = { item ->
                                Card(
                                    modifier = Modifier.padding(
                                        horizontal = 55.dp,
                                        vertical = 8.dp
                                    ),
                                    elevation = 30.dp,
                                    shape = RoundedCornerShape(15.dp),
                                    backgroundColor = MaterialTheme.colors.secondary,
                                    onClick = {
                                        navHostController.navigate("${Screens.WorkoutDetail.name}/${item.id}") {
                                            navHostController.graph.startDestinationRoute?.let {
                                                popUpTo(it) {
                                                    saveState = true
                                                }
                                            }
                                            launchSingleTop = true
                                        }
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
                                            text = item.name,
                                            style = MaterialTheme.typography.body2
                                        )
                                        Text(text = item.duration)
                                    }
                                }

                            })
                        }
                    }

                }
            }
        }
    }

}