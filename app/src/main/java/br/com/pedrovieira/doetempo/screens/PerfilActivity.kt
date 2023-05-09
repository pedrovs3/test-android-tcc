package br.com.pedrovieira.doetempo.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.pedrovieira.doetempo.screens.ui.theme.DoeTempoTheme

@Composable
fun PerfilActivity(modifier: Modifier = Modifier) {
    Text(
        text = "Hello userActivity!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    DoeTempoTheme {
        PerfilActivity()
    }
}