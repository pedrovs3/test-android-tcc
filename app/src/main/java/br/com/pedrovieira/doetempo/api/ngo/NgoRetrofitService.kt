package br.com.pedrovieira.doetempo.api.ngo

import br.com.pedrovieira.doetempo.models.responses.CreateNgo
import br.com.pedrovieira.doetempo.models.responses.OngCreatedResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface NgoRetrofitService {
    @POST("ngo/")
    fun saveOng(@Body body: CreateNgo) : Call<OngCreatedResponse>
}