package br.com.pedrovieira.doetempo.screens

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import br.com.pedrovieira.doetempo.MainActivity
import br.com.pedrovieira.doetempo.R
import br.com.pedrovieira.doetempo.api.RetrofitApiDoeTempo
import br.com.pedrovieira.doetempo.components.login_form.LoginFormDarkBg
import br.com.pedrovieira.doetempo.components.login_form.LoginFormWhiteBg
import br.com.pedrovieira.doetempo.datastore.DataStoreAppData
import br.com.pedrovieira.doetempo.datastore.models.dto.AuthDTO
import br.com.pedrovieira.doetempo.datastore.models.enums.ButtonState
import br.com.pedrovieira.doetempo.datastore.models.responses.LoginResponse
import br.com.pedrovieira.doetempo.ui.theme.DoeTempoTheme
import com.auth0.android.jwt.JWT
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var token by remember {
                mutableStateOf("")
            }

            val scope = rememberCoroutineScope()
            val dataStore = DataStoreAppData(this)
            token = dataStore.getToken.collectAsState(initial = "").value.toString()
            DoeTempoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    Home(token)
                }
            }
        }

    }
}

@Composable
fun Home(token: String) {
    val context = LocalContext.current
    var emailState by remember {
        mutableStateOf("")
    }

    var passwordState by remember {
        mutableStateOf("")
    }

    var nameUser by remember {
       mutableStateOf("")
    }

    var decodedJwt by remember {
        mutableStateOf("")
    }

    var buttonState by remember {
        mutableStateOf(ButtonState.IDLE)
    }


    val dataStore = DataStoreAppData(context)
    val scope = rememberCoroutineScope()

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
                onClick = {
                    if (token.isNotEmpty()) {
                        scope.launch{
                            dataStore.deleteToken()
                        }
                    }

                    buttonState = ButtonState.LOADING
                    val authBody = AuthDTO(emailState, passwordState) //AuthDTO("pedrovs3@hotmail.com", "ccx967408")
                    val call = RetrofitApiDoeTempo.retrofitServiceAuth().login(authBody)

                    call.enqueue(object : Callback<LoginResponse> {
                        override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                            if(response.isSuccessful) {
                                buttonState = ButtonState.DONE
                                val jwt = JWT(response.body()!!.token)

                                scope.launch {
                                    dataStore.saveToken(response.body()!!.token)
                                    dataStore.saveType(jwt.getClaim("type").asString().toString())
                                    dataStore.saveIdUser(jwt.getClaim("id").asString().toString())
                                    dataStore.saveName(response.body()!!.data.name.toString())
                                }

                                if(response.body()?.token?.isNotEmpty() == true) {
                                    val intent = Intent(context, MainActivity::class.java).putExtra("token", response.body()!!.token)
                                    startActivity(context, intent, null)
                                }else {
                                    Toast.makeText(context, "Usuário ou senha incorreto! Tente novamente.", Toast.LENGTH_SHORT).show()
                                }
                                buttonState = ButtonState.IDLE
                            } else {
                                Toast.makeText(context, "Algum dado está incorreto!", Toast.LENGTH_SHORT).show()
                                buttonState = ButtonState.IDLE
                            }
                        }

                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            Log.i("teste", t.toString())
                            Toast.makeText(context, "Tente novamente mais tarde!", Toast.LENGTH_SHORT).show()
                        }

                    })
                },
                Modifier.fillMaxWidth(),
                enabled = buttonState == ButtonState.IDLE,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.onSurface),
                shape = RoundedCornerShape(30.dp)
            ) {
                when (buttonState) {
                    ButtonState.IDLE -> Text(
                        text = "Entrar",
                        Modifier.padding(8.dp),
                        style = MaterialTheme.typography.button,
                        color = MaterialTheme.colors.primary
                    )
                    ButtonState.LOADING -> CircularProgressIndicator()
                    ButtonState.DONE -> Text(
                        text = "Entrar",
                        Modifier.padding(8.dp),
                        style = MaterialTheme.typography.button,
                        color = MaterialTheme.colors.primary
                    )
                }

            }
            Spacer(modifier = Modifier.size(20.dp))
            
            Text(text = "Ainda não possui uma conta?")
            Text(text = "Registre-se aqui!", Modifier.clickable {
                val intent = Intent(context, TypeSelectionActivity::class.java)
                startActivity(context, intent, null)
                 },
                color = MaterialTheme.colors.onSurface
            )
        }
        
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DoeTempoTheme {
        Home(token = "")
    }
}