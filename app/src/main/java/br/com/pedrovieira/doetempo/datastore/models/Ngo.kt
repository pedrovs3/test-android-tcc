package br.com.pedrovieira.doetempo.datastore.models

import com.google.gson.annotations.SerializedName

data class Ngo (
    val id: String? = null,
    val name: String? = null,
    val description: Any? = null,
    val email: String? = null,
    val cnpj: String? = null,
    @SerializedName("photo_url")
    val photoURL: String? = null
)