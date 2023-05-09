package br.com.pedrovieira.doetempo.api

import br.com.pedrovieira.doetempo.api.auth.AuthRetrofitService
import br.com.pedrovieira.doetempo.api.campaign.CampaignRetrofitService
import br.com.pedrovieira.doetempo.constants.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitApiDoeTempo {
    companion object {
        private lateinit var instance: Retrofit

        fun getRetrofit(): Retrofit {
            if(!Companion::instance.isInitialized) {
                instance = Retrofit
                    .Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return instance
        }

        fun retrofitServiceAuth(): AuthRetrofitService {
            instance = getRetrofit()
            return instance.create(AuthRetrofitService::class.java)
        }

        fun retrofitCampaignServices(): CampaignRetrofitService {
            instance = getRetrofit()
            return instance.create(CampaignRetrofitService::class.java)
        }
    }
}