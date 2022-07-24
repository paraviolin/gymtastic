package it.matteo.gymtastic.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun OutlinedStyledButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    textLabel: String,
    textLabelStyle: TextStyle = MaterialTheme.typography.h4,
    enabled: Boolean = true,
    color: Color = MaterialTheme.colors.secondary
) {
    Button(
        onClick = onClick,
        border = BorderStroke(1.dp, color),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.padding(top = 16.dp),
        enabled = enabled,
    ) {
        Text(text = textLabel, style = textLabelStyle, color = color)
    }
}