package br.com.pedrovieira.doetempo.datastore.models

import br.com.pedrovieira.doetempo.datastore.models.dto.AddressDTO
import br.com.pedrovieira.doetempo.datastore.models.dto.PhoneDTO

data class UserCreate(
    val name: String? = null,
    val email: String? = null,
    val password: String? = null,
    val cpf: String? = null,
    val birthdate: String? = null,
    val address: AddressDTO? = null,
    val phone: List<PhoneDTO>? = null,
    val gender: String? = null
)
