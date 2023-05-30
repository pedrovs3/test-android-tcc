package br.com.pedrovieira.doetempo.models.responses

import br.com.pedrovieira.doetempo.datastore.models.campaign.Campaign
import br.com.pedrovieira.doetempo.models.PostNgo
import com.google.gson.annotations.SerializedName

data class NgoDetailsResponse(
    val id: String? = null,
    @SerializedName("photo_url")
    val photoURL: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("attached_link")
    val attachedLink: List<Any?>? = null,
    @SerializedName("banner_photo")
    val bannerPhoto: String? = null,
    @SerializedName("post_ngo")
    val postNgo: List<PostNgo>? = null,
    @SerializedName("ngo_address")
    val ngoAddress: NgoAddress? = null,
    @SerializedName("ngo_causes")
    val ngoCauses: List<Any?>? = null,
    val email: String? = null,
    val name: String? = null,
    val password: String? = null,
    @SerializedName("foundation_date")
    val foundationDate: String? = null,
    val type: Type? = null,
    val cnpj: String? = null,
    val campaign: List<Campaign>? = null,
    val description: String? = null,
    val following: List<Any?>? = null,
    @SerializedName("ngo_phone")
    val ngoPhone: List<Any?>? = null
)
