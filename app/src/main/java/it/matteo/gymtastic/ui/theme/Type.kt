package it.matteo.gymtastic.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.Typeface
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.matteo.gymtastic.R

// Set of Material typography styles to start with
val bebasNeue = FontFamily(Font(R.font.bebas_neue_regular))

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily(Font(R.font.bebas_neue_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    h4 = TextStyle(
        fontFamily = bebasNeue,
        fontSize = 40.sp,
        color = Yellow200
    ),
    h5 = TextStyle(
        fontFamily = bebasNeue,
        fontSize = 24.sp,
    ),
    body2 = TextStyle(
        fontFamily = FontFamily(Font(R.font.bebas_neue_regular)),
        fontWeight = FontWeight.ExtraBold,
        fontSize = 24.sp,
        color = Color.Black
    )
)