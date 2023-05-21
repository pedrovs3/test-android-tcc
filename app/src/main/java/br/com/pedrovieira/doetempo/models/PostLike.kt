package br.com.pedrovieira.doetempo.models

import com.google.gson.annotations.SerializedName

data class PostLike(
    val id: String? = null,
    @SerializedName("id_user")
    val idUser: String? = null,
    @SerializedName("id_ngo")
    val idNgo: Any? = null,
    @SerializedName("id_post")
    val idPost: String? = null
)
