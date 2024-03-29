package it.matteo.gymtastic

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import it.matteo.gymtastic.presentation.Screens
import it.matteo.gymtastic.presentation.auth.forgotPassword.ForgotPasswordScreen
import it.matteo.gymtastic.presentation.auth.login.LoginScreen
import it.matteo.gymtastic.presentation.auth.signup.SignupScreen
import it.matteo.gymtastic.presentation.auth.viewModel.AuthViewModel
import it.matteo.gymtastic.presentation.customer.CustomerDetailScreen
import it.matteo.gymtastic.presentation.customer.CustomerWorkoutCreationScreen
import it.matteo.gymtastic.presentation.customer.CustomerWorkoutsScreen
import it.matteo.gymtastic.presentation.exercise.CreateExerciseScreen
import it.matteo.gymtastic.presentation.exercise.ExercisesScreen
import it.matteo.gymtastic.presentation.main.MainScreen
import it.matteo.gymtastic.presentation.profile.ProfileScreen
import it.matteo.gymtastic.presentation.session.SessionScreen
import it.matteo.gymtastic.presentation.trainingCard.DetailCard
import it.matteo.gymtastic.presentation.trainingCard.TrainingCardsScreen
import it.matteo.gymtastic.presentation.workoutDetail.WorkoutDetailScreen

@Composable
fun GymNavigator(
    navHost: NavHostController,
    modifier: Modifier
) {
    val authViewModel: AuthViewModel = hiltViewModel()
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
        // AUTH
        composable(Screens.Login.name) {
            LoginScreen(navHostController = navHost)
        }
        composable(Screens.ForgotPassword.name) {
            ForgotPasswordScreen(navHostController = navHost)
        }
        composable(Screens.Signup.name) {
            SignupScreen(navHostController = navHost)
        }

        // MAIN SCREENS
        composable(Screens.Main.name) {
            MainScreen(navHostController = navHost)
        }
        composable(Screens.Profile.name) {
            ProfileScreen(navHostController = navHost)
        }

        // CUSTOMER
        composable(Screens.Workouts.name) {
            TrainingCardsScreen(navHostController = navHost)
        }
        composable(
            "${Screens.WorkoutDetail.name}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) {
            WorkoutDetailScreen(navHostController = navHost, it.arguments?.getString("id"))
        }
        composable(
            "${Screens.TrainingCard.name}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) {
            DetailCard(navHostController = navHost, cardId = it.arguments?.getString("id"))
        }
        composable(
            "${Screens.Session.name}/{userId}/{trainingId}",
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType },
                navArgument("trainingId") { type = NavType.StringType })
        ) {
            SessionScreen(
                navHostController = navHost,
                userId = it.arguments?.getString("userId").toString(),
                trainingId = it.arguments?.getString("trainingId").toString()
            )
        }

        // TRAINER
        composable(
            "${Screens.CustomerDetail.name}/{email}",
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) {
            CustomerDetailScreen(navHostController = navHost, it.arguments?.getString("email"))
        }
        composable(
            "${Screens.CustomerWorkouts.name}/{customerId}",
            arguments = listOf(navArgument("customerId") { type = NavType.StringType })
        ) {
            CustomerWorkoutsScreen(
                navHostController = navHost,
                it.arguments?.getString("customerId").toString()
            )
        }
        composable(
            "${Screens.CustomerCreationWorkout.name}/{customerId}",
            arguments = listOf(navArgument("customerId") { type = NavType.StringType })
        ) {
            CustomerWorkoutCreationScreen(
                navHostController = navHost,
                it.arguments?.getString("customerId").toString()
            )
        }
        composable(
            "${Screens.Exercises.name}/{isCustomer}",
            arguments = listOf(navArgument("isCustomer") { type = NavType.BoolType })
        ) {
            ExercisesScreen(navHostController = navHost, it.arguments?.getBoolean("isCustomer"))
        }
        composable(Screens.CreateExercise.name) {
            CreateExerciseScreen(navHostController = navHost)
        }
    }
}
