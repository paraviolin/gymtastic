package it.matteo.gymtastic.presentation.main

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import it.matteo.gymtastic.presentation.common.BottomNavigationBar
import it.matteo.gymtastic.presentation.main.viewModel.MainScreenViewModel

@Composable
fun MainScreen(navHostController: NavHostController, mainScreenViewModel: MainScreenViewModel = hiltViewModel()) {

    Scaffold(bottomBar = { BottomNavigationBar(navHostController) }) {
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