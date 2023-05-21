package br.com.pedrovieira.doetempo.models

import com.google.gson.annotations.SerializedName

data class PostPhoto (
    val id: String? = null,
    @SerializedName("id_post")
    val idPost: String? = null,
    @SerializedName("photo_url")
    val photoURL: String? = null
)
