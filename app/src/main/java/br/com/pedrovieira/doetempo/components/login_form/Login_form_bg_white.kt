package br.com.pedrovieira.doetempo.components.login_form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun LoginFormWhiteBg(emailState: String, onEmailChange: (String) -> Unit, passwordState: String, onPasswordChange: (String) -> Unit) {
    Card(backgroundColor = Color(0x00ffffff), elevation = 0.dp) {
        Column(
            Modifier
                .fillMaxWidth()
                .height(300.dp),
            verticalArrangement = Arrangement.Top
        ) {
            OutlinedTextField(
                value = emailState,
                onValueChange = onEmailChange,
                Modifier.fillMaxWidth(),
                label = { Text(text = "E-mail") },
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedLabelColor = MaterialTheme.colors.primary,
                    unfocusedBorderColor = MaterialTheme.colors.primary
                )
            )
            OutlinedTextField(
                value = passwordState,
                onValueChange = onPasswordChange,
                Modifier.fillMaxWidth(),
                label = { Text(text = "Senha") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedLabelColor = MaterialTheme.colors.primary,
                    unfocusedBorderColor = MaterialTheme.colors.primary
                )
            )
        }
    }
}
