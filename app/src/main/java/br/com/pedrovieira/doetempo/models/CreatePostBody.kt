package br.com.pedrovieira.doetempo.models

import com.google.gson.annotations.SerializedName

data class CreatePostBody(
    val content: String? = null,
    @SerializedName("type_of_user")
    val typeUser: String? = null,
    val photos: List<String>? = null,
)
