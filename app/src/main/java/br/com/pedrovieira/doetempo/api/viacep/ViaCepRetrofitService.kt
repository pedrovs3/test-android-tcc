package br.com.pedrovieira.doetempo.api.viacep

import br.com.pedrovieira.doetempo.models.responses.CepResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ViaCepRetrofitService {

    @GET("{cep}/json")
    fun getAddress(@Path("cep") cep: String): Call<CepResponse>
}