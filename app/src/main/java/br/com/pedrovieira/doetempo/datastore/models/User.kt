package br.com.pedrovieira.doetempo.datastore.models

import br.com.pedrovieira.doetempo.datastore.models.dto.GenderDTO
import com.google.gson.annotations.SerializedName

data class User(
    var id: String? = null,
    var name: String? = null,
    var email: String? = null,
    var cpf: String? = null,
    var birthdate: String? = null,
    @SerializedName("user_address") var address: Address? = null,
    var gender: GenderDTO? = null,
    @SerializedName("user_phone") var phones: Phone? = null,
    @SerializedName("following") var following: List<User>? = null,
    @SerializedName("campaign_participants") var campaignParticipant: List<User>? = null,
    var type: String? = null
)