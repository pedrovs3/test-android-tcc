package br.com.pedrovieira.doetempo.components.top_bar

import android.content.Intent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import br.com.pedrovieira.doetempo.MainActivity

@Composable
fun TopBarNewCampaign() {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        Modifier
                            .fillMaxWidth(0.8f),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Nova campanha", modifier = Modifier
                            .padding(10.dp)
                            .verticalScroll(state = ScrollState(0)))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "voltar")
                    }
                },
                backgroundColor = MaterialTheme.colors.secondaryVariant,
                contentColor = Color.White,
                elevation = 0.dp,
            )
        }
    ) {}
}