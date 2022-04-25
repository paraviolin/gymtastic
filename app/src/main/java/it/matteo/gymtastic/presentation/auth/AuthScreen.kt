package it.matteo.gymtastic.presentation.auth.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import it.matteo.gymtastic.R

@Composable
fun AuthScreen(
    title: String,
    onSubmit: (String, String) -> Unit,
    outlinedButtonText: String,
    buttonTextContent: List<Pair<String, () -> Unit>>
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h3,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp)
            )
        }
        TextField(
            value = username,
            onValueChange = {
                username = it
            },
            label = {
                Text(stringResource(id = R.string.username))
            },
            modifier = Modifier.padding(16.dp)
        )
        TextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text(stringResource(id = R.string.password))
            },
            modifier = Modifier.padding(16.dp)
        )
        OutlinedButton(
            onClick = {
                onSubmit(username, password)
            }) {
            Text(text = outlinedButtonText)
        }
        TextButton(onClick = {
            buttonTextContent.first().second()
        }) {
            Text(text = buttonTextContent.first().first)
        }
        TextButton(onClick = { buttonTextContent[1].second() }) {
            Text(text = buttonTextContent[1].first)
        }
    }
}
