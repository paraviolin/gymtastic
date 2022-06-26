package it.matteo.gymtastic.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    Scaffold(bottomBar = { BottomNavigationBar(navHostController) }) {
        when {
            state == LoadingState.LOADING -> {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }
            state == LoadingState.LOADED -> {
                mainScreenViewModel.trainingCards.forEach {
                    Text(text = it.id)
                    LazyColumn() {
                        items(it.exercises, itemContent = { item ->
                            Text(text = item.name)
                        })
                    }
                }

            }
        }

    }

}