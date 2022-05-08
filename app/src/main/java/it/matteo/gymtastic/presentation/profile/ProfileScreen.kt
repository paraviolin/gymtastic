package it.matteo.gymtastic.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import it.matteo.gymtastic.presentation.auth.viewModel.AuthViewModel
import it.matteo.gymtastic.presentation.common.BottomNavigationBar

@Composable
fun ProfileScreen(navHostController: NavHostController) {
    val authViewModel: AuthViewModel = viewModel()

    Scaffold(bottomBar = { BottomNavigationBar(navHostController = navHostController) }) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Welcome ${authViewModel.user.value?.email}")
        }
    }
}