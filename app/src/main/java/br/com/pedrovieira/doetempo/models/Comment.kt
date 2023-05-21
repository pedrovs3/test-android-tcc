package br.com.pedrovieira.doetempo.models

import com.google.gson.annotations.SerializedName

data class Comment (
    val id: String? = null,
    val content: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("id_post")
    val idPost: String? = null
)