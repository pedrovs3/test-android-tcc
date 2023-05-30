package br.com.pedrovieira.doetempo.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.pedrovieira.doetempo.R
import br.com.pedrovieira.doetempo.components.card_campanha.CardCampaign
import br.com.pedrovieira.doetempo.datastore.DataStoreAppData
import br.com.pedrovieira.doetempo.datastore.models.campaign.Campaign
import br.com.pedrovieira.doetempo.models.UserDetails
import br.com.pedrovieira.doetempo.models.responses.NgoDetailsResponse
import br.com.pedrovieira.doetempo.ui.theme.DoeTempoTheme
import coil.compose.AsyncImage
import coil.imageLoader
import coil.util.DebugLogger


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeCampaigns(
    campaigns: List<Campaign>,
    user: UserDetails?,
    ngoDetails: NgoDetailsResponse?
) {
    var isLoading by remember {
        mutableStateOf(true)
    }

    var nameUser by remember {
        mutableStateOf("")
    }

    var typeUser by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val dataStore = DataStoreAppData(context = context)
    typeUser = dataStore.getType.collectAsState(initial = "").value.toString()
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
            IconButton(onClick = {
                val intent = Intent(context, PerfilActivity::class.java)
                context.startActivity(intent)
            }) {
                if (typeUser == "USER") {
                    AsyncImage(
                        model = user?.photoURL.toString(),
                        contentDescription = "Imagem da ong responsavel pela campanha.",
                        modifier = Modifier
                            .size(45.dp, 45.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        placeholder = painterResource(id = R.drawable.logo_doe_tempo),
                        contentScale = ContentScale.Crop,
                        onError = { isLoading = false },
                        onSuccess = { isLoading = false},
                    )
                } else {
                    AsyncImage(
                        model = ngoDetails?.photoURL.toString(),
                        contentDescription = "Imagem da ong responsavel pela campanha.",
                        modifier = Modifier
                            .size(45.dp, 45.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        placeholder = painterResource(id = R.drawable.logo_doe_tempo),
                        contentScale = ContentScale.Crop,
                        onError = { isLoading = false },
                        onSuccess = { isLoading = false},
                    )
                }
//                Icon(imageVector = Icons.Default.Settings, contentDescription = "Button for settings")
            }
        }
        Column(
            Modifier
                .fillMaxSize()
                .padding(bottom = 40.dp),
        ) {
            LazyColumn(Modifier.fillMaxSize()) {
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
        HomeCampaigns(campaigns = listOf(Campaign()), user = UserDetails(), ngoDetails = NgoDetailsResponse())
    }
}