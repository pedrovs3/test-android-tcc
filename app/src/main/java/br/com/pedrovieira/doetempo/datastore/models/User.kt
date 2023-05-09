package br.com.pedrovieira.doetempo.datastore.models

import br.com.pedrovieira.doetempo.datastore.models.dto.GenderDTO
import com.google.gson.annotations.SerializedName

data class User(
    var id: String,
    var name: String?,
    var email: String?,
    var cpf: String?,
    var birthdate: String?,
    @SerializedName("user_address") var address: Address?,
    var gender: GenderDTO?,
    @SerializedName("user_phone") var phones: Phone?,
    @SerializedName("following") var following: List<User>?,
    @SerializedName("campaign_participants") var campaignParticipant: List<User>?,
    var type: String?
)