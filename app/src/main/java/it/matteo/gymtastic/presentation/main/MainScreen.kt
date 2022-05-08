package it.matteo.gymtastic.presentation.main

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import it.matteo.gymtastic.presentation.common.BottomNavigationBar

@Composable
fun MainScreen(navHostController: NavHostController) {
    Scaffold(bottomBar = { BottomNavigationBar(navHostController) }) {
        Text(text = "Welcome!")
    }

}