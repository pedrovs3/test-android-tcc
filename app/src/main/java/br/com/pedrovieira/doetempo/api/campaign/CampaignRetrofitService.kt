package br.com.pedrovieira.doetempo.api.campaign

import br.com.pedrovieira.doetempo.datastore.models.responses.CampaignsResponse
import retrofit2.Call
import retrofit2.http.GET

interface CampaignRetrofitService {

    @GET("campaign/")
    fun getAll() : Call<CampaignsResponse>
}