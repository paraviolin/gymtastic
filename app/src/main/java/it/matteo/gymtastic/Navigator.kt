package it.matteo.gymtastic

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import it.matteo.gymtastic.presentation.auth.forgotPassword.ForgotPasswordScreen
import it.matteo.gymtastic.presentation.auth.login.LoginScreen
import it.matteo.gymtastic.presentation.auth.signup.SignupScreen
import it.matteo.gymtastic.presentation.main.MainScreen

@Composable
fun GymNavigator(
    navHost: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navHost,
        startDestination = Screens.Login.name,
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
            MainScreen()
        }
    }
}
