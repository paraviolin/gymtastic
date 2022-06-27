package it.matteo.gymtastic.presentation.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import it.matteo.gymtastic.presentation.auth.viewModel.LoadingState
import it.matteo.gymtastic.presentation.common.BottomNavigationBar
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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally
        ) {

            Text(
                text = "YOUR TRAINING CARD",
               modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colors.secondaryVariant
            )

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
                    mainScreenViewModel.trainingCards.forEach {
                        LazyColumn {
                            items(it.exercises, itemContent = { item ->
                                Card(
                                    modifier = Modifier.padding(horizontal = 55.dp, vertical = 8.dp),
                                    elevation = 30.dp,
                                    shape = RoundedCornerShape(15.dp),
                                    backgroundColor = MaterialTheme.colors.secondary
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(30.dp)
                                            .fillMaxSize(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = CenterHorizontally
                                    ) {
                                        Text(text = item.name, style = MaterialTheme.typography.body2)
                                        Text(text = item.description)
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