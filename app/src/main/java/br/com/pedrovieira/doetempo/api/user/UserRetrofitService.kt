package br.com.pedrovieira.doetempo.api.user

import br.com.pedrovieira.doetempo.datastore.models.UserCreate
import br.com.pedrovieira.doetempo.models.UserCreatedResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserRetrofitService {
    @POST("user")
    fun saveUser(@Body body: UserCreate): Call<UserCreatedResponse>
}