package br.com.pedrovieira.doetempo

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import br.com.pedrovieira.doetempo.api.auth.getToken
import br.com.pedrovieira.doetempo.components.login_form.LoginFormDarkBg
import br.com.pedrovieira.doetempo.components.login_form.LoginFormWhiteBg
import br.com.pedrovieira.doetempo.models.dto.AuthDTO
import br.com.pedrovieira.doetempo.screens.HomeActivity
import br.com.pedrovieira.doetempo.ui.theme.DoeTempoTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlin.jvm.internal.Intrinsics.Kotlin
import kotlin.reflect.typeOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoeTempoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    Home()
                }
            }
        }
    }
}

@Composable
fun Home() {
    val context = LocalContext.current

    var emailState by remember {
        mutableStateOf("")
    }

    var passwordState by remember {
        mutableStateOf("")
    }


    Column(
        Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.bg_main_home),
                alignment = Alignment.BottomCenter,
            )
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Image(
            painter = painterResource(id = R.drawable.logo_doe_tempo), 
            contentDescription = "Logo DoeTempo", 
            contentScale = ContentScale.FillWidth
        )

        if(!isSystemInDarkTheme()) {
            LoginFormWhiteBg(
                emailState = emailState,
                onEmailChange = { emailState = it},
                passwordState = passwordState,
                onPasswordChange = { passwordState = it}
            )
        } else {
            LoginFormDarkBg(
                emailState = emailState,
                onEmailChange = {emailState = it},
                passwordState = passwordState,
                onPasswordChange = {passwordState = it}
            )
        }

        Column(
            Modifier
                .fillMaxWidth()
                .height(300.dp),
            Arrangement.Center,
            Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { handleButtonClick(context, emailState, passwordState)},
                Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.onSurface),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text(
                    text = "Entrar",
                    Modifier.padding(8.dp),
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.primary
                )
            }
            Spacer(modifier = Modifier.size(20.dp))
            
            Text(text = "Ainda não possui uma conta?")
            Text(text = "Registre-se aqui!", Modifier.clickable {
                Toast.makeText(
                    context,
                    "Testee",
                    Toast.LENGTH_SHORT
                ).show() },
                color = MaterialTheme.colors.onSurface
            )
        }
        
    }
}

fun handleButtonClick(context: Context, email: String, password: String) {
    val authBody = AuthDTO(email, password)

    val data = getToken(context, authBody) {
       it
    }.toString()

    if(!context.getSharedPreferences("app_data", MODE_PRIVATE).getString("token", "").isNullOrEmpty()) {
        if(data === "USER"){
            val intent = Intent(context, HomeActivity::class.java)
            startActivity(context, intent, null)
        } else{
            Toast.makeText(context, "é ong", Toast.LENGTH_SHORT).show()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DoeTempoTheme {
        Home()
    }
}