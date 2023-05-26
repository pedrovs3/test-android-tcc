package br.com.pedrovieira.doetempo.models.payload

import com.google.gson.annotations.SerializedName

data class PayloadPublication(
    val id: String? = null,
    val content: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null
)
