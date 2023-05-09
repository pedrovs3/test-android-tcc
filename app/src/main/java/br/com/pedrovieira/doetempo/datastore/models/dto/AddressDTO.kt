package br.com.pedrovieira.doetempo.datastore.models.dto

data class AddressDTO(
    var postalCode: String? = null,
    var number: String? = null,
    var complement: String? = null,
)
