package it.matteo.gymtastic

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import it.matteo.gymtastic.presentation.Screens
import it.matteo.gymtastic.presentation.profile.ProfileScreen
import it.matteo.gymtastic.presentation.auth.forgotPassword.ForgotPasswordScreen
import it.matteo.gymtastic.presentation.auth.login.LoginScreen
import it.matteo.gymtastic.presentation.auth.signup.SignupScreen
import it.matteo.gymtastic.presentation.auth.viewModel.AuthViewModel
import it.matteo.gymtastic.presentation.main.MainScreen
import it.matteo.gymtastic.presentation.trainingCard.TrainingCardsScreen
import it.matteo.gymtastic.presentation.workoutDetail.WorkoutDetailScreen

@Composable
fun GymNavigator(
    navHost: NavHostController,
    modifier: Modifier
) {
    val authViewModel: AuthViewModel = viewModel()
    val startDestination = if (authViewModel.isLoggedIn()) {
        Screens.Main.name
    } else {
        Screens.Login.name
    }
    NavHost(
        navController = navHost,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(Screens.Login.name) {
            LoginScreen(navHostController = navHost)
        }
        composable(Screens.ForgotPassword.name) {
            ForgotPasswordScreen(navHostController = navHost)
        }
        composable(Screens.Signup.name) {
            SignupScreen(navHostController = navHost)
        }
        composable(Screens.Main.name) {
            MainScreen(navHostController = navHost)
        }
        composable(Screens.Profile.name) {
            ProfileScreen(navHostController = navHost)
        }
        composable(Screens.TrainingCards.name) {
            TrainingCardsScreen(navHostController = navHost)
        }
        composable(
            "${Screens.WorkoutDetail.name}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) {
            WorkoutDetailScreen(navHostController = navHost, it.arguments?.getString("id"))
        }
    }
}
