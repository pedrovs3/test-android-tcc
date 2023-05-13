package br.com.pedrovieira.doetempo.datastore.models.dto

import com.google.gson.annotations.SerializedName

data class AddressDTO(
    @SerializedName("postal_code")
    var postalCode: String? = null,
    var number: String? = null,
    var complement: String? = null,
)
