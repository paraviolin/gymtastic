package it.matteo.gymtastic.presentation.auth.signup

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import it.matteo.gymtastic.R
import it.matteo.gymtastic.Screens
import it.matteo.gymtastic.presentation.auth.login.AuthScreen
import it.matteo.gymtastic.presentation.auth.viewModel.AuthViewModel

@Composable
fun SignupScreen(navHostController: NavHostController) {
    val authViewModel: AuthViewModel = viewModel()
    AuthScreen(
        title = stringResource(id = R.string.register),
        onSubmit = authViewModel::signUp,
        outlinedButtonText = stringResource(
            id = R.string.register
        ), buttonTextContent = listOf(
            Pair(
                stringResource(
                    id = R.string.forgotPassword
                )
            ) {
                navHostController.navigate(Screens.ForgotPassword.name)
            },
            Pair(
                stringResource(id = R.string.login)
            ) {
                navHostController.navigate(Screens.Login.name)
            }
        )
    )
}