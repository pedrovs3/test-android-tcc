package br.com.pedrovieira.doetempo.screens

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.Button
import androidx.compose.material.RadioButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.Surface
import androidx.compose.material.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.pedrovieira.doetempo.R
import br.com.pedrovieira.doetempo.api.RetrofitApiDoeTempo
import br.com.pedrovieira.doetempo.datastore.DataStoreAppData
import br.com.pedrovieira.doetempo.datastore.models.dto.GenderDTO
import br.com.pedrovieira.doetempo.models.AllGenders
import br.com.pedrovieira.doetempo.ui.theme.DoeTempoTheme
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterUserActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var allGenders by rememberSaveable {
                mutableStateOf(listOf(GenderDTO()))
            }
            val gendersCall = RetrofitApiDoeTempo.retrofitGenderServices().getGenders()
            gendersCall.enqueue(object :Callback<AllGenders> {
                override fun onResponse(call: Call<AllGenders>, response: Response<AllGenders>) {
                    if (response.isSuccessful) {
                        Log.i("generos", response.body().toString())
                        allGenders = response.body()?.genders!!
                    }
                }

                override fun onFailure(call: Call<AllGenders>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })

            DoeTempoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    RegisterUser(allGenders)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterUser(allGenders: List<GenderDTO>?) {
    var nameState by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var birthdate by remember {
        mutableStateOf("Data de nascimento")
    }
    var genderState by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val datastore = DataStoreAppData(context = context)

    // timePicker
    val calendarState = rememberSheetState()

    CalendarDialog(
        state = calendarState,
        selection = CalendarSelection.Date { date ->
            birthdate = date.toString()
        }
    )

    Column(
        Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.bg_register),
                alignment = Alignment.BottomCenter
            ),
        verticalArrangement = Arrangement.SpaceEvenly
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
                .fillMaxHeight(0.75f)
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(
                value = nameState,
                onValueChange = { nameState = it },
                Modifier.fillMaxWidth(),
                label = { Text(text = "Nome", color = Color.White) },
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = MaterialTheme.colors.onSurface, 
                    textColor = Color.White,
                    focusedBorderColor = MaterialTheme.colors.onSurface,
                    unfocusedBorderColor = Color.White,
                )
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                Modifier.fillMaxWidth(),
                label = { Text(text = "E-mail", color =Color.White) },
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = MaterialTheme.colors.onSurface,
                    textColor = Color.White,
                    focusedBorderColor = MaterialTheme.colors.onSurface,
                    unfocusedBorderColor = Color.White,
                )
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password= it },
                Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                label = { Text(text = "Senha", color = Color.White) },
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = MaterialTheme.colors.onSurface,
                    textColor = Color.White,
                    focusedBorderColor = MaterialTheme.colors.onSurface,
                    unfocusedBorderColor = Color.White
                )
            )
            if (birthdate !== "Data de nascimento") {
                Text(
                    text = "Data de nascimento",
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, start = 10.dp),
                    color = Color.White,
                    style = MaterialTheme.typography.subtitle1
                )
            }
            Text(
                text = birthdate,
                Modifier
                    .padding(vertical = 10.dp)
                    .clip(shape = RoundedCornerShape(20.dp))
                    .border(1.dp, Color.White, shape = RoundedCornerShape(20.dp))
                    .clickable { calendarState.show() }
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 15.dp),
                color = Color.White
            )
            Text(
                text = "Sexo:", Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 10.dp),
                color = Color.White,
                style = MaterialTheme.typography.subtitle1
            )

            if (allGenders != null) {
                if (allGenders.isNotEmpty()) {
                    val (selectedOption, onOptionSelected) = remember { mutableStateOf(allGenders[0]) }
                    Column(Modifier.selectableGroup()) {
                        allGenders.forEach { text ->
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .height(40.dp)
                                    .selectable(
                                        selected = (text == selectedOption),
                                        onClick = {
                                            onOptionSelected(text)
                                            genderState = text.id.toString()
                                            //                                        Log.i("ds3m", genderState)
                                        },
                                        role = Role.RadioButton
                                    )
                                    .padding(horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = (text == selectedOption),
                                    onClick = { genderState = text.id.toString() }
                                )
                                Text(text = text.name.toString())
                            }
                        }
                    }
                }
            }

//            LazyRow(Modifier.fillMaxSize()) {
//                if (allGenders?.isNotEmpty() == true) {
//                    items(allGenders.size) { index ->
//                        if (selectedOption === allGenders[index].id) {
//                            Text(
//                                text = allGenders[index].name.toString(),
//                                Modifier
//                                    .padding(5.dp)
//                                    .clip(shape = RoundedCornerShape(10.dp))
//                                    .clickable {
//                                        selectedOption = allGenders[index].id.toString()
//                                    }
//                                    .background(MaterialTheme.colors.secondary)
//                                    .padding(10.dp),
//                                color = MaterialTheme.colors.primary
//                            )
//                        } else {
//                            Text(
//                                text = allGenders[index].name.toString(),
//                                Modifier
//                                    .padding(5.dp)
//                                    .clip(shape = RoundedCornerShape(10.dp))
//                                    .clickable {
//                                        selectedOption = allGenders[index].id.toString()
//                                    }
//                                    .padding(10.dp)
//                            )
//                        }
//
//                    }
//                }
//            }
        }
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .padding(horizontal = 30.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center) {
            Button(
                onClick = {
                    Toast.makeText(context, genderState, Toast.LENGTH_SHORT).show()
                          if (
                              email.isNotEmpty()
                              && password.isNotEmpty()
                              && nameState.isNotEmpty()
                              && birthdate !== "Data de nascimento"
                              && genderState.isNotEmpty()
                          ) {
                              scope.launch {
                                  datastore.saveNameRegister(nameState)
                                  datastore.saveEmailRegister(email)
                                  datastore.savePasswordRegister(password = password)
                                  datastore.saveBirthdateRegister(birthdate)
                                  datastore.saveGenderRegister(genderState)
                              }

                              val intent = Intent(context, CompleteRegisterUserActivity::class.java)
                              context.startActivity(intent)
                          }
                },
                Modifier
                    .height(50.dp)
                    .width(50.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onSurface)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_forward_24),
                    contentDescription = "Avançar",
                    Modifier.size(40.dp),
                    tint = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview6() {
    DoeTempoTheme {
        RegisterUser(allGenders = listOf(GenderDTO()))
    }
}