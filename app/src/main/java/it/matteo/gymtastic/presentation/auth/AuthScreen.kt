package it.matteo.gymtastic.presentation.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import it.matteo.gymtastic.R
import it.matteo.gymtastic.presentation.Screens
import it.matteo.gymtastic.presentation.common.OutlinedStyledButton
import it.matteo.gymtastic.presentation.profile.components.TextFieldComponent

@Composable
fun AuthScreen(
    navHostController: NavHostController,
    title: String,
    onSubmit: (String, String) -> Unit,
    outlinedButtonText: String,
    buttonTextContent: List<Pair<String, () -> Unit>>,
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
        )

        Image(
            painter = painterResource(id = R.drawable.gym_background),
            contentDescription = "gym background",
            modifier = Modifier
                .height(LocalConfiguration.current.screenHeightDp.dp * 3 / 7)
                .fillMaxWidth()
        )


        Divider(
            color = Color.DarkGray,
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth()
                .width(1.dp)
        )
        TextFieldComponent(
            content = username,
            onValueChange = { username = it },
            labelName = stringResource(id = R.string.username),
            enabled = true
        )
        TextFieldComponent(
            content = password,
            onValueChange = {
                password = it
            },
            visualTransformation = PasswordVisualTransformation(),
            labelName = stringResource(id = R.string.password),
            enabled = true
        )
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 16.dp)
        ) {

            Button(
                onClick = {
                    onSubmit(username, password)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary,
                    contentColor = MaterialTheme.colors.primary
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = outlinedButtonText, style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.primary))
            }
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.Top
            ) {

/*                OutlinedStyledButton(
                    modifier = Modifier.padding(end = 16.dp),
                    onClick = {
                        navHostController.navigate(Screens.Login.name)
                    },
                    textLabel = stringResource(id = R.string.login),
                    textLabelStyle = MaterialTheme.typography.body1.copy(MaterialTheme.colors.secondary),
                )

                OutlinedStyledButton(
                    onClick = { buttonTextContent[1].second() },
                    textLabel = buttonTextContent[1].first,
                    textLabelStyle = MaterialTheme.typography.body1.copy(MaterialTheme.colors.secondary)
                )*/
            }
        }
    }
}
