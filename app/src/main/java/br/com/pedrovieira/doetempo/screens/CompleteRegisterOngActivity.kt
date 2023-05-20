package br.com.pedrovieira.doetempo.screens

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.pedrovieira.doetempo.R
import br.com.pedrovieira.doetempo.api.RetrofitApiDoeTempo
import br.com.pedrovieira.doetempo.api.viacep.RetrofitApiViaCep
import br.com.pedrovieira.doetempo.datastore.DataStoreAppData
import br.com.pedrovieira.doetempo.datastore.models.dto.AddressDTO
import br.com.pedrovieira.doetempo.models.responses.CepResponse
import br.com.pedrovieira.doetempo.models.responses.CreateNgo
import br.com.pedrovieira.doetempo.models.responses.OngCreatedResponse
import br.com.pedrovieira.doetempo.models.responses.UserCreatedResponse
import br.com.pedrovieira.doetempo.screens.ui.theme.DoeTempoTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class CompleteRegisterOngActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoeTempoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CompleteRegisterOng(datastore = DataStoreAppData(this))
                }
            }
        }
    }
}

@Composable
fun CompleteRegisterOng(datastore: DataStoreAppData) {
    var cnpjState by remember {
        mutableStateOf("")
    }
    var cepState by remember {
        mutableStateOf("")
    }
    var countryState by remember {
        mutableStateOf("")
    }
    var cityState by remember {
        mutableStateOf("")
    }
    var streetState by remember {
        mutableStateOf("")
    }
    var neighborhoodState by remember {
        mutableStateOf("")
    }
    var numberState by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    val nameUser = datastore.getNameRegister.collectAsState(initial = "").value.toString()
    val emailUser = datastore.getEmailRegister.collectAsState(initial = "").value.toString()
    val password = datastore.getPasswordRegister.collectAsState(initial = "").value.toString()
    val birthDate = datastore.getBirthdateRegister.collectAsState(initial = "").value.toString()

    Column(
        Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.bg_register),
                alignment = Alignment.BottomCenter
            )
            .padding(vertical = 30.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.logo_white),
                contentDescription = "Logo da plataforma",
                alignment = Alignment.Center,
                modifier = Modifier
                    .height(250.dp)
                    .padding(top = 20.dp)
            )
        }
        Column(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(
                value = cnpjState,
                onValueChange = { cnpjState = it },
                Modifier.fillMaxWidth(),
                label = { Text(text = "CPF", color = Color.White) },
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = MaterialTheme.colors.onSurface,
                    textColor = Color.White,
                    focusedBorderColor = MaterialTheme.colors.onSurface,
                    unfocusedBorderColor = Color.White,
                )
            )
            OutlinedTextField(
                value = cepState,
                onValueChange = { cepState = it },
                Modifier.fillMaxWidth(),
                label = { Text(text = "CEP", color = Color.White) },
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = MaterialTheme.colors.onSurface,
                    textColor = Color.White,
                    focusedBorderColor = MaterialTheme.colors.onSurface,
                    unfocusedBorderColor = Color.White,
                )
            )
            if (cepState.length == 8) {
                val viacepCall = RetrofitApiViaCep.getViaCepService().getAddress(cepState)
                viacepCall.enqueue(object : Callback<CepResponse> {
                    override fun onResponse(
                        call: Call<CepResponse>,
                        response: Response<CepResponse>
                    ) {
                        if (response.isSuccessful) {
                            cepState = response.body()?.cep.toString()
                            countryState = response.body()?.uf.toString()
                            cityState = response.body()?.localidade.toString()
                            neighborhoodState = response.body()?.bairro.toString()
                            streetState = response.body()?.logradouro.toString()
                        }
                    }

                    override fun onFailure(call: Call<CepResponse>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })
            }
            OutlinedTextField(
                value = streetState,
                onValueChange = { streetState },
                Modifier.fillMaxWidth(),
                label = { Text(text = "Rua", color = Color.White) },
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = MaterialTheme.colors.onSurface,
                    textColor = Color.White,
                    focusedBorderColor = MaterialTheme.colors.onSurface,
                    unfocusedBorderColor = Color.White,
                )
            )
            Column(Modifier.fillMaxSize()) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    OutlinedTextField(
                        value = cityState,
                        onValueChange = { cityState },
                        Modifier.fillMaxWidth(0.48f),
                        label = { Text(text = "Cidade", color = Color.White) },
                        shape = RoundedCornerShape(20.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            cursorColor = MaterialTheme.colors.onSurface,
                            textColor = Color.White,
                            focusedBorderColor = MaterialTheme.colors.onSurface,
                            unfocusedBorderColor = Color.White,
                        )
                    )
                    OutlinedTextField(
                        value = neighborhoodState,
                        onValueChange = { neighborhoodState },
                        Modifier.fillMaxWidth(0.9f),
                        label = { Text(text = "Bairro", color = Color.White) },
                        shape = RoundedCornerShape(20.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            cursorColor = MaterialTheme.colors.onSurface,
                            textColor = Color.White,
                            focusedBorderColor = MaterialTheme.colors.onSurface,
                            unfocusedBorderColor = Color.White,
                        )
                    )
                }
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    OutlinedTextField(
                        value = countryState,
                        onValueChange = { countryState },
                        Modifier.fillMaxWidth(0.48f),
                        label = { Text(text = "Estado", color = Color.White) },
                        shape = RoundedCornerShape(20.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            cursorColor = MaterialTheme.colors.onSurface,
                            textColor = Color.White,
                            focusedBorderColor = MaterialTheme.colors.onSurface,
                            unfocusedBorderColor = Color.White,
                        )
                    )
                    OutlinedTextField(
                        value = numberState,
                        onValueChange = { numberState = it },
                        Modifier.fillMaxWidth(0.9f),
                        label = { Text(text = "Número", color = Color.White) },
                        shape = RoundedCornerShape(20.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            cursorColor = MaterialTheme.colors.onSurface,
                            textColor = Color.White,
                            focusedBorderColor = MaterialTheme.colors.onSurface,
                            unfocusedBorderColor = Color.White,
                        )
                    )
                }
                Column(Modifier.fillMaxHeight().fillMaxWidth(), verticalArrangement = Arrangement.Bottom) {
                    Button(
                        onClick = {
                            val ong = CreateNgo(
                                name = nameUser,
                                address = AddressDTO(postalCode = cepState, number = numberState),
                                email = emailUser,
                                cnpj = cnpjState,
                                foundationDate = birthDate,
                                password = password
                            )

                            val registerOngCall = RetrofitApiDoeTempo.retrofitNgoServices().saveOng(ong)
                            registerOngCall.enqueue(object : Callback<OngCreatedResponse>{
                                override fun onResponse(
                                    call: Call<OngCreatedResponse>,
                                    response: Response<OngCreatedResponse>
                                ) {
                                    if(response.isSuccessful) {
                                        Toast.makeText(context, "Ong cadastrada com sucesso", Toast.LENGTH_SHORT).show()
                                        val intent = Intent(context, LoginActivity::class.java)
                                        context.startActivity(intent)
                                        Log.i("created", response.body().toString())
                                    }
                                    Log.i("teste", response.body().toString())
                                    Log.i("teste", response.code().toString())
                                    Log.i("teste", response.message().toString())
                                }

                                override fun onFailure(
                                    call: Call<OngCreatedResponse>,
                                    t: Throwable
                                ) {
                                    TODO("Not yet implemented")
                                }
                            })
                        },
                        Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onSurface)) {
                        Text(
                            text = "Enviar",
                            Modifier.padding(vertical = 5.dp),
                            color = MaterialTheme.colors.primary ,
                            style = MaterialTheme.typography.button
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview9() {
    DoeTempoTheme {
        CompleteRegisterOng(datastore = DataStoreAppData(context = LocalContext.current))
    }
}