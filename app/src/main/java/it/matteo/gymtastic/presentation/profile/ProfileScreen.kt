package it.matteo.gymtastic.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import it.matteo.gymtastic.R
import it.matteo.gymtastic.presentation.Screens
import it.matteo.gymtastic.presentation.auth.viewModel.AuthViewModel
import it.matteo.gymtastic.presentation.common.BottomNavigationBar
import it.matteo.gymtastic.presentation.common.OutlinedStyledButton
import it.matteo.gymtastic.presentation.profile.components.TextFieldComponent
import it.matteo.gymtastic.presentation.profile.viewModel.ProfileViewModel

@Composable
fun ProfileScreen(navHostController: NavHostController) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val profileViewModel: ProfileViewModel = hiltViewModel()

    val switchState = false
    val editEnabled = remember {
        mutableStateOf(switchState)
    }

    val nameField = remember {
        mutableStateOf(profileViewModel.userProfile?.name)
    }
    val surnameField = remember {
        mutableStateOf(profileViewModel.userProfile?.surname)
    }

    authViewModel.user.value?.let {
        profileViewModel.updateUserProfile(it.email!!)
        nameField.value = profileViewModel.userProfile?.name
        surnameField.value = profileViewModel.userProfile?.surname
    }

    Scaffold(bottomBar = { BottomNavigationBar(navHostController = navHostController) }) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.your_profile),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
            )

            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .height(60.dp)
            ) {
                Divider(
                    color = Color.DarkGray,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp),
                )
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(text = stringResource(R.string.joined), color = Color.DarkGray)
                    Text(text = profileViewModel.userProfile?.createdAt?.toDate().toString())
                }
            }

            Divider(
                color = Color.DarkGray,
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .width(1.dp)
            )
            TextFieldComponent(
                content = "${profileViewModel.userProfile?.id}",
                labelName = stringResource(R.string.id),
                enabled = false,
                onValueChange = {}
            )

            TextFieldComponent(
                content = "${profileViewModel.userProfile?.email}",
                labelName = stringResource(R.string.email),
                enabled = false,
                onValueChange = {}
            )

            TextFieldComponent(
                content = nameField.value.toString(),
                labelName = stringResource(R.string.name),
                enabled = editEnabled.value,
                onValueChange = {
                    nameField.value = it
                    profileViewModel.updateName(it)
                }
            )

            TextFieldComponent(
                content = "${profileViewModel.userProfile?.surname}",
                labelName = stringResource(R.string.surname),
                enabled = editEnabled.value,
                onValueChange = {
                    surnameField.value = it
                    profileViewModel.updateSurname(it)
                }
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = stringResource(R.string.edit_profile),
                    style = MaterialTheme.typography.body1.copy(MaterialTheme.colors.secondary)
                )

                Switch(
                    checked = editEnabled.value, onCheckedChange = {
                        editEnabled.value = it
                    }, colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colors.secondary,
                        uncheckedThumbColor = Color.LightGray,
                        checkedTrackColor = MaterialTheme.colors.secondaryVariant,
                        uncheckedTrackColor = Color.LightGray
                    )
                )
            }

            OutlinedStyledButton(onClick = {
                authViewModel.logout()
                navHostController.navigate(Screens.Login.name) {
                    navHostController.graph.startDestinationRoute?.let {
                        popUpTo(it) {
                            saveState = false
                        }
                    }
                    launchSingleTop = true
                    restoreState = false
                }
            }, textLabel = stringResource(R.string.logout))

        }
    }
}