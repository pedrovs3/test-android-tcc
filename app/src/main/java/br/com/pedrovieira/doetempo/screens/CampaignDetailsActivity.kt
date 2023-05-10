package br.com.pedrovieira.doetempo.screens

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import br.com.pedrovieira.doetempo.api.RetrofitApiDoeTempo
import br.com.pedrovieira.doetempo.components.top_bar.TopBar
import br.com.pedrovieira.doetempo.datastore.models.campaign.Campaign
import br.com.pedrovieira.doetempo.screens.ui.theme.DoeTempoTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CampaignDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var campaignDetailsState by remember {
                mutableStateOf(Campaign())
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

            DoeTempoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = androidx.compose.material.MaterialTheme.colors.background,
                ) {
                    CampaignDetails(campaignDetailsState)
                }
            }
        }
    }
}

@Composable
fun CampaignDetails(campaign: Campaign) {
    Column(Modifier.fillMaxSize()) {
        TopBar(campaign.title)
        Text(
            text = "Hello ${campaign.title}!",
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DoeTempoTheme {
        CampaignDetails(campaign = Campaign())
    }
}