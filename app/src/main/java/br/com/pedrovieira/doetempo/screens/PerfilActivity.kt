package br.com.pedrovieira.doetempo.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import br.com.pedrovieira.doetempo.datastore.DataStoreAppData
import br.com.pedrovieira.doetempo.screens.ui.theme.DoeTempoTheme

class PerfilActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val dataStore = DataStoreAppData(this)
            val idUser = dataStore.getIdUser.collectAsState(initial = "").value.toString()
            DoeTempoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PerfilScreen(idUser)
                }
            }
        }
    }
}


@Composable
fun PerfilScreen(idUser: String?) {
    val context = LocalContext.current
    val dataStore = DataStoreAppData(context)
    if(idUser.isNullOrEmpty()) {
        val idUser = dataStore.getIdUser.collectAsState(initial = "").value.toString()
    }

    Text(
        text = "Hello $idUser!",
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    DoeTempoTheme {
        PerfilScreen(idUser = "")
    }
}