package it.matteo.gymtastic.presentation.common

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import it.matteo.gymtastic.presentation.Screens

@Composable
fun BottomNavigationBar(navHostController: NavHostController) {
    val items = listOf(
        Screens.Main,
        Screens.TrainingCards,
        Screens.Profile
    )
    BottomNavigation(
    ) {
        val navBackStackEntry by navHostController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.image, contentDescription = item.name) },
                label = { Text(text = item.name) },
                selectedContentColor = MaterialTheme.colors.secondary,
                unselectedContentColor = MaterialTheme.colors.secondary.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.name,
                onClick = {
                    navHostController.navigate(item.name) {
                        navHostController.graph.startDestinationRoute?.let {
                            popUpTo(it) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}