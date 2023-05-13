package br.com.pedrovieira.doetempo.helpers

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

fun randomColor(): Color {
    val red = 200 + Random.nextInt(56)
    val green = 200 + Random.nextInt(56)
    val blue = 200 + Random.nextInt(56)
    return Color(red, green, blue)
}