package it.matteo.gymtastic.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import it.matteo.gymtastic.presentation.auth.forgotPassword.ForgotPasswordScreen
import it.matteo.gymtastic.presentation.auth.login.LoginScreen
import it.matteo.gymtastic.presentation.auth.signup.SignupScreen

enum class Screens(
    val image: ImageVector,
) {
    Login(
        image = Icons.Filled.Lock
    ),
    Signup(
        image = Icons.Filled.Lock
    ),
    ForgotPassword(
        image = Icons.Filled.Edit
    ),
    Main(
        image = Icons.Filled.Star
    ),
    TrainingCards(
        image = Icons.Default.List
    ),
    Profile(
        image = Icons.Default.Person
    ),
    WorkoutDetail(
        image = Icons.Filled.Build
    )
}