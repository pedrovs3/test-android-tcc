package br.com.pedrovieira.doetempo.components.top_bar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TopBar(title: String?) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (title != null) {
                        Row(
                            Modifier
                                .fillMaxWidth(0.8f),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = title)
                        }
                    } else {
                        Row(
                            Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = "null")
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "voltar")
                    }
                },
                backgroundColor = Color(0xFF305BE5),
                contentColor = Color.White,
                elevation = 0.dp
            )
        }
    ) {

    }
}