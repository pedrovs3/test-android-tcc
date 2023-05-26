package br.com.pedrovieira.doetempo.api

import br.com.pedrovieira.doetempo.api.auth.AuthRetrofitService
import br.com.pedrovieira.doetempo.api.campaign.CampaignRetrofitService
import br.com.pedrovieira.doetempo.api.genders.GendersRetrofitService
import br.com.pedrovieira.doetempo.api.ngo.NgoRetrofitService
import br.com.pedrovieira.doetempo.api.publication.PublicationRetrofitService
import br.com.pedrovieira.doetempo.api.user.UserRetrofitService
import br.com.pedrovieira.doetempo.constants.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

        fun retrofitGenderServices(): GendersRetrofitService {
            instance = getRetrofit()
            return instance.create(GendersRetrofitService::class.java)
        }

        fun retrofitUserServices(): UserRetrofitService {
            instance = getRetrofit()
            return instance.create(UserRetrofitService::class.java)
        }
        fun retrofitNgoServices(): NgoRetrofitService {
            instance = getRetrofit()
            return instance.create(NgoRetrofitService::class.java)
        }

        fun retrofitPublicationServices(): PublicationRetrofitService {
            instance = getRetrofit()
            return instance.create(PublicationRetrofitService::class.java)
        }
    }
}