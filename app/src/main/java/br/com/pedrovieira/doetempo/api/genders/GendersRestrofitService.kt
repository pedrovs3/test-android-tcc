package br.com.pedrovieira.doetempo.api.genders

import br.com.pedrovieira.doetempo.models.AllGenders
import retrofit2.Call
import retrofit2.http.GET

interface GendersRestrofitService {
    @GET("gender")
    fun getGenders(): Call<AllGenders>
}