package it.matteo.gymtastic.presentation.auth.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import it.matteo.gymtastic.R
import it.matteo.gymtastic.Screens
import it.matteo.gymtastic.presentation.auth.viewModel.AuthViewModel

@OptIn(ExperimentalUnitApi::class)
@Composable
fun LoginScreen(navHostController: NavHostController) {
    val authViewModel: AuthViewModel = viewModel()
    AuthScreen(
        title = stringResource(id = R.string.login),
        onSubmit = { username, password ->
                   authViewModel.login(username, password)
            if (authViewModel.user.value != null) {
                navHostController.navigate(Screens.Main.name)
            }
        },
        outlinedButtonText = stringResource(
            id = R.string.login
        ), buttonTextContent = listOf(
            Pair(
                stringResource(
                    id = R.string.forgotPassword
                )
            ) {
                navHostController.navigate(Screens.ForgotPassword.name)
            },
            Pair(
                stringResource(id = R.string.register)
            ) {
                navHostController.navigate(Screens.Signup.name)
            }
        )
    )
}


