package br.com.pedrovieira.doetempo.screens

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.pedrovieira.doetempo.R
import br.com.pedrovieira.doetempo.api.RetrofitApiDoeTempo
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
    var isLoading by remember {
        mutableStateOf(true)
    }
    val imageLoader = LocalContext.current.imageLoader.newBuilder()
        .logger(DebugLogger())
        .build()

    val yearOfSubscribe = convertIsoStringToLocalDate(user?.createdAt.toString())?.year

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
            Spacer(modifier = Modifier.height(50.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(108.dp),
                verticalAlignment = Alignment.Bottom,
            ) {
                Box(
                    Modifier
                        .clip(shape = RoundedCornerShape(100.dp))
                        .border(
                            border = BorderStroke(2.dp, MaterialTheme.colors.primary),
                            shape = RoundedCornerShape(100.dp)
                        )
                        .padding(5.dp)) {
                    AsyncImage(
                        model = user?.photoURL.toString(),
                        contentDescription = "Imagem da ong responsavel pela campanha.",
                        imageLoader = imageLoader,
                        modifier = Modifier
                            .size(100.dp, 100.dp)
                            .clip(RoundedCornerShape(100.dp))
                        ,
                        placeholder = painterResource(id = R.drawable.logo_doe_tempo),
                        contentScale = ContentScale.Crop,
                        onError = { isLoading = false },
                        onSuccess = { isLoading = false},
                    )
                }
                Spacer(modifier = Modifier.padding(5.dp))
                Column(Modifier.fillMaxWidth().height(55.dp), verticalArrangement = Arrangement.Top) {
//                    Spacer(modifier =Modifier.height(10.dp))
                    Text(
                        text = user?.name.toString(),
//                        Modifier.padding(bottom = 33.dp),
                    )
                    Text(text = "Entrou em $yearOfSubscribe")
                }

            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Editar")
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