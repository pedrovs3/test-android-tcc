package br.com.pedrovieira.doetempo.api.auth

import android.content.Context
import android.util.Log
import br.com.pedrovieira.doetempo.api.RetrofitApiDoeTempo
import br.com.pedrovieira.doetempo.datastore.DataStoreAppData
import br.com.pedrovieira.doetempo.datastore.models.dto.AuthDTO
import br.com.pedrovieira.doetempo.datastore.models.responses.LoginResponse
import com.auth0.android.jwt.JWT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun getToken(context: Context, authBody: AuthDTO, onComplete: (String) -> Unit) {
    val call = RetrofitApiDoeTempo.retrofitServiceAuth().login(authBody)
    val scope = CoroutineScope(Dispatchers.Main)
    val dataStore = DataStoreAppData(context = context)

    call.enqueue(object : Callback<LoginResponse> {
        override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
            if(response.message().toString().contains("Bad Request") || response.message().toString().contains("Unauthorized")) {
                return onComplete.invoke("Usuário ou senha incorretos!")
            }

            val jwt = JWT(response.body()!!.token)

            scope.launch {
                dataStore.saveToken(response.body()!!.token)
                dataStore.saveType(jwt.getClaim("type").asString().toString())
                dataStore.saveIdUser(jwt.getClaim("id").asString().toString())
                dataStore.saveName(response.body()!!.data.name.toString())
            }

            onComplete.invoke(response.body()!!.token)
        }

        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
            TODO("Not yet implemented")
        }

    })
}