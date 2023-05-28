package br.com.pedrovieira.doetempo.api.campaign

import br.com.pedrovieira.doetempo.datastore.models.campaign.Campaign
import br.com.pedrovieira.doetempo.datastore.models.responses.CampaignsResponse
import br.com.pedrovieira.doetempo.models.responses.CampaignByNameResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CampaignRetrofitService {

    @GET("campaign/")
    fun getAll() : Call<CampaignsResponse>

    @GET("campaign/search")
    fun findByName(@Query("search") searchString: String): Call<CampaignByNameResponse>

    @GET("campaign/{id}")
    fun getById(@Path("id") id: String): Call<Campaign>
}