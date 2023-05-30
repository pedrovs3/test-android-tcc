package br.com.pedrovieira.doetempo.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.net.toUri
import br.com.pedrovieira.doetempo.MainActivity
import br.com.pedrovieira.doetempo.R
import br.com.pedrovieira.doetempo.api.viacep.RetrofitApiViaCep
import br.com.pedrovieira.doetempo.components.top_bar.TopBar
import br.com.pedrovieira.doetempo.components.top_bar.TopBarNewCampaign
import br.com.pedrovieira.doetempo.datastore.models.dto.AddressDTO
import br.com.pedrovieira.doetempo.models.responses.CepResponse
import br.com.pedrovieira.doetempo.screens.ui.theme.DoeTempoTheme
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.google.firebase.storage.FirebaseStorage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewCampaignActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoeTempoTheme {
                window.statusBarColor = MaterialTheme.colors.secondaryVariant.toArgb()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NewCampaignScreen()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NewCampaignScreen() {
    val context = LocalContext.current
    var selectedImage by remember {
        mutableStateOf("")
    }
    var imageToSend = remember {
        mutableStateListOf("")
    }
    var title by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    var beginDate by remember {
        mutableStateOf("")
    }
    var endDate by remember {
        mutableStateOf("")
    }
    var homeOffice by remember {
        mutableStateOf(false)
    }
    var howToContribute by remember {
        mutableStateOf("")
    }
    var preRequisites by remember {
        mutableStateOf("")
    }
    var cep by remember {
        mutableStateOf("")
    }
    var numberAddress by remember {
        mutableStateOf("")
    }
    var street by remember {
        mutableStateOf("")
    }
    var city by remember {
        mutableStateOf("")
    }
    var uf by remember {
        mutableStateOf("")
    }
    var complement by remember {
        mutableStateOf("")
    }
    var address by remember {
        mutableStateOf(AddressDTO())
    }
    var scrollState = rememberScrollState()

    LaunchedEffect(cep.length == 8) {
        val callAddress = RetrofitApiViaCep.getViaCepService().getAddress(cep)
        callAddress.enqueue(object : Callback<CepResponse> {
            override fun onResponse(call: Call<CepResponse>, response: Response<CepResponse>) {
                if (response.isSuccessful) {
                    street = response.body()?.logradouro.toString()
                    cep = response.body()?.cep.toString()
                    city = response.body()?.localidade.toString()
                    uf = response.body()?.uf.toString()
                }
            }

            override fun onFailure(call: Call<CepResponse>, t: Throwable) {
                Toast.makeText(context, "Não é possivel buscar o endereço agora, tente novamente mais tarde!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ) {
        selectedImage = it.toString()
        val storageRef = FirebaseStorage.getInstance().reference.child("images/${selectedImage.toUri().lastPathSegment}")
        val uploadTask = storageRef.putFile(selectedImage.toUri())
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            storageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                imageToSend.add(task.result.toString())
            } else {
                // Handle failures
                // ...
            }
        }
    }
    imageToSend.removeIf { string -> string.isEmpty() }
    val painter = rememberImagePainter(
        if (selectedImage.isEmpty())
            R.drawable.baseline_preview_24
        else
            imageToSend.toList().toString()
    )
    TopBarNewCampaign()
    Column(
        Modifier
            .fillMaxSize()
            .padding(15.dp, 57.dp, 15.dp, 10.dp)
            .verticalScroll(scrollState), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = { launcher.launch("image/*") },
            Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_add_photo_alternate_24),
                contentDescription = "adicionar foto.",
                Modifier.height(45.dp)
            )
        }
        if (selectedImage.isNotEmpty()) {
            Box(modifier = Modifier
                .padding(vertical = 10.dp)
                .background(MaterialTheme.colors.onPrimary)
                .clip(RoundedCornerShape(12.dp))
                .padding(10.dp)
                .fillMaxWidth()
                .height(110.dp)
                ) {
                LazyRow {
                    items(imageToSend.toList().size) {
                        Box(
                            Modifier
                                .padding(end = 5.dp)
                                .clip(RoundedCornerShape(8.dp))) {
                            AsyncImage(model = imageToSend.toList()[it], "imagem escolhida")
                        }
                    }
                }

            }
        }
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            Modifier.fillMaxWidth(),
            label = { Text(text = "Título da campanha") },
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedLabelColor = MaterialTheme.colors.secondary,
                unfocusedBorderColor = MaterialTheme.colors.secondary,
                focusedBorderColor = MaterialTheme.colors.onSurface,
                focusedLabelColor = MaterialTheme.colors.onSurface
            )
        )
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            Modifier.fillMaxWidth(),
            label = { Text(text = "Escreva uma breve descrição") },
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedLabelColor = MaterialTheme.colors.secondary,
                unfocusedBorderColor = MaterialTheme.colors.secondary,
                focusedBorderColor = MaterialTheme.colors.onSurface,
                focusedLabelColor = MaterialTheme.colors.onSurface
            )
        )
        OutlinedTextField(
            value = howToContribute,
            onValueChange = { howToContribute = it },
            Modifier.fillMaxWidth(),
            label = { Text(text = "Como é possivel contribuir?") },
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedLabelColor = MaterialTheme.colors.secondary,
                unfocusedBorderColor = MaterialTheme.colors.secondary,
                focusedBorderColor = MaterialTheme.colors.onSurface,
                focusedLabelColor = MaterialTheme.colors.onSurface
            )
        )
        OutlinedTextField(
            value = preRequisites,
            onValueChange = { preRequisites = it },
            Modifier.fillMaxWidth(),
            label = { Text(text = "Há algum pré-requisito?") },
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedLabelColor = MaterialTheme.colors.secondary,
                unfocusedBorderColor = MaterialTheme.colors.secondary,
                focusedBorderColor = MaterialTheme.colors.onSurface,
                focusedLabelColor = MaterialTheme.colors.onSurface
            )
        )
        Text(text = "Endereço",
            Modifier
                .fillMaxWidth()
                .padding(top = 5.dp), textAlign = TextAlign.Start ,style = MaterialTheme.typography.subtitle1)
        Row(Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = cep,
                onValueChange = { cep = it },
                Modifier.fillMaxWidth(0.5f),
                label = { Text(text = "CEP") },
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedLabelColor = MaterialTheme.colors.secondary,
                    unfocusedBorderColor = MaterialTheme.colors.secondary,
                    focusedBorderColor = MaterialTheme.colors.onSurface,
                    focusedLabelColor = MaterialTheme.colors.onSurface
                )
            )
            Spacer(modifier =Modifier.width(5.dp))
            OutlinedTextField(
                value = uf,
                onValueChange = { uf = it },
                Modifier.fillMaxWidth(),
                label = { Text(text = "Estado") },
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedLabelColor = MaterialTheme.colors.secondary,
                    unfocusedBorderColor = MaterialTheme.colors.secondary,
                    focusedBorderColor = MaterialTheme.colors.onSurface,
                    focusedLabelColor = MaterialTheme.colors.onSurface
                )
            )
        }
        Row(Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = street,
                onValueChange = { street = it },
                Modifier.fillMaxWidth(0.5f),
                label = { Text(text = "Rua") },
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedLabelColor = MaterialTheme.colors.secondary,
                    unfocusedBorderColor = MaterialTheme.colors.secondary,
                    focusedBorderColor = MaterialTheme.colors.onSurface,
                    focusedLabelColor = MaterialTheme.colors.onSurface
                )
            )
            Spacer(modifier =Modifier.width(5.dp))
            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                Modifier.fillMaxWidth(),
                label = { Text(text = "Cidade") },
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedLabelColor = MaterialTheme.colors.secondary,
                    unfocusedBorderColor = MaterialTheme.colors.secondary,
                    focusedBorderColor = MaterialTheme.colors.onSurface,
                    focusedLabelColor = MaterialTheme.colors.onSurface
                )
            )
        }
        Row(Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = numberAddress,
                onValueChange = { numberAddress = it },
                Modifier.fillMaxWidth(0.5f),
                label = { Text(text = "Número") },
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedLabelColor = MaterialTheme.colors.secondary,
                    unfocusedBorderColor = MaterialTheme.colors.secondary,
                    focusedBorderColor = MaterialTheme.colors.onSurface,
                    focusedLabelColor = MaterialTheme.colors.onSurface
                )
            )
            Spacer(modifier =Modifier.width(5.dp))
            OutlinedTextField(
                value = complement,
                onValueChange = { complement = it },
                Modifier.fillMaxWidth(),
                label = { Text(text = "Complemento") },
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedLabelColor = MaterialTheme.colors.secondary,
                    unfocusedBorderColor = MaterialTheme.colors.secondary,
                    focusedBorderColor = MaterialTheme.colors.onSurface,
                    focusedLabelColor = MaterialTheme.colors.onSurface
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview10() {
    DoeTempoTheme {
        NewCampaignScreen()
    }
}