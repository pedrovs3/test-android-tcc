package br.com.pedrovieira.doetempo.screens.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import br.com.pedrovieira.doetempo.ui.theme.BlueBerry
import br.com.pedrovieira.doetempo.ui.theme.MayaBlue
import br.com.pedrovieira.doetempo.ui.theme.Shapes
import br.com.pedrovieira.doetempo.ui.theme.TuftsBlue
import br.com.pedrovieira.doetempo.ui.theme.Turquouise_700

private val DarkColorScheme = darkColorScheme(
    primary = BlueBerry,
    secondary = MayaBlue,
    tertiary = Turquouise_700,
    outline = MorningBlue
)

private val LightColorScheme = lightColorScheme(
    primary = BlueBerry,
    secondary = MayaBlue,
    tertiary = TuftsBlue,



    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

private val DarkColorPalette = darkColors(
    primary = BlueBerry,
    primaryVariant = TuftsBlue,
    secondary = MayaBlue,
    onSurface = Turquouise_700,
    background = Color(0xFF1c1c1e),
    secondaryVariant = MorningBlue
)

private val LightColorPalette = lightColors(
    primary = BlueBerry,
    primaryVariant = TuftsBlue,
    secondary = MayaBlue,
    onSurface = Turquouise_700,
    background = Color(0xFFFBFBFD),
    secondaryVariant = MorningBlue
)


@Composable
fun DoeTempoTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    androidx.compose.material.MaterialTheme(
        colors = colors,
        typography = br.com.pedrovieira.doetempo.ui.theme.Typography,
        shapes = Shapes,
        content = content,
    )
}