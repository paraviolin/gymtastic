package it.matteo.gymtastic.presentation.common

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import it.matteo.gymtastic.Screens

@Composable
fun BottomNavigationBar(navHostController: NavHostController) {
    val items = listOf(
        Screens.Main,
        Screens.WorkoutDetail,
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
                selectedContentColor = White,
                unselectedContentColor = White.copy(0.4f),
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