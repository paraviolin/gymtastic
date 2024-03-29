package it.matteo.gymtastic.presentation.profile.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextFieldComponent(
    modifier: Modifier = Modifier,
    content: String,
    labelName: String,
    labelFontSize: TextUnit? = null,
    enabled: Boolean = true,
    onValueChange: ((String) -> Unit),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    shape: Shape? = null
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(modifier = Modifier.padding(16.dp)) {
        BasicTextField(
            modifier = modifier,
            enabled = enabled,
            content = content,
            onValueChange = onValueChange,
            labelName = labelName,
            labelFontSize = labelFontSize,
            keyboardController = keyboardController,
            visualTransformation = visualTransformation,
            shape = shape
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


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BasicTextField(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: String,
    onValueChange: (String) -> Unit,
    labelName: String,
    labelFontSize: TextUnit? = null,
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    shape: Shape? = null
) {
    TextField(
        modifier = modifier.padding(horizontal = 16.dp),
        enabled = enabled,
        value = content,
        onValueChange = onValueChange,
        label = {
            Text(
                text = labelName,
                style = MaterialTheme.typography.body1.copy(
                    MaterialTheme.colors.secondary,
                    fontSize = labelFontSize ?: 10.sp
                )
            )
        },
        textStyle = MaterialTheme.typography.h6.copy(fontFamily = MaterialTheme.typography.h4.fontFamily),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = { keyboardController?.hide() }),
        visualTransformation = visualTransformation,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.primaryVariant,
            cursorColor = MaterialTheme.colors.secondary
        ),
        shape = shape ?: MaterialTheme.shapes.medium
    )
}
