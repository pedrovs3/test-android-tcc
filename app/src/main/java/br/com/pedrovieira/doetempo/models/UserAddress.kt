package br.com.pedrovieira.doetempo.models

import br.com.pedrovieira.doetempo.datastore.models.dto.AddressDTO

data class UserAddress(
    val id: String? = null,
    val idAddress: String? = null,
    val idUser: String? = null,
    val address: AddressDTO? = null
)
