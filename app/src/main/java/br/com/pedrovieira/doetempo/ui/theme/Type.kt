package br.com.pedrovieira.doetempo.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import br.com.pedrovieira.doetempo.R

val fonts = FontFamily(
    Font(R.font.roboto_bold, weight = FontWeight.Bold),
    Font(R.font.roboto_medium, weight = FontWeight.Medium),
    Font(R.font.roboto_regular, weight = FontWeight.Normal),
    Font(R.font.roboto_thin, weight = FontWeight.Thin),
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    button = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
)
    /* Other default text styles to override
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)