package it.matteo.gymtastic.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
            Text(
                text = "YOUR PROFILE",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
            )


            Row(modifier = Modifier.padding(16.dp)) {
                Text(text = "NAME")
                TextField(modifier = Modifier.padding(horizontal = 16.dp), enabled = false, value = "${authViewModel.user.value?.displayName}", onValueChange = {})
            }

            Row(modifier = Modifier.padding(16.dp)) {
                Text(text = "EMAIL")
                TextField(modifier = Modifier.padding(horizontal = 16.dp), enabled = false, value = "${authViewModel.user.value?.email}", onValueChange = {})
            }

            // Todo enabled with a switch, value fetch from viewmodel

        }
    }
}