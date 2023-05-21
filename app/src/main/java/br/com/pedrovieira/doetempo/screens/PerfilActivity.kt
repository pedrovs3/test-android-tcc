package br.com.pedrovieira.doetempo.screens

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.pedrovieira.doetempo.MainActivity
import br.com.pedrovieira.doetempo.R
import br.com.pedrovieira.doetempo.api.RetrofitApiDoeTempo
import br.com.pedrovieira.doetempo.components.card_post.CardPost
import br.com.pedrovieira.doetempo.datastore.DataStoreAppData
import br.com.pedrovieira.doetempo.helpers.convertIsoStringToLocalDate
import br.com.pedrovieira.doetempo.models.UserDetails
import br.com.pedrovieira.doetempo.models.responses.UserDetailsResponse
import br.com.pedrovieira.doetempo.screens.ui.theme.DoeTempoTheme
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.imageLoader
import coil.util.DebugLogger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

class PerfilActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var userDetails by remember {
                mutableStateOf(UserDetails())
            }
            val dataStore = DataStoreAppData(this)
            val idUser = dataStore.getIdUser.collectAsState(initial = "").value.toString()
            val typeUser = dataStore.getType.collectAsState(initial = "").value.toString()
            val token = dataStore.getToken.collectAsState(initial = "").value.toString()
            if (typeUser == "USER") {
                Log.i("userDetailsToken", token)
                val callUserDetails = RetrofitApiDoeTempo
                    .retrofitUserServices()
                    .getUserDetails("Bearer $token", id = idUser)

                callUserDetails.enqueue(object : Callback<UserDetailsResponse> {
                    override fun onResponse(
                        call: Call<UserDetailsResponse>,
                        response: Response<UserDetailsResponse>
                    ) {
                        if (response.isSuccessful) {
                            userDetails = response.body()!!.user!!
                        }
                    }

                    override fun onFailure(call: Call<UserDetailsResponse>, t: Throwable) {
                        Log.i("userDetails", t.message.toString())
                    }
                })
            }
            DoeTempoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PerfilScreen(idUser, userDetails)
                }
            }
        }
    }
}


@Composable
fun PerfilScreen(idUser: String?, user: UserDetails?) {
    val context = LocalContext.current
    val dataStore = DataStoreAppData(context = context)

    var content by remember {
        mutableStateOf("")
    }
    var isLoading by remember {
        mutableStateOf(true)
    }
    val imageLoader = LocalContext.current.imageLoader.newBuilder()
        .logger(DebugLogger())
        .build()

    val yearOfSubscribe = convertIsoStringToLocalDate(user?.createdAt.toString())?.year

    Log.i("user", user.toString())

    Column(
        Modifier
            .fillMaxSize()
            .paint(
                painter = rememberAsyncImagePainter(
                    model = user?.bannerPhoto.toString(),
                    imageLoader = imageLoader,
                    placeholder = painterResource(id = R.drawable.logo_doe_tempo),
                    onSuccess = { isLoading = false },
                    onError = { isLoading = false },
                ), alignment = Alignment.TopCenter, contentScale = ContentScale.FillWidth
            )) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)) {
            Spacer(modifier = Modifier.height(2.dp))
            IconButton(onClick = {
                                 val intent = Intent(context, MainActivity()::class.java)
                context.startActivity(intent)
            }, Modifier.clip(RoundedCornerShape(14.dp))) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "voltar",
                    Modifier
                        .border(
                            2.dp,
                            MaterialTheme.colors.onSurface,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(5.dp)
                        .clip(
                            RoundedCornerShape(12.dp)
                        )
                )
            }
//            Spacer(modifier = Modifier.height(50.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(110.dp),
                verticalAlignment = Alignment.Bottom,
            ) {
                Box(
                    Modifier
                        .clip(shape = RoundedCornerShape(12.dp))
                        .border(
                            border = BorderStroke(2.dp, MaterialTheme.colors.primary),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(5.dp)) {
                    AsyncImage(
                        model = user?.photoURL.toString(),
                        contentDescription = "Imagem da ong responsavel pela campanha.",
                        imageLoader = imageLoader,
                        modifier = Modifier
                            .size(70.dp, 70.dp)
                            .clip(RoundedCornerShape(8.dp))
                        ,
                        placeholder = painterResource(id = R.drawable.logo_doe_tempo),
                        contentScale = ContentScale.Crop,
                        onError = { isLoading = false },
                        onSuccess = { isLoading = false},
                    )
                }
                Spacer(modifier = Modifier.padding(5.dp))
                Column(
                    Modifier
                        .fillMaxWidth()
                        .height(55.dp), verticalArrangement = Arrangement.Bottom) {
                    Row(Modifier.fillMaxSize()) {
                        Column(Modifier.fillMaxWidth(0.7f)) {
                            Text(
                                text = user?.name.toString(),
                                style = MaterialTheme.typography.subtitle1
                            )
                            Text(text = "Entrou em $yearOfSubscribe")
                        }
                        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Bottom) {
                            Button(onClick = { /*TODO*/ },
                                Modifier
                                    .clip(RoundedCornerShape(50.dp))
                                    .fillMaxWidth()
                                    .height(30.dp)
                            ) {
                                Text(text = "Editar", fontSize = 13.sp)
                            }
                        }
                    }
                }
            }
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colors.onPrimary)
                    .padding(vertical = 10.dp, horizontal = 10.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlinedTextField(
                            value = content,
                            onValueChange = {content = it},
                            Modifier.fillMaxWidth(0.8f),
                            label = { Text(text = "O que gostaria de compartilhar?") },
                            shape = RoundedCornerShape(8.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Button(onClick = { /*TODO*/ }, Modifier.padding(0.dp).fillMaxWidth()) {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "enviar post.")
                        }
                    }
                }
                LazyColumn(Modifier.fillMaxSize()) {
                    if (user != null) {
                        user.postUser?.let {
                            items(it.size) {
                                user.postUser[it].post?.let { it1 ->
                                    CardPost(context, post = it1, user = user)
                                }
                            }
                        }
                    }
                }
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    DoeTempoTheme {
        PerfilScreen(idUser = "", user = UserDetails())
    }
}