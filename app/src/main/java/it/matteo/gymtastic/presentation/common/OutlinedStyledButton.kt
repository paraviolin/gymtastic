package it.matteo.gymtastic.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun OutlinedStyledButton(
    onClick: () -> Unit,
    textLabel: String,
    textLabelStyle: TextStyle = MaterialTheme.typography.h4,
    modifier: Modifier = Modifier,
) {
    Button(

        onClick = onClick,
        border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
        shape = RoundedCornerShape(50),
        modifier = modifier.padding(top = 16.dp)
    ) {
        Text(text = textLabel, style = textLabelStyle)
    }
}