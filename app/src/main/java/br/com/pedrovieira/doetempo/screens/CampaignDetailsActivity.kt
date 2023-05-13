package br.com.pedrovieira.doetempo.screens

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.pedrovieira.doetempo.R
import br.com.pedrovieira.doetempo.api.RetrofitApiDoeTempo
import br.com.pedrovieira.doetempo.api.viacep.RetrofitApiViaCep
import br.com.pedrovieira.doetempo.components.top_bar.TopBar
import br.com.pedrovieira.doetempo.datastore.models.campaign.Campaign
import br.com.pedrovieira.doetempo.datastore.models.campaign.CampaignCause
import br.com.pedrovieira.doetempo.datastore.models.campaign.CampaignPhoto
import br.com.pedrovieira.doetempo.helpers.convertIsoStringToLocalDate
import br.com.pedrovieira.doetempo.helpers.randomColor
import br.com.pedrovieira.doetempo.models.CepResponse
import br.com.pedrovieira.doetempo.screens.ui.theme.DoeTempoTheme
import br.com.pedrovieira.doetempo.screens.ui.theme.Typography
import coil.compose.AsyncImage
import coil.imageLoader
import coil.util.DebugLogger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.Period

class CampaignDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var campaignDetailsState by remember {
                mutableStateOf(Campaign())
            }
            var address by remember {
                mutableStateOf(CepResponse())
            }


            val idCampaign = this.intent.getStringExtra("id_campaign").toString()
            val call = RetrofitApiDoeTempo.retrofitCampaignServices().getById(idCampaign)

            call.enqueue(object: Callback<Campaign> {
                override fun onResponse(call: Call<Campaign>, response: Response<Campaign>) {
                    Log.i("teste", response.body().toString())
                    if (response.isSuccessful) {
                        campaignDetailsState = response.body() ?: Campaign()
                    }
                }

                override fun onFailure(call: Call<Campaign>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })

            if (campaignDetailsState.campaignAddress != null) {
                val cep = campaignDetailsState.campaignAddress?.address?.postalCode.toString()
                val callViaCep = RetrofitApiViaCep.getViaCepService().getAddress(cep)

                callViaCep.enqueue(object : Callback<CepResponse> {
                    override fun onResponse(call: Call<CepResponse>, response: Response<CepResponse>) {
                        Log.i("viacep", response.body().toString())
                        if (response.isSuccessful) {
                            address = response.body()!!
                        }
                    }

                    override fun onFailure(call: Call<CepResponse>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })
            }
            DoeTempoTheme {
                window.statusBarColor = MaterialTheme.colors.secondaryVariant.toArgb()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = androidx.compose.material.MaterialTheme.colors.background,
                ) {
                    CampaignDetails(campaignDetailsState, address)
                }
            }
        }
    }
}

