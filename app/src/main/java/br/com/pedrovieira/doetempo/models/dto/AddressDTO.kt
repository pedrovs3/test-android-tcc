package br.com.pedrovieira.doetempo.models.dto

data class AddressDTO(
    var postalCode: String,
    var number: String,
    var complement: String?,
)
