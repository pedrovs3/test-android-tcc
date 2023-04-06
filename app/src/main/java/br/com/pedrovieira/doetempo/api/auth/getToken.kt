package br.com.pedrovieira.doetempo.api.auth

import android.content.Context
import android.util.Log
import br.com.pedrovieira.doetempo.api.RetrofitApiDoeTempo
import br.com.pedrovieira.doetempo.models.User
import br.com.pedrovieira.doetempo.models.dto.AuthDTO
import br.com.pedrovieira.doetempo.models.responses.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun getToken(context: Context ,authBody: AuthDTO, onComplete: (String) -> Unit) {
    val call = RetrofitApiDoeTempo.retrofitServiceAuth().login(authBody)

    call.enqueue(object : Callback<LoginResponse> {
        override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
            if(response.body()!!.token.isEmpty()) {
                return
            }
            onComplete.invoke(response.body()!!.data.type.toString())

            val sharedPreferences = context.getSharedPreferences("app_data", Context.MODE_PRIVATE)
            sharedPreferences.edit().putString("token", response.body()!!.token).apply()
        }

        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
            TODO("Not yet implemented")
        }

    })
}