package br.com.pedrovieira.doetempo.api.campaign

import br.com.pedrovieira.doetempo.datastore.models.campaign.Campaign
import br.com.pedrovieira.doetempo.datastore.models.responses.CampaignsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CampaignRetrofitService {

    @GET("campaign/")
    fun getAll() : Call<CampaignsResponse>

    @GET("campaign/{id}")
    fun getById(@Path("id") id: String): Call<Campaign>
}