package br.com.pedrovieira.doetempo.components.card_campanha

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import br.com.pedrovieira.doetempo.R
import br.com.pedrovieira.doetempo.datastore.models.campaign.Campaign
import br.com.pedrovieira.doetempo.screens.CampaignDetailsActivity
import coil.compose.AsyncImage
import coil.imageLoader
import coil.util.DebugLogger

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardCampaign(context: Context, campaign: Campaign) {
    var imageLink by remember {
        mutableStateOf("https://firebasestorage.googleapis.com/v0/b/doe-tempo-50ccb.appspot.com/o/images%2F5fc289672cf782aaa7e5831f8e462438_4096x2730_2a60182d.webp?alt=media&token=f021a2b4-1a34-458e-a304-94bf07252389")
    }

    var error by remember {
        mutableStateOf(false)
    }

    var isLoading by remember {
     mutableStateOf(true)
    }
    val imageLoader = LocalContext.current.imageLoader.newBuilder()
        .logger(DebugLogger())
        .build()

    imageLink = campaign.ngo?.photoURL.toString()
    Card(onClick = {
        val intent = Intent(context, CampaignDetailsActivity::class.java)
        intent.putExtra("id_campaign", campaign.id)
        startActivity(context, intent, null)
    },
        Modifier
            .fillMaxWidth()
            .padding(top = 10.dp), backgroundColor = Color(0xFFF4F4F4),
            elevation = 0.dp,
            shape = RoundedCornerShape(12.dp)
        ) {
        Column(Modifier.padding(vertical = 5.dp, horizontal = 5.dp)) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp), verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = imageLink,
                        imageLoader = imageLoader,
                        placeholder = painterResource(id = R.drawable.logo_doe_tempo),
                        contentDescription = "Imagem da ong responsavel pela campanha.",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .size(45.dp, 45.dp)
                            .padding(end = 5.dp)
                            .border(
                                border = BorderStroke(1.dp, MaterialTheme.colors.primary),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(3.dp),
                        onError = { isLoading = false },
                        onSuccess = { isLoading = false},
                    )
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Top) {
                    Text(
                        text = campaign.title.toString(),
                         fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        color = MaterialTheme.colors.primary
                    )
                }

            }
            Column(Modifier.padding(start = 5.dp, bottom = 10.dp), verticalArrangement = Arrangement.Center) {
                Text(text = "Sobre:", color = MaterialTheme.colors.primary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = campaign.description.toString(), Modifier.padding(start = 2.dp), color = Color(0xFF888888), fontSize = 14.sp)
            }
        }
    }
}
