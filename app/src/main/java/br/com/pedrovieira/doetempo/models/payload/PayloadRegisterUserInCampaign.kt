package br.com.pedrovieira.doetempo.models.payload

import com.google.gson.annotations.SerializedName

data class PayloadRegisterUserInCampaign(
    val id: String? = null,
    @SerializedName("id_campaign")
    val idCampaign: String? = null,
    @SerializedName("id_user")
    val idUser: String? = null,
    @SerializedName("id_status")
    val idStatus: String? = null
)
