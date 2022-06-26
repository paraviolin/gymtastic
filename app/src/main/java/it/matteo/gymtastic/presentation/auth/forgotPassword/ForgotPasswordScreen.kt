package it.matteo.gymtastic.presentation.auth.forgotPassword

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import it.matteo.gymtastic.R
import it.matteo.gymtastic.presentation.Screens
import it.matteo.gymtastic.presentation.auth.AuthScreen
import it.matteo.gymtastic.presentation.auth.viewModel.AuthViewModel

@Composable
fun ForgotPasswordScreen(navHostController: NavHostController) {
    val authViewModel: AuthViewModel = viewModel()
    AuthScreen(
        title = stringResource(id = R.string.forgotPassword),
        onSubmit = authViewModel::signUp,
        outlinedButtonText = stringResource(
            id = R.string.reset
        ), buttonTextContent = listOf(
            Pair(
                stringResource(
                    id = R.string.login
                )
            ) {
                navHostController.navigate(Screens.Login.name)
            },
            Pair(
                stringResource(id = R.string.register)
            ) {
                navHostController.navigate(Screens.Signup.name)
            }
        )
    )
}