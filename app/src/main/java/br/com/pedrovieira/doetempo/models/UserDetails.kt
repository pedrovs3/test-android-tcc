package br.com.pedrovieira.doetempo.models

import br.com.pedrovieira.doetempo.datastore.models.Address
import br.com.pedrovieira.doetempo.datastore.models.Phone
import br.com.pedrovieira.doetempo.datastore.models.User
import br.com.pedrovieira.doetempo.datastore.models.campaign.Campaign
import br.com.pedrovieira.doetempo.datastore.models.campaign.UserSupportedCampaign
import br.com.pedrovieira.doetempo.datastore.models.dto.GenderDTO
import com.google.gson.annotations.SerializedName

data class UserDetails(
    var id: String? = null,
    var name: String? = null,
    var email: String? = null,
    var cpf: String? = null,
    var birthdate: String? = null,
    @SerializedName("user_address") var address: Address? = null,
    var gender: GenderDTO? = null,
    @SerializedName("user_phone") var phones: Phone? = null,
    @SerializedName("following") var following: List<User>? = null,
    @SerializedName("supported_campaigns") var supportedCampaigns: List<UserSupportedCampaign>? = null,
    val password: String? = null,
    @SerializedName("id_gender")
    val idGender: String? = null,
    val rg: String? = null,
    val description: String? = null,
    @SerializedName("banner_photo")
    val bannerPhoto: String? = null,
    @SerializedName("photo_url")
    val photoURL: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
//    @SerializedName("attached_link")
//    val attachedLink: List<Any?>? = null,
//    @SerializedName("user_address")
//    val userAddress: UserAddress? = null,
//    @SerializedName("user_phone")
//    val userPhone: Any? = null,
//    @SerializedName("post_user")
//    val postUser: List<Any?>? = null,
    @SerializedName("_count")
    val count: Count? = null
)

