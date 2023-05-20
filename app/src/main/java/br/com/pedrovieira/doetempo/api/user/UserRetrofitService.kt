package br.com.pedrovieira.doetempo.api.user

import br.com.pedrovieira.doetempo.datastore.models.UserCreate
import br.com.pedrovieira.doetempo.models.responses.RegisterUserInCampaignResponse
import br.com.pedrovieira.doetempo.models.responses.UserCreatedResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface UserRetrofitService {
    @POST("user")
    fun saveUser(@Body body: UserCreate): Call<UserCreatedResponse>

    @POST("user/campaign/")
    fun registerUserInCampaign(@Header("Authorization") token: String, @Query("idCampaign") idCampaign: String): Call<RegisterUserInCampaignResponse>
}