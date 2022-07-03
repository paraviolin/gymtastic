package it.matteo.gymtastic.presentation.auth.login

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import it.matteo.gymtastic.R
import it.matteo.gymtastic.presentation.Screens
import it.matteo.gymtastic.presentation.auth.AuthScreen
import it.matteo.gymtastic.presentation.auth.viewModel.AuthViewModel
import it.matteo.gymtastic.presentation.auth.viewModel.LoadingState
import it.matteo.gymtastic.presentation.common.LoaderComponent

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun LoginScreen(navHostController: NavHostController) {
    val authViewModel: AuthViewModel = viewModel()
    val state by authViewModel.loadingState.collectAsState()
    AuthScreen(
        title = stringResource(id = R.string.login),
        onSubmit = { username, password ->
            authViewModel.login(username, password)
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
    when {
        state == LoadingState.LOADING -> LoaderComponent()
        state == LoadingState.LOGGED_IN -> {
            LocalFocusManager.current.clearFocus()
            navHostController.navigate(Screens.Main.name)
            authViewModel.loadScreen()
        }
        state.msg?.isNotEmpty() == true -> {
            Toast.makeText(
                LocalContext.current,
                state.msg,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}


