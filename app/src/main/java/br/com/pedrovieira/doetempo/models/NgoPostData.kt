package br.com.pedrovieira.doetempo.models

import com.google.gson.annotations.SerializedName

data class NgoPostData(
    val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    @SerializedName("photo_url")
    val photoURL: String? = null,
)
