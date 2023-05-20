package br.com.pedrovieira.doetempo.api.user

import br.com.pedrovieira.doetempo.datastore.models.UserCreate
import br.com.pedrovieira.doetempo.models.responses.RegisterUserInCampaignResponse
import br.com.pedrovieira.doetempo.models.responses.UserCreatedResponse
import br.com.pedrovieira.doetempo.models.responses.UserDetailsResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserRetrofitService {
    @POST("user")
    fun saveUser(@Body body: UserCreate): Call<UserCreatedResponse>

    @GET("user/{id}")
    fun getUserDetails(@Header("Authorization") auth: String, @Path("id") id: String) : Call<UserDetailsResponse>

    @POST("user/campaign/")
    fun registerUserInCampaign(@Header("Authorization") token: String, @Query("idCampaign") idCampaign: String): Call<RegisterUserInCampaignResponse>
}