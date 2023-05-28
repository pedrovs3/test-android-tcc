package br.com.pedrovieira.doetempo.api.publication

import br.com.pedrovieira.doetempo.models.CreatePostBody
import br.com.pedrovieira.doetempo.models.responses.AllPostResponse
import br.com.pedrovieira.doetempo.models.responses.PostResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface PublicationRetrofitService {
    @POST("post/")
    fun createPost(@Header("Authorization") authToken: String, @Body bodyPost: CreatePostBody): Call<PostResponse>

    @GET("post/")
    fun getAll(@Header("Authorization") authToken: String) : Call<AllPostResponse>
}