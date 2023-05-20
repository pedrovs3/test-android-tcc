package br.com.pedrovieira.doetempo.models.responses

import br.com.pedrovieira.doetempo.datastore.models.dto.AddressDTO
import com.google.gson.annotations.SerializedName

data class CreateNgo(
    val name: String? = null,
    val email: String? = null,
    val password: String? = null,
    @SerializedName("foundation_date")
    val foundationDate: String? = null,
    val cnpj: String? = null,
    val address: AddressDTO? = null
)
