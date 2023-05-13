package br.com.pedrovieira.doetempo.api.viacep

import br.com.pedrovieira.doetempo.api.RetrofitApiDoeTempo
import br.com.pedrovieira.doetempo.constants.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitApiViaCep {
    companion object {
        private lateinit var instance: Retrofit

        fun getRetrofit(): Retrofit {
            if (!RetrofitApiViaCep.Companion::instance.isInitialized) {
                instance = Retrofit
                    .Builder()
                    .baseUrl(Constants.VIACEP_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return instance
        }

        fun getViaCepService(): ViaCepRetrofitService {
            instance = getRetrofit()
            return instance.create(ViaCepRetrofitService::class.java)
        }
    }
}