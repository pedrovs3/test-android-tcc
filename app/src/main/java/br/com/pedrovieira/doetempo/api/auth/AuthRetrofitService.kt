package br.com.pedrovieira.doetempo.api.auth

import br.com.pedrovieira.doetempo.datastore.models.dto.AuthDTO
import br.com.pedrovieira.doetempo.datastore.models.responses.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRetrofitService {

    @POST("auth")
    fun login(@Body authBody: AuthDTO) : Call<LoginResponse>
}