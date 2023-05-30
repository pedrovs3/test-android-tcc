package br.com.pedrovieira.doetempo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import br.com.pedrovieira.doetempo.api.RetrofitApiDoeTempo
import br.com.pedrovieira.doetempo.components.BottomBar
import br.com.pedrovieira.doetempo.datastore.DataStoreAppData
import br.com.pedrovieira.doetempo.datastore.models.campaign.Campaign
import br.com.pedrovieira.doetempo.datastore.models.responses.CampaignsResponse
import br.com.pedrovieira.doetempo.models.UserDetails
import br.com.pedrovieira.doetempo.models.responses.NgoDetailsResponse
import br.com.pedrovieira.doetempo.models.responses.UserDetailsResponse
import br.com.pedrovieira.doetempo.navigation.ItemsMenu
import br.com.pedrovieira.doetempo.screens.NewCampaignActivity
import br.com.pedrovieira.doetempo.ui.theme.DoeTempoTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var campaigns by remember {
                mutableStateOf(listOf(Campaign()))
            }
            var userDetails by remember {
                mutableStateOf(UserDetails())
            }
            var ngoDetails by remember {
                mutableStateOf(NgoDetailsResponse())
            }

            val dataStore = DataStoreAppData(context = this)

            val typeUser = dataStore.getType.collectAsState(initial = "").value.toString()
            val token = dataStore.getToken.collectAsState(initial = "").value.toString()
            val idUser = dataStore.getIdUser.collectAsState(initial = "").value.toString()

            if (typeUser == "USER") {
                try {
                    val response = RetrofitApiDoeTempo
                        .retrofitUserServices()
                        .getUserDetails("Bearer $token", id = idUser)

                    response.enqueue(object : Callback<UserDetailsResponse>{
                        override fun onResponse(
                            call: Call<UserDetailsResponse>,
                            response: Response<UserDetailsResponse>
                        ) {
                            if (response.isSuccessful) {
                                userDetails = response.body()?.user ?: UserDetails()
                                Log.i("userAPI", response.body().toString())
                            }
                        }
                        override fun onFailure(call: Call<UserDetailsResponse>, t: Throwable) {
                            TODO("Not yet implemented")
                        }
                    })
                } catch (e: Exception) {
                    Log.i("userDetails", e.message.toString())
                }
            } else {
                try {
                    val response = RetrofitApiDoeTempo.retrofitNgoServices().getNgoDetails("Bearer $token", idUser)
                    response.enqueue(object : Callback<NgoDetailsResponse> {
                        override fun onResponse(
                            call: Call<NgoDetailsResponse>,
                            response: Response<NgoDetailsResponse>
                        ) {
                            if (response.isSuccessful) {
                                ngoDetails = response.body() ?: NgoDetailsResponse()
                            }
                        }
                        override fun onFailure(call: Call<NgoDetailsResponse>, t: Throwable) {
                            TODO("Not yet implemented")
                        }
                    })

                } catch (e: Exception) {
                    Log.i("ngoDetails", e.message.toString())
                }
            }

            try {
                val response = RetrofitApiDoeTempo.retrofitCampaignServices().getAll()
                response.enqueue(object : Callback<CampaignsResponse>{
                    override fun onResponse(
                        call: Call<CampaignsResponse>,
                        response: Response<CampaignsResponse>
                    ) {
                        if (response.isSuccessful) {
                            campaigns = response.body()?.campaigns ?: listOf()
                        }
                    }

                    override fun onFailure(call: Call<CampaignsResponse>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })

            } catch (e: Exception) {
                Log.i("campaigns", e.message.toString())
            }


            DoeTempoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BottomMenuNavigation(
                        typeUser,
                        userDetails = userDetails,
                        ngoDetails = ngoDetails,
                        idUser = idUser,
                        campaigns = campaigns
                    )
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BottomMenuNavigation(
    typeUser: String,
    userDetails: UserDetails?,
    ngoDetails: NgoDetailsResponse?,
    idUser: String,
    campaigns: List<Campaign>
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val navigationItem = listOf(
        ItemsMenu.Campaign,
        ItemsMenu.Search,
        ItemsMenu.Feed
    )

    if (typeUser == "USER") {
        Scaffold(
            scaffoldState = scaffoldState,
            bottomBar = { BottomBar(navController, menuItems = navigationItem, typeUser) },
        ) {
            NavigationHost(
                navController = navController,
                campaigns = campaigns,
                idUser = idUser,
                userDetails,
                ngoDetails)
        }
    } else {
        Scaffold(
            scaffoldState = scaffoldState,
            bottomBar = { BottomBar(navController, menuItems = navigationItem, typeUser) },
            floatingActionButton = {Fab(scope, scaffoldState)},
            isFloatingActionButtonDocked = true
        ) {
            NavigationHost(
                navController = navController,
                campaigns = campaigns,
                idUser = idUser,
                userDetails,
                ngoDetails)
        }
    }

}

@Composable
fun Fab(scope: CoroutineScope, scaffoldState: ScaffoldState) {
    val context = LocalContext.current
    FloatingActionButton(
        onClick = {
            val intent = Intent(context, NewCampaignActivity::class.java)
            context.startActivity(intent)
            scope.launch { scaffoldState.snackbarHostState
                .showSnackbar("Ainda indisponivel", actionLabel = "Aceitar", duration = SnackbarDuration.Indefinite) }
        },
        backgroundColor = MaterialTheme.colors.secondaryVariant,
        elevation = FloatingActionButtonDefaults.elevation(0.dp)
    ) {
        if(isSystemInDarkTheme()) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Abrir menu",
                tint = MaterialTheme.colors.primary
            )
        } else {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Abrir menu",
                tint = MaterialTheme.colors.background
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    DoeTempoTheme {
        BottomMenuNavigation(campaigns = listOf(), idUser = "", ngoDetails = NgoDetailsResponse(), userDetails = UserDetails(), typeUser = "")
    }
}