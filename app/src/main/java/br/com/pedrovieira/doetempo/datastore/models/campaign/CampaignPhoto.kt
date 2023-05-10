package br.com.pedrovieira.doetempo.datastore.models.campaign

import com.google.gson.annotations.SerializedName

data class CampaignPhoto (
    @SerializedName("photo_url")
    val photoURL: String? = null
)