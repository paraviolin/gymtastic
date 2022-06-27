package it.matteo.gymtastic.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.Typeface
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
    h5 = TextStyle(
        fontFamily = bebasNeue,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp
    ),
    body2 = TextStyle(
        fontFamily = FontFamily(Font(R.font.bebas_neue_regular)),
        fontWeight = FontWeight.ExtraBold,
        fontSize = 24.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)