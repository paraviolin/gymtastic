package it.matteo.gymtastic.presentation.common

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import it.matteo.gymtastic.presentation.Screens

@Composable
fun BottomNavigationBar(navHostController: NavHostController, isCustomer: Boolean) {
    val items = if (isCustomer) {
        listOf(
            Screens.Main,
            Screens.Workouts,
            Screens.Profile
        )
    } else {
        listOf(Screens.Main, Screens.Exercises, Screens.Profile)
    }
    BottomNavigation {
        val navBackStackEntry by navHostController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.image, contentDescription = item.name) },
                label = { Text(text = item.name) },
                selectedContentColor = MaterialTheme.colors.secondary,
                unselectedContentColor = MaterialTheme.colors.secondary.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute?.contains(item.name) == true,
                onClick = {
                    if (item == Screens.Exercises) {
                        navHostController.navigate("${item.name}/$isCustomer") {
                            navHostController.graph.startDestinationRoute?.let {
                                popUpTo(it) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    } else {
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
                }
            )
        }
    }
}