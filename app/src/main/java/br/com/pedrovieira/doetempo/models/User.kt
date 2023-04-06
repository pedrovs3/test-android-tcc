package br.com.pedrovieira.doetempo.models

import br.com.pedrovieira.doetempo.models.dto.GenderDTO
import com.google.gson.annotations.SerializedName

data class User(
    var id: String,
    var name: String?,
    var email: String?,
    var cpf: String?,
    var birthdate: String?,
    @SerializedName("userAddress") var address: Address?,
    var gender: GenderDTO?,
    @SerializedName("tbl_user_phone") var phones: Phone?,
    @SerializedName("tbl_following") var following: List<User>?,
    @SerializedName("tbl_campaign_participants") var campaignParticipant: List<User>?,
    var type: String?
)