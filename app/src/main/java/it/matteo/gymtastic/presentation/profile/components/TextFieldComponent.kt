package it.matteo.gymtastic.presentation.profile.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextFieldComponent(content: String, labelName: String, enabled: Boolean, onValueChange: ((String) -> Unit)) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(modifier = Modifier.padding(16.dp)) {
        TextField(
            modifier = Modifier.padding(horizontal = 16.dp),
            enabled = enabled,
            value = content,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = labelName,
                    style = MaterialTheme.typography.body1.copy(MaterialTheme.colors.secondary)
                )
            },
            textStyle = MaterialTheme.typography.h6.copy(fontFamily = MaterialTheme.typography.h4.fontFamily),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {keyboardController?.hide()})
        )
    }

    Divider(
        color = Color.DarkGray,
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .width(1.dp)
    )

}
