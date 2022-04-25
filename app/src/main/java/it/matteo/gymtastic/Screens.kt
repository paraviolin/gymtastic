package it.matteo.gymtastic

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
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
    )
}