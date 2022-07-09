package it.matteo.gymtastic.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun OutlinedStyledButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    textLabel: String,
    textLabelStyle: TextStyle = MaterialTheme.typography.h4,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.padding(top = 16.dp),
        enabled = enabled,
    ) {
        Text(text = textLabel, style = textLabelStyle)
    }
}