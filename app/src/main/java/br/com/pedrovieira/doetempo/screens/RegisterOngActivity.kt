package br.com.pedrovieira.doetempo.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.pedrovieira.doetempo.R
import br.com.pedrovieira.doetempo.datastore.DataStoreAppData
import br.com.pedrovieira.doetempo.screens.ui.theme.DoeTempoTheme
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import kotlinx.coroutines.launch
import java.time.LocalDate

class RegisterOngActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoeTempoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RegisterOng("Android")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterOng(name: String, modifier: Modifier = Modifier) {
    var nameState by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var foundationDate by remember {
        mutableStateOf("Data de fundação")
    }
    var genderState by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val datastore = DataStoreAppData(context = context)
    val localDate = LocalDate.now()

    // timePicker
    val calendarState = rememberSheetState()

    CalendarDialog(
        state = calendarState,
        config= CalendarConfig(
            monthSelection = true,
            yearSelection = true,
            maxYear = LocalDate.now().year,
            minYear = 1910,
            disabledDates = listOf(localDate)
        ),
        selection = CalendarSelection.Date { date ->
            if (date >= LocalDate.now()) {
                return@Date
            }
            foundationDate = date.toString()
        }
    )

    Column(
        Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.bg_register),
                alignment = Alignment.BottomCenter
            )
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
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
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
                label = { Text(text = "E-mail", color = Color.White) },
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
            if (foundationDate !== "Data de nascimento") {
                Text(
                    text = "Data de fundação",
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, start = 10.dp),
                    color = Color.White,
                    style = MaterialTheme.typography.subtitle1
                )
            }
            Text(
                text = foundationDate,
                Modifier
                    .padding(vertical = 10.dp)
                    .clip(shape = RoundedCornerShape(20.dp))
                    .border(1.dp, Color.White, shape = RoundedCornerShape(20.dp))
                    .clickable { calendarState.show() }
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 15.dp),
                color = Color.White
            )
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 30.dp)
            .padding(bottom = 30.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom) {
        Button(
            onClick = {
                if (email.isNotEmpty()
                            && password.isNotEmpty()
                            && nameState.isNotEmpty()
                            && foundationDate !== "Data de nascimento"
                ) {
                    scope.launch {
                        datastore.saveNameRegister(nameState)
                        datastore.saveEmailRegister(email)
                        datastore.savePasswordRegister(password = password)
                        datastore.saveBirthdateRegister(foundationDate)
                    }

                    val intent = Intent(context, CompleteRegisterOngActivity::class.java)
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview8() {
    DoeTempoTheme {
        RegisterOng("Android")
    }
}