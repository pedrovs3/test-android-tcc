package br.com.pedrovieira.doetempo.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.pedrovieira.doetempo.NavigationHost
import br.com.pedrovieira.doetempo.api.RetrofitApiDoeTempo
import br.com.pedrovieira.doetempo.components.BottomBar
import br.com.pedrovieira.doetempo.components.card_campanha.CardCampaign
import br.com.pedrovieira.doetempo.datastore.DataStoreAppData
import br.com.pedrovieira.doetempo.datastore.models.campaign.Campaign
import br.com.pedrovieira.doetempo.datastore.models.responses.CampaignsResponse
import br.com.pedrovieira.doetempo.navigation.ItemsMenu
import br.com.pedrovieira.doetempo.ui.theme.DoeTempoTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeCampaigns(campaigns: List<Campaign>) {
    var nameUser by remember {
        mutableStateOf("")
    }


    val context = LocalContext.current
    val dataStore = DataStoreAppData(context = context)
    nameUser = dataStore.getName.collectAsState(initial = "").value.toString()
    val namesUserListSplitted = nameUser.split(" ")

    if (namesUserListSplitted.size > 2) {
        nameUser = "${namesUserListSplitted[0]} ${namesUserListSplitted[namesUserListSplitted.lastIndex]}"
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 26.dp, vertical = 30.dp)) {
        Row(
            Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Olá $nameUser!", fontSize = 18.sp)
            IconButton(onClick = { /*TODO*/ }, ) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = "Button for settings")
            }
        }
        Column(
            Modifier
                .fillMaxSize()
                .padding(bottom = 45.dp),
        ) {
            LazyColumn(Modifier.fillMaxSize()) {
                Log.i("testee", campaigns.toString())
                items(campaigns) {
                    CardCampaign(context = context, campaign = it)
                }
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    DoeTempoTheme {
        HomeCampaigns(campaigns = listOf(Campaign()))
    }
}