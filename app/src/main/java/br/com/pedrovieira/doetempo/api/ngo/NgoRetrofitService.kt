package br.com.pedrovieira.doetempo.api.ngo

import br.com.pedrovieira.doetempo.models.responses.CreateNgo
import br.com.pedrovieira.doetempo.models.responses.NgoDetailsResponse
import br.com.pedrovieira.doetempo.models.responses.OngCreatedResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface NgoRetrofitService {
    @POST("ngo/")
    fun saveOng(@Body body: CreateNgo) : Call<OngCreatedResponse>

    @GET("ngo/{id}")
    fun getNgoDetails(
        @Header("Authorization") authToken: String,
        @Path("id") idNgo: String
    ) : Call<NgoDetailsResponse>
}