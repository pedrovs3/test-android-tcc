package br.com.pedrovieira.doetempo.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import br.com.pedrovieira.doetempo.api.RetrofitApiDoeTempo
import br.com.pedrovieira.doetempo.components.card_campanha.CardCampaign
import br.com.pedrovieira.doetempo.datastore.models.campaign.Campaign
import br.com.pedrovieira.doetempo.datastore.models.responses.CampaignsResponse
import br.com.pedrovieira.doetempo.models.responses.CampaignByNameResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun SearchCampaignScreen() {
    val context = LocalContext.current
    var searchString by remember {
      mutableStateOf("")
    }
    var campaigns by remember {
        mutableStateOf(listOf(Campaign()))
    }
    if (searchString.isNotEmpty()) {
        val campaignsCall = RetrofitApiDoeTempo.retrofitCampaignServices().findByName(searchString)
        campaignsCall.enqueue(object : Callback<CampaignByNameResponse> {
            override fun onResponse(
                call: Call<CampaignByNameResponse>,
                response: Response<CampaignByNameResponse>
            ) {
                if (response.isSuccessful){
                    campaigns = response.body()?.payload!!
                }
            }

            override fun onFailure(call: Call<CampaignByNameResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    } else {
        val campaignsCall = RetrofitApiDoeTempo.retrofitCampaignServices().getAll()
        campaignsCall.enqueue(object : Callback<CampaignsResponse> {
            override fun onResponse(
                call: Call<CampaignsResponse>,
                response: Response<CampaignsResponse>
            ) {
                if (response.isSuccessful) {
                    campaigns = response.body()?.campaigns!!
                }

            }

            override fun onFailure(call: Call<CampaignsResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }



    Column(
        Modifier
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .padding(bottom = 45.dp)
            .fillMaxSize()) {
        OutlinedTextField(
            value = searchString,
            onValueChange = { searchString = it },
            Modifier.fillMaxWidth(),
            label = { Text(text = "Procure por uma campanha!") },
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedLabelColor = MaterialTheme.colors.primary,
                unfocusedBorderColor = MaterialTheme.colors.primary
            )
        )
        LazyColumn {
            items(campaigns.size) {
                CardCampaign(context = context, campaign = campaigns[it])
            }
        }
    }
}