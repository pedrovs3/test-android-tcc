package br.com.pedrovieira.doetempo.screens

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import br.com.pedrovieira.doetempo.ui.theme.DoeTempoTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoeTempoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    val context = LocalContext.current
    val token = context.getSharedPreferences("app_data", MODE_PRIVATE).getString("token", "")

    Text(text = "Hello $token!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    DoeTempoTheme {
        Greeting()
    }
}