@Composable
fun CampaignDetails(campaign: Campaign, address: CepResponse) {
    var isLoading by remember {
        mutableStateOf(true)
    }

    var editable by remember {
        mutableStateOf(false)
    }

    val imageLoader = LocalContext.current.imageLoader.newBuilder()
        .logger(DebugLogger())
        .build()

    var images by remember {
        mutableStateOf(listOf(CampaignPhoto()))
    }
    var causes by remember {
        mutableStateOf(listOf(CampaignCause()))
    }

    if (!campaign.campaignPhotos.isNullOrEmpty()) {
        images = campaign.campaignPhotos
    }
    if (!campaign.campaignCauses.isNullOrEmpty()) {
        causes = campaign.campaignCauses
    }

    TopBar(campaign.title)
    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 55.dp, end = 15.dp, start = 15.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            Modifier
                .fillMaxWidth()
        ) {
            Box(
                Modifier
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = 10.dp,
                            topEnd = 10.dp,
                            bottomStart = 10.dp,
                            bottomEnd = 10.dp
                        )
                    )
                    .padding(end = 5.dp)
                    .border(
                        border = BorderStroke(
                            1.dp,
                            MaterialTheme.colors.primary
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(3.dp),
            ) {
                    AsyncImage(
                        model = campaign.ngo?.photoURL,
                        contentDescription = "Imagem da ong responsavel pela campanha.",
                        imageLoader = imageLoader,
                        modifier = Modifier
                            .size(45.dp, 45.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        placeholder = painterResource(id = R.drawable.logo_doe_tempo),
                        contentScale = ContentScale.Crop,
                        onError = { isLoading = false },
                        onSuccess = { isLoading = false},
                    )
            }
            Column(Modifier.fillMaxWidth()) {
                Text(text = "Criada por ${campaign.ngo?.name.toString()}")
            }
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            LazyRow {
                items(images.size) { index ->
                    AsyncImage(
                        model = images[index].photoURL.toString(),
                        contentDescription = "Imagem da ong responsavel pela campanha.",
                        imageLoader = imageLoader,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .padding(top = 10.dp)
                            .align(Alignment.Center)
                            .clip(RoundedCornerShape(10.dp)),
                        placeholder = painterResource(id = R.drawable.logo_doe_tempo),
                        contentScale = ContentScale.Crop,
                        onError = { isLoading = false },
                        onSuccess = { isLoading = false},
                    )
                }
            }
        }
        LazyRow(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            items(causes.size) {index ->
                val color = randomColor()
                val contrastColor = Color(color.toArgb() + 200)
                Box(modifier = Modifier
                    .padding(top = 10.dp, end = 10.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .border(
                        border = BorderStroke(2.dp, contrastColor),
                        shape = RoundedCornerShape(50.dp)
                    )
                    .background(Color(color.toArgb()))) {
                    Text(text = causes[index].causes?.title.toString(), Modifier.padding(horizontal = 20.dp, vertical = 9.dp))
                }

            }
        }
        Text(
            text = "Sobre a campanha:",
            Modifier
                .padding(top = 7.dp)
                .fillMaxWidth(),
            style = Typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "${campaign.description}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp),
            style = MaterialTheme.typography.body1,
            color = Color(0xB2000000)
        )
        Text(
            text = "Detalhes:",
            Modifier
                .padding(top = 5.dp)
                .fillMaxWidth(),
            style = Typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = 5.dp)
                .fillMaxHeight(0.4f),
            Arrangement.SpaceEvenly
        ) {
            Row(Modifier.fillMaxWidth(), Arrangement.Start, Alignment.CenterVertically) {
                Icon(painter = painterResource(id = R.drawable.clock_icon), contentDescription = "icone ilustrativo", Modifier.size(27.dp))
                Spacer(modifier = Modifier.width(10.dp))
                if (campaign.endDate != null && campaign.beginDate != null ) {
                    val expireDate =   Period.between(LocalDate.now() ,convertIsoStringToLocalDate(campaign.endDate.toString()))
                    if (expireDate.isNegative) {
                        Text(
                            text = "Campanha encerrada!",
                            style = MaterialTheme.typography.body1,
                            color = Color(0xB2000000)
                        )
                    } else {
                        Text(
                            text = "Encerra em ${expireDate.months} mes(es) e ${expireDate.days} dia(s)",
                            style = MaterialTheme.typography.body1,
                            color = Color(0xB2000000)
                        )
                    }
                    // Period.between(convertIsoStringToLocalDate(campaign.beginDate.toString()), convertIsoStringToLocalDate(campaign.endDate.toString()))
                }
            }
            Row(Modifier.fillMaxWidth(), Arrangement.Start, Alignment.CenterVertically) {
                Icon(painter = painterResource(id = R.drawable.globe_icon), contentDescription = "icone ilustrativo", Modifier.size(27.dp))
                Spacer(modifier = Modifier.width(10.dp))
                if (campaign.homeOffice == true) {
                    Text(
                        text = "Pode ser feito à distância",
                        style = MaterialTheme.typography.body1,
                        color = Color(0xB2000000))
                } else {
                    Text(text = "Apenas presencial!",
                    style = MaterialTheme.typography.body1,
                        color = Color(0xB2000000))
                }
            }
            Row(Modifier.fillMaxWidth(), Arrangement.Start, Alignment.CenterVertically) {
                Icon(painter = painterResource(id = R.drawable.map_pin_icon), contentDescription = "icone ilustrativo", Modifier.size(27.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "${address.logradouro}, ${campaign.campaignAddress?.address?.number} - ${address.bairro}, ${address.localidade} - ${address.uf}",
                    style = MaterialTheme.typography.body1,
                    color = Color(0xB2000000))
            }
        }
        Row(
            Modifier
                .fillMaxWidth()
                .clickable {
                    editable = !editable
                }
                .clip(RoundedCornerShape(12.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Como contribuir?", style = Typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(10.dp))
            Icon(painter = painterResource(id = R.drawable.question), contentDescription = "Como contribuir", Modifier.size(20.dp))
        }
        AnimatedVisibility(visible = editable) {
            Column(Modifier.fillMaxSize()) {
                Text(text = campaign.howToContribute.toString(),
                    Modifier.padding(start = 5.dp),
                    style = MaterialTheme.typography.body1,
                    color = Color(0xB2000000))
                Text(
                    text = "Pré-requisitos:",
                    Modifier.padding(top = 5.dp),
                    style = Typography.titleLarge,
                    fontWeight = FontWeight.Bold)
                Text(text = campaign.prerequisites.toString(),
                    Modifier.padding(start = 5.dp),
                    style = MaterialTheme.typography.body1,
                    color = Color(0xB2000000))
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize().padding(10.dp), contentAlignment = Alignment.BottomCenter) {
        Button(onClick = { /*TODO*/ },
            Modifier
                .fillMaxWidth().clip(RoundedCornerShape(12.dp)),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
        ) {
            Text(text = "Inscrever-se", Modifier.padding(6.dp), style = MaterialTheme.typography.button, color = MaterialTheme.colors.onSurface)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DoeTempoTheme {
        CampaignDetails(campaign = Campaign(), address = CepResponse())
    }
}