package br.com.pedrovieira.doetempo.ui.theme

import androidx.annotation.ColorRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = BlueBerry,
    primaryVariant = TuftsBlue,
    secondary = MayaBlue,
    onSurface = Turquouise_700,
    background = Color(0xFF1c1c1e)
)

private val LightColorPalette = lightColors(
    primary = BlueBerry,
    primaryVariant = TuftsBlue,
    secondary = MayaBlue,
    onSurface = Turquouise_700,
    background = Color(0xFFFBFBFD)

    /* Other default colors to override
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun DoeTempoTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}