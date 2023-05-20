package br.com.pedrovieira.doetempo

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import br.com.pedrovieira.doetempo.api.RetrofitApiDoeTempo
import br.com.pedrovieira.doetempo.components.BottomBar
import br.com.pedrovieira.doetempo.datastore.DataStoreAppData
import br.com.pedrovieira.doetempo.datastore.models.campaign.Campaign
import br.com.pedrovieira.doetempo.datastore.models.responses.CampaignsResponse
import br.com.pedrovieira.doetempo.navigation.ItemsMenu
import br.com.pedrovieira.doetempo.ui.theme.DoeTempoTheme
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoeTempoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BottomMenuNavigation()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BottomMenuNavigation() {
    var campaigns by remember {
        mutableStateOf(listOf(Campaign()))
    }
    val call = RetrofitApiDoeTempo.retrofitCampaignServices().getAll()

    call.enqueue(object: Callback<CampaignsResponse> {
        override fun onResponse(
            call: Call<CampaignsResponse>,
            response: Response<CampaignsResponse>
        ) {
            Log.i("ds3m", response.body().toString())
            if (response.isSuccessful) {
                campaigns = response.body()?.campaigns as List<Campaign>
            }
        }

        override fun onFailure(call: Call<CampaignsResponse>, t: Throwable) {
            TODO("Not yet implemented")
        }

    })
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val navigationItem = listOf(
        ItemsMenu.Campaign,
        ItemsMenu.User,
        ItemsMenu.Feed
    )

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = { BottomBar(navController, menuItems = navigationItem) },
    ) {
        NavigationHost(navController = navController, campaigns = campaigns)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    DoeTempoTheme {
        BottomMenuNavigation()
    }
}