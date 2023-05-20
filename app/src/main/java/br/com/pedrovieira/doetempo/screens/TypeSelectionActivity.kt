package br.com.pedrovieira.doetempo.screens

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.pedrovieira.doetempo.R
import br.com.pedrovieira.doetempo.screens.ui.theme.DoeTempoTheme

class TypeSelectionActivity : ComponentActivity() {
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
    var animateState by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    Handler().postDelayed({
          animateState = true
    }, 200)

    AnimatedVisibility(visible = animateState, enter = slideIn(initialOffset = { IntOffset(x = 0, y = 1000)}, animationSpec = tween(300))) {
        Column(
            Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = R.drawable.bg_main_home),
                    alignment = Alignment.BottomCenter
                ),
            Arrangement.SpaceBetween
        ) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.logo_doe_tempo),
                    contentDescription = "Logo da plataforma",
                    alignment = Alignment.Center
                )
            }
            Column(
                Modifier
                    .fillMaxHeight(0.7f)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Button(
                    onClick = {
                        val intent = Intent(context, RegisterOngActivity::class.java)
                        context.startActivity(intent)
                    },
                    Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.onSurface)
                ) {
                    Text(text = "Sou uma Ong",
                        Modifier.padding(vertical = 5.dp),
                        style = MaterialTheme.typography.button,
                        color = MaterialTheme.colors.primary,
                        fontSize = 20.sp,
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(onClick = {
                    val intent = Intent(context, RegisterUserActivity::class.java)
                    context.startActivity(intent)
                },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.onSurface)
                ) {
                    Text(text = "Sou um voluntário",
                        Modifier.padding(vertical = 5.dp),
                        style = MaterialTheme.typography.button,
                        color = MaterialTheme.colors.primary,
                        fontSize = 20.sp,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview5() {
    DoeTempoTheme {
        Greeting()
    }
